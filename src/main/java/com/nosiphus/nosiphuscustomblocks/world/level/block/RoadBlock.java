package com.nosiphus.nosiphuscustomblocks.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class RoadBlock extends Block {

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final EnumProperty<SlopeState> SLOPE = EnumProperty.create("slope", SlopeState.class);
    public static final EnumProperty<RoadTexture> TEXTURE = EnumProperty.create("texture", RoadTexture.class);

    public RoadBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AXIS, Direction.Axis.X)
                .setValue(SLOPE, SlopeState.NONE)
                .setValue(TEXTURE, RoadTexture.LANE));
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        SlopeState slope = state.getValue(SLOPE);
        if (slope == SlopeState.NONE) return Shapes.block();
        VoxelShape shape = Shapes.empty();
        for (int i = 0; i < 16; i++) {
            shape = switch (slope) {
                case NORTH -> Shapes.or(shape, Block.box(0, 0, 15 - i, 16, i + 1, 16 - i));
                case SOUTH -> Shapes.or(shape, Block.box(0, 0, i, 16, i + 1, i + 1));
                case EAST -> Shapes.or(shape, Block.box(i, 0, 0, i + 1, i + 1, 16));
                case WEST -> Shapes.or(shape, Block.box(15 - i, 0, 0, 16 - i, i + 1, 16));
                default -> shape;
            };
        }
        return shape;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.getRoadState(context.getLevel(), context.getClickedPos(), this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if(!level.isClientSide) {
            BlockPos.betweenClosedStream(pos.offset(-8, -1, -8), pos.offset(8, 1, 8))
                    .forEach(targetPos -> {
                        BlockState targetState = level.getBlockState(targetPos);
                        if(targetState.is(this)) {
                            level.setBlock(targetPos, this.getRoadState(level, targetPos, targetState), 3);
                        }
                    });
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if(level instanceof Level world && !world.isClientSide) {
            return this.getRoadState(world, pos, state);
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    private BlockState getRoadState(Level level, BlockPos pos, BlockState state) {
        Direction.Axis axis = state.getValue(AXIS);

        if (axis != Direction.Axis.Y) {
            BlockState below = level.getBlockState(pos.below());
            if (below.is(this) && below.getValue(AXIS) == axis) {
                RoadTexture inherited = below.getValue(TEXTURE);
                if (axis == Direction.Axis.X) {
                    if (isRoadAxis(level, pos.east(), axis)) return state.setValue(SLOPE, SlopeState.EAST).setValue(TEXTURE, inherited);
                    if (isRoadAxis(level, pos.west(), axis)) return state.setValue(SLOPE, SlopeState.WEST).setValue(TEXTURE, inherited);
                } else {
                    if (isRoadAxis(level, pos.north(), axis)) return state.setValue(SLOPE, SlopeState.NORTH).setValue(TEXTURE, inherited);
                    if (isRoadAxis(level, pos.south(), axis)) return state.setValue(SLOPE, SlopeState.SOUTH).setValue(TEXTURE, inherited);
                }
            }
        }

        return (axis.isVertical()) ? calculateIntersection(level, pos, state) : calculateStraightRoad(level, pos, state, axis);
    }

    private BlockState calculateIntersection(Level level, BlockPos pos, BlockState state) {
        // 1. POSITIONING & SCANNING
        boolean northHub = isRoadAxis(level, pos.north(), Direction.Axis.Y);
        boolean southHub = isRoadAxis(level, pos.south(), Direction.Axis.Y);
        boolean eastHub = isRoadAxis(level, pos.east(), Direction.Axis.Y);
        boolean westHub = isRoadAxis(level, pos.west(), Direction.Axis.Y);

        // High-precision anchor scanning
        AnchorInfo n = scanAnchor(level, pos, Direction.NORTH);
        AnchorInfo s = scanAnchor(level, pos, Direction.SOUTH);
        AnchorInfo e = scanAnchor(level, pos, Direction.EAST);
        AnchorInfo w = scanAnchor(level, pos, Direction.WEST);

        // Determine intersection width and calculate the stopping point for dividers
        int roadWidth = (n.dist < 20 && s.dist < 20) ? (n.dist + s.dist - 1) :
                (e.dist < 20 && w.dist < 20) ? (e.dist + w.dist - 1) :
                        Math.max(Math.max(n.width, s.width), Math.max(e.width, w.width));

        float limit = (roadWidth % 2 != 0) ? (roadWidth / 2.0f + 0.5f) : (roadWidth / 2.0f + 1.0f);

        LShape lShape = LShape.NONE;
        ShoulderCorner corner = ShoulderCorner.NONE;
        ShoulderEdge edge = ShoulderEdge.NONE;

        // 2. SHOULDER & EDGE LOGIC (Existing)
        if (!northHub && !southHub && !eastHub && !westHub) {
            corner = ShoulderCorner.FULL;
        } else if (!northHub && !westHub && southHub && eastHub) {
            edge = ShoulderEdge.NORTHWEST;
            if (n.dist < 20 && w.dist < 20) corner = ShoulderCorner.NORTHWEST;
            else if (n.dist < 20) corner = ShoulderCorner.NORTH;
            else if (w.dist < 20) corner = ShoulderCorner.WEST;
        } else if (!northHub && !eastHub && southHub && westHub) {
            edge = ShoulderEdge.NORTHEAST;
            if (n.dist < 20 && e.dist < 20) corner = ShoulderCorner.NORTHEAST;
            else if (n.dist < 20) corner = ShoulderCorner.NORTH;
            else if (e.dist < 20) corner = ShoulderCorner.EAST;
        } else if (!southHub && !westHub && northHub && eastHub) {
            edge = ShoulderEdge.SOUTHWEST;
            if (s.dist < 20 && w.dist < 20) corner = ShoulderCorner.SOUTHWEST;
            else if (s.dist < 20) corner = ShoulderCorner.SOUTH;
            else if (w.dist < 20) corner = ShoulderCorner.WEST;
        } else if (!southHub && !eastHub && northHub && westHub) {
            edge = ShoulderEdge.SOUTHEAST;
            if (s.dist < 20 && e.dist < 20) corner = ShoulderCorner.SOUTHEAST;
            else if (s.dist < 20) corner = ShoulderCorner.SOUTH;
            else if (e.dist < 20) corner = ShoulderCorner.EAST;
        } else if (!northHub && southHub) edge = ShoulderEdge.NORTH;
        else if (!southHub && northHub) edge = ShoulderEdge.SOUTH;
        else if (!eastHub && westHub) edge = ShoulderEdge.EAST;
        else if (!westHub && eastHub) edge = ShoulderEdge.WEST;

        // 3. REFINED DIVIDER LOGIC (The "Bend Zone")
        // Only process dividers if we are within the limit of the intersection center
        boolean inVLimit = (n.dist <= limit || s.dist <= limit);
        boolean inHLimit = (e.dist <= limit || w.dist <= limit);

        if (inVLimit && inHLimit) {
            // Handshake: Check if dividers are entering from cardinal anchors
            boolean hasDivN = n.dist < 20 && hasDividerAtAnchor(level, pos, Direction.NORTH);
            boolean hasDivS = s.dist < 20 && hasDividerAtAnchor(level, pos, Direction.SOUTH);
            boolean hasDivE = e.dist < 20 && hasDividerAtAnchor(level, pos, Direction.EAST);
            boolean hasDivW = w.dist < 20 && hasDividerAtAnchor(level, pos, Direction.WEST);

            // XOR check to ensure it is a true L-turn (one vertical, one horizontal)
            if ((hasDivN ^ hasDivS) && (hasDivE ^ hasDivW)) {
                if (hasDivN && hasDivW) lShape = LShape.BEND_NORTHWEST;
                else if (hasDivN && hasDivE) lShape = LShape.BEND_NORTHEAST;
                else if (hasDivS && hasDivW) lShape = LShape.BEND_SOUTHWEST;
                else if (hasDivS && hasDivE) lShape = LShape.BEND_SOUTHEAST;
            } else {
                if (hasDivN || hasDivS) lShape = LShape.ODD_NORTH_SOUTH;
                else if (hasDivE || hasDivW) lShape = LShape.ODD_EAST_WEST;
            }
        }

        return resolveTexture(state, lShape, corner, edge, false, !isRoad(level, pos.north()), !isRoad(level, pos.south()), 0, roadWidth);
    }

    private BlockState calculateStraightRoad(Level level, BlockPos pos, BlockState state, Direction.Axis axis) {
        Direction widthDir = (axis == Direction.Axis.X) ? Direction.NORTH : Direction.WEST;
        int L = 0; while (L < 8 && isRoadAxis(level, pos.relative(widthDir, L + 1), axis)) L++;
        int R = 0; while (R < 8 && isRoadAxis(level, pos.relative(widthDir.getOpposite(), R + 1), axis)) R++;
        int totalWidth = L + R + 1;

        if (totalWidth == 1) return resolveTexture(state, LShape.NONE, ShoulderCorner.NONE, ShoulderEdge.NONE, false, true, true, 0, 1);

        return resolveTexture(state, LShape.NONE, ShoulderCorner.NONE, ShoulderEdge.NONE, false, L == 0, R == 0, L, totalWidth);
    }

    private boolean hasDividerAtAnchor(Level level, BlockPos pos, Direction dir) {
        for (int i = 1; i <= 16; i++) {
            BlockPos checkPos = pos.relative(dir, i);
            BlockState state = level.getBlockState(checkPos);

            // Stop if we run out of road blocks
            if (!state.is(this)) break;

            // Once we hit the horizontal/flow road, check the Texture Enum
            if (state.getValue(AXIS) != Direction.Axis.Y) {
                String texName = state.getValue(TEXTURE).name();
                // This covers ODD_DIVIDER, EVEN_DIVIDER_LEFT, and EVEN_DIVIDER_RIGHT
                return texName.contains("DIVIDER");
            }
        }
        return false;
    }

    private boolean isRoad(Level level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof RoadBlock;
    }

    private boolean isRoadAxis(Level level, BlockPos pos, Direction.Axis axis) {
        return level.getBlockState(pos).is(this) && level.getBlockState(pos).getValue(AXIS) == axis;
    }

    private LShape resolveL(AnchorInfo n, AnchorInfo e, AnchorInfo s, AnchorInfo w) {
        if (n.dist < 20 && w.dist < 20) return LShape.BEND_NORTHWEST;
        if (n.dist < 20 && e.dist < 20) return LShape.BEND_NORTHEAST;
        if (s.dist < 20 && w.dist < 20) return LShape.BEND_SOUTHWEST;
        return LShape.BEND_SOUTHEAST;
    }

    private BlockState resolveTexture(BlockState state, LShape lShape, ShoulderCorner corner, ShoulderEdge edge, boolean crosswalk, boolean sL, boolean sR, int laneIndex, int width) {
        Direction.Axis axis = state.getValue(AXIS);
        RoadTexture tex = RoadTexture.LANE;

        try {
            // --- 1. HORIZONTAL LOGIC (X or Z Axis) ---
            if (axis != Direction.Axis.Y) {
                if (crosswalk) {
                    if (sL && sR) tex = RoadTexture.CROSSWALK_SINGLE;
                    else if (sL) tex = RoadTexture.SHOULDER_CROSSWALK_LEFT;
                    else if (sR) tex = RoadTexture.SHOULDER_CROSSWALK_RIGHT;
                    else tex = RoadTexture.CROSSWALK;
                } else {
                    if (width == 1) tex = RoadTexture.SINGLE;
                    else if (width == 2) tex = sL ? RoadTexture.SHOULDER_DIVIDER_LEFT : RoadTexture.SHOULDER_DIVIDER_RIGHT;
                    else if (sL) tex = RoadTexture.SHOULDER_LEFT;
                    else if (sR) tex = RoadTexture.SHOULDER_RIGHT;
                    else if (width % 2 != 0 && laneIndex == width / 2) tex = RoadTexture.ODD_DIVIDER;
                    else if (width % 2 == 0) {
                        if (laneIndex == (width / 2) - 1) tex = RoadTexture.EVEN_DIVIDER_LEFT;
                        else if (laneIndex == width / 2) tex = RoadTexture.EVEN_DIVIDER_RIGHT;
                    }
                }
            }
            // --- 2. VERTICAL LOGIC (Y Axis / Intersections) ---
            else {
                if (lShape != LShape.NONE) {
                    String name = lShape.name();
                    if (name.startsWith("BEND")) {
                        String orient = name.substring(5);
                        if (width % 2 != 0) return state.setValue(TEXTURE, RoadTexture.valueOf("ODD_DIVIDER_L_" + orient));
                        String prefix = (width == 2) ? "DOUBLE_DIVIDER_L_" : "EVEN_DIVIDER_L_";
                        return state.setValue(TEXTURE, RoadTexture.valueOf(prefix + corner.name() + "_" + orient));
                    } else if (name.contains("NS")) {
                        tex = (width % 2 != 0) ? RoadTexture.ODD_DIVIDER_Y_NORTHSOUTH : (width == 2 ? RoadTexture.EVEN_DIVIDER_LEFT_Y_NORTHSOUTH : RoadTexture.LANE);
                    } else if (name.contains("EW")) {
                        tex = (width % 2 != 0) ? RoadTexture.ODD_DIVIDER_Y_EASTWEST : (width == 2 ? RoadTexture.EVEN_DIVIDER_LEFT_Y_EASTWEST : RoadTexture.LANE);
                    }
                }
                else if (corner == ShoulderCorner.FULL) {
                    tex = RoadTexture.SHOULDER_SINGLE;
                } else if (edge != ShoulderEdge.NONE) {
                    String eName = edge.name(); // Diagonal (NORTHWEST, etc.)
                    String cName = corner.name(); // Cardinal (NORTH) or Diagonal (NORTHWEST)

                    // RULE 1: Inside Corner Bends
                    // Triggers when corner and edge match exactly (e.g., NORTHWEST + NORTHWEST)
                    if (cName.equals(eName)) {
                        tex = RoadTexture.valueOf("SHOULDER_INSIDE_CORNER_" + cName);
                    }
                    // RULE 2: Outside Corner Diagonals
                    // Triggers when edge is diagonal but corner is NONE
                    else if (corner == ShoulderCorner.NONE && eName.length() > 5) {
                        tex = RoadTexture.valueOf("SHOULDER_OUTSIDE_CORNER_" + eName);
                    }
                    // RULE 3: Straight Shoulder Edges
                    // Triggers when corner is cardinal (NORTH, SOUTH, EAST, WEST)
                    else if (corner != ShoulderCorner.NONE && cName.length() <= 5) {
                        tex = RoadTexture.valueOf("SHOULDER_EDGE_" + cName);
                    }
                }
            }
        } catch (Exception e) {
            tex = RoadTexture.LANE;
        }
        return state.setValue(TEXTURE, tex);
    }

    private record AnchorInfo(int dist, int width) {}

    private AnchorInfo scanAnchor(Level level, BlockPos pos, Direction dir) {
        for (int i = 1; i <= 16; i++) {
            BlockPos checkPos = pos.relative(dir, i);
            BlockState checkState = level.getBlockState(checkPos);
            if (!checkState.is(this)) return new AnchorInfo(99, 1);
            if (checkState.getValue(AXIS) != Direction.Axis.Y) {
                Direction widthDir = (dir.getAxis() == Direction.Axis.X) ? Direction.NORTH : Direction.WEST;
                int left = 0; while (left < 8 && isRoadAxis(level, checkPos.relative(widthDir, left + 1), checkState.getValue(AXIS))) left++;
                int right = 0; while (right < 8 && isRoadAxis(level, checkPos.relative(widthDir.getOpposite(), right + 1), checkState.getValue(AXIS))) right++;
                return new AnchorInfo(i, left + right + 1);
            }
        }
        return new AnchorInfo(99, 1);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, SLOPE, TEXTURE);
    }

    public enum LShape {
        NONE,
        EVEN_NORTH_SOUTH,
        EVEN_EAST_WEST,
        ODD_NORTH_SOUTH,
        ODD_EAST_WEST,
        BEND_NORTHWEST,
        BEND_NORTHEAST,
        BEND_SOUTHWEST,
        BEND_SOUTHEAST;
    }

    public enum RoadTexture implements StringRepresentable {
        CROSSWALK("crosswalk"),
        CROSSWALK_SINGLE("crosswalk_single"),
        DOUBLE_DIVIDER_L_BOTTOM_LEFT_NORTHWEST("double_divider_l_bottom_left_northwest"),
        DOUBLE_DIVIDER_L_BOTTOM_LEFT_NORTHEAST("double_divider_l_bottom_left_northeast"),
        DOUBLE_DIVIDER_L_BOTTOM_LEFT_SOUTHEAST("double_divider_l_bottom_left_southeast"),
        DOUBLE_DIVIDER_L_BOTTOM_LEFT_SOUTHWEST("double_divider_l_bottom_left_southwest"),
        DOUBLE_DIVIDER_L_BOTTOM_RIGHT_NORTHWEST("double_divider_l_bottom_right_northwest"),
        DOUBLE_DIVIDER_L_BOTTOM_RIGHT_NORTHEAST("double_divider_l_bottom_right_northeast"),
        DOUBLE_DIVIDER_L_BOTTOM_RIGHT_SOUTHEAST("double_divider_l_bottom_right_southeast"),
        DOUBLE_DIVIDER_L_BOTTOM_RIGHT_SOUTHWEST("double_divider_l_bottom_right_southwest"),
        DOUBLE_DIVIDER_L_TOP_LEFT_NORTHWEST("double_divider_l_top_left_northwest"),
        DOUBLE_DIVIDER_L_TOP_LEFT_NORTHEAST("double_divider_l_top_left_northeast"),
        DOUBLE_DIVIDER_L_TOP_LEFT_SOUTHEAST("double_divider_l_top_left_southeast"),
        DOUBLE_DIVIDER_L_TOP_LEFT_SOUTHWEST("double_divider_l_top_left_southwest"),
        DOUBLE_DIVIDER_L_TOP_RIGHT_NORTHWEST("double_divider_l_top_right_northwest"),
        DOUBLE_DIVIDER_L_TOP_RIGHT_NORTHEAST("double_divider_l_top_right_northeast"),
        DOUBLE_DIVIDER_L_TOP_RIGHT_SOUTHEAST("double_divider_l_top_right_southeast"),
        DOUBLE_DIVIDER_L_TOP_RIGHT_SOUTHWEST("double_divider_l_top_right_southwest"),
        EVEN_DIVIDER_L_BOTTOM_LEFT_NORTHWEST("even_divider_l_bottom_left_northwest"),
        EVEN_DIVIDER_L_BOTTOM_LEFT_NORTHEAST("even_divider_l_bottom_left_northeast"),
        EVEN_DIVIDER_L_BOTTOM_LEFT_SOUTHEAST("even_divider_l_bottom_left_southeast"),
        EVEN_DIVIDER_L_BOTTOM_LEFT_SOUTHWEST("even_divider_l_bottom_left_southwest"),
        EVEN_DIVIDER_L_BOTTOM_RIGHT_NORTHWEST("even_divider_l_bottom_right_northwest"),
        EVEN_DIVIDER_L_BOTTOM_RIGHT_NORTHEAST("even_divider_l_bottom_right_northeast"),
        EVEN_DIVIDER_L_BOTTOM_RIGHT_SOUTHEAST("even_divider_l_bottom_right_southeast"),
        EVEN_DIVIDER_L_BOTTOM_RIGHT_SOUTHWEST("even_divider_l_bottom_right_southwest"),
        EVEN_DIVIDER_L_TOP_LEFT_NORTHWEST("even_divider_l_top_left_northwest"),
        EVEN_DIVIDER_L_TOP_LEFT_NORTHEAST("even_divider_l_top_left_northeast"),
        EVEN_DIVIDER_L_TOP_LEFT_SOUTHEAST("even_divider_l_top_left_southeast"),
        EVEN_DIVIDER_L_TOP_LEFT_SOUTHWEST("even_divider_l_top_left_southwest"),
        EVEN_DIVIDER_L_TOP_RIGHT_NORTHWEST("even_divider_l_top_right_northwest"),
        EVEN_DIVIDER_L_TOP_RIGHT_NORTHEAST("even_divider_l_top_right_northeast"),
        EVEN_DIVIDER_L_TOP_RIGHT_SOUTHEAST("even_divider_l_top_right_southeast"),
        EVEN_DIVIDER_L_TOP_RIGHT_SOUTHWEST("even_divider_l_top_right_southwest"),
        EVEN_DIVIDER_LEFT("even_divider_left"),
        EVEN_DIVIDER_RIGHT("even_divider_right"),
        EVEN_DIVIDER_LEFT_Y_NORTHSOUTH("even_divider_left_y_northsouth"),
        EVEN_DIVIDER_LEFT_Y_EASTWEST("even_divider_left_y_eastwest"),
        EVEN_DIVIDER_RIGHT_Y_NORTHSOUTH("even_divider_right_y_northsouth"),
        EVEN_DIVIDER_RIGHT_Y_EASTWEST("even_divider_right_y_eastwest"),
        LANE("lane"),
        ODD_DIVIDER("odd_divider"),
        ODD_DIVIDER_L_NORTHWEST("odd_divider_l_northwest"),
        ODD_DIVIDER_L_NORTHEAST("odd_divider_l_northeast"),
        ODD_DIVIDER_L_SOUTHEAST("odd_divider_l_southeast"),
        ODD_DIVIDER_L_SOUTHWEST("odd_divider_l_southwest"),
        ODD_DIVIDER_Y_NORTHSOUTH("odd_divider_y_northsouth"),
        ODD_DIVIDER_Y_EASTWEST("odd_divider_y_eastwest"),
        SINGLE("single"),
        SHOULDER_CROSSWALK_LEFT("shoulder_crosswalk_left"),
        SHOULDER_CROSSWALK_RIGHT("shoulder_crosswalk_right"),
        SHOULDER_DIVIDER_LEFT("shoulder_divider_left"),
        SHOULDER_DIVIDER_RIGHT("shoulder_divider_right"),
        SHOULDER_EDGE_NORTH("shoulder_edge_north"),
        SHOULDER_EDGE_SOUTH("shoulder_edge_south"),
        SHOULDER_EDGE_EAST("shoulder_edge_east"),
        SHOULDER_EDGE_WEST("shoulder_edge_west"),
        SHOULDER_INSIDE_CORNER_NORTHWEST("shoulder_inside_corner_northwest"),
        SHOULDER_INSIDE_CORNER_NORTHEAST("shoulder_inside_corner_northeast"),
        SHOULDER_INSIDE_CORNER_SOUTHEAST("shoulder_inside_corner_southeast"),
        SHOULDER_INSIDE_CORNER_SOUTHWEST("shoulder_inside_corner_southwest"),
        SHOULDER_LEFT("shoulder_left"),
        SHOULDER_OUTSIDE_CORNER_NORTHWEST("shoulder_outside_corner_northwest"),
        SHOULDER_OUTSIDE_CORNER_NORTHEAST("shoulder_outside_corner_northeast"),
        SHOULDER_OUTSIDE_CORNER_SOUTHEAST("shoulder_outside_corner_southeast"),
        SHOULDER_OUTSIDE_CORNER_SOUTHWEST("shoulder_outside_corner_southwest"),
        SHOULDER_RIGHT("shoulder_right"),
        SHOULDER_SINGLE("shoulder_single"),
        SHOULDER_SINGLE_L_NORTHWEST("shoulder_single_l_northwest"),
        SHOULDER_SINGLE_L_NORTHEAST("shoulder_single_l_northeast"),
        SHOULDER_SINGLE_L_SOUTHEAST("shoulder_single_l_southeast"),
        SHOULDER_SINGLE_L_SOUTHWEST("shoulder_single_l_southwest"),
        SHOULDER_SINGLE_T_NORTH("shoulder_single_t_north"),
        SHOULDER_SINGLE_T_SOUTH("shoulder_single_t_south"),
        SHOULDER_SINGLE_T_EAST("shoulder_single_t_east"),
        SHOULDER_SINGLE_T_WEST("shoulder_single_t_west");

        private final String name;

        RoadTexture(String name) {
            this.name = name;
        }
        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public enum ShoulderCorner {
        NONE,
        NORTH,
        SOUTH,
        EAST,
        WEST,
        NORTHWEST,
        NORTHEAST,
        SOUTHWEST,
        SOUTHEAST,
        FULL;
    }

    public enum ShoulderEdge {
        NONE,
        NORTH,
        SOUTH,
        EAST,
        WEST,
        NORTHWEST,
        NORTHEAST,
        SOUTHWEST,
        SOUTHEAST;
    }

    public enum SlopeState implements StringRepresentable {
        NONE("none"),
        NORTH("north"),
        EAST("east"),
        SOUTH("south"),
        WEST("west");

        private final String name;

        SlopeState(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}