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
                case STRAIGHT_NORTH -> Shapes.or(shape, Block.box(0, 0, 15 - i, 16, i + 1, 16 - i));
                case STRAIGHT_SOUTH -> Shapes.or(shape, Block.box(0, 0, i, 16, i + 1, i + 1));
                case STRAIGHT_EAST -> Shapes.or(shape, Block.box(i, 0, 0, i + 1, i + 1, 16));
                case STRAIGHT_WEST -> Shapes.or(shape, Block.box(15 - i, 0, 0, 16 - i, i + 1, 16));
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
        return state.getValue(SLOPE) != SlopeState.NONE;
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

        if (axis.isVertical()) {
            return calculateVerticalRoad(level, pos, state);
        }

        BlockState below = level.getBlockState(pos.below());
        if (below.is(this) && below.getValue(AXIS) == axis) {
            return getSlopeInheritance(level, pos, state, axis, below.getValue(TEXTURE));
        }

        return calculateHorizontalRoad(level, pos, state, axis);

    }

    private BlockState calculateHorizontalRoad(Level level, BlockPos pos, BlockState state, Direction.Axis axis) {
        BlockState below = level.getBlockState(pos.below());
        if(below.is(this) && below.getValue(AXIS) == axis) {
            RoadTexture inherited = below.getValue(TEXTURE);
            if (axis == Direction.Axis.X) {
                if (isRoadAxis(level, pos.east(), axis)) {
                    return state.setValue(SLOPE, SlopeState.STRAIGHT_EAST).setValue(TEXTURE, inherited);
                } else if (isRoadAxis(level, pos.west(), axis)) {
                    return state.setValue(SLOPE, SlopeState.STRAIGHT_WEST).setValue(TEXTURE, inherited);
                }
            } else if (axis == Direction.Axis.Z) {
                if (isRoadAxis(level, pos.north(), axis)) {
                    return state.setValue(SLOPE, SlopeState.STRAIGHT_NORTH).setValue(TEXTURE, inherited);
                } else if (isRoadAxis(level, pos.south(), axis)) {
                    return state.setValue(SLOPE, SlopeState.STRAIGHT_SOUTH).setValue(TEXTURE, inherited);
                }
            }
        }

        Direction widthDirection = (axis == Direction.Axis.X) ? Direction.NORTH : Direction.WEST;
        Direction flowDirection = (axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;

        int leftWidth = 0;
        while (leftWidth < 8 && isRoadAxis(level, pos.relative(widthDirection, leftWidth + 1), axis)) {
            leftWidth++;
        }

        int rightWidth = 0;
        while (rightWidth < 8 && isRoadAxis(level, pos.relative(widthDirection.getOpposite(), rightWidth + 1), axis)) {
            rightWidth++;
        }

        int totalWidth = leftWidth + rightWidth + 1;

        boolean hasCrosswalk = false;

        for (Direction d : new Direction[]{flowDirection, flowDirection.getOpposite()}) {
            BlockPos hubPos = pos.relative(d);
            if (level.getBlockState(hubPos).is(this) && level.getBlockState(hubPos).getValue(AXIS).isVertical()) {
                int activeConnections = 0;
                for (Direction scanDirection : Direction.Plane.HORIZONTAL) {
                    for (int i = 1; i <= totalWidth + 1; i++) {
                        BlockPos checkPos = hubPos.relative(scanDirection, i);
                        BlockState checkState = level.getBlockState(checkPos);

                        if(checkState.is(this)) {
                            if(checkState.getValue(AXIS).isHorizontal()) {
                                activeConnections++;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                if (activeConnections >= 3) {
                    hasCrosswalk = true;
                    break;
                }
            }
        }

        return getRoadTexture(state, JunctionShape.NONE, "NONE", ShoulderCorner.NONE, ShoulderEdge.NONE, hasCrosswalk, leftWidth == 0, rightWidth == 0, false, false, leftWidth, rightWidth, totalWidth);
    }

    private BlockState calculateVerticalRoad(Level level, BlockPos pos, BlockState state) {
        boolean northHub = isRoadAxis(level, pos.north(), Direction.Axis.Y);
        boolean eastHub = isRoadAxis(level, pos.east(), Direction.Axis.Y);
        boolean southHub = isRoadAxis(level, pos.south(), Direction.Axis.Y);
        boolean westHub = isRoadAxis(level, pos.west(), Direction.Axis.Y);

        boolean northRoad = isRoadHorizontal(level, pos.north());
        boolean eastRoad = isRoadHorizontal(level, pos.east());
        boolean southRoad = isRoadHorizontal(level, pos.south());
        boolean westRoad = isRoadHorizontal(level, pos.west());

        AnchorInfo north = scanAnchor(level, pos, Direction.NORTH);
        AnchorInfo east = scanAnchor(level, pos, Direction.EAST);
        AnchorInfo south = scanAnchor(level, pos, Direction.SOUTH);
        AnchorInfo west = scanAnchor(level, pos, Direction.WEST);

        int roadWidth = (north.dist < 20 && south.dist < 20) ? (north.dist + south.dist - 1) :
                (east.dist < 20 && west.dist < 20) ? (east.dist + west.dist - 1) :
                        Math.max(Math.max(north.width, south.width), Math.max(east.width, west.width));

        float limit = (roadWidth % 2 != 0) ? (roadWidth / 2.0f + 0.5f) : (roadWidth / 2.0f + 1.0f);

        JunctionShape junctionShape = JunctionShape.NONE;
        ShoulderCorner corner = ShoulderCorner.NONE;
        ShoulderEdge edge = ShoulderEdge.NONE;
        String quadrant = "NONE";

        if (!northHub && !southHub && !eastHub && !westHub) {
            corner = ShoulderCorner.FULL;
        } else if (!northHub && !westHub && southHub && eastHub) {
            edge = ShoulderEdge.NORTHWEST;
            if (northRoad && westRoad) {
                corner = ShoulderCorner.NORTHWEST;
            } else if (northRoad) {
                corner = ShoulderCorner.WEST;
            } else if (westRoad) {
                corner = ShoulderCorner.NORTH;
            }
        } else if (!northHub && !eastHub && southHub && westHub) {
            edge = ShoulderEdge.NORTHEAST;
            if (northRoad && eastRoad) {
                corner = ShoulderCorner.NORTHEAST;
            } else if (northRoad) {
                corner = ShoulderCorner.EAST;
            } else if (eastRoad) {
                corner = ShoulderCorner.NORTH;
            }
        } else if (!southHub && !westHub && northHub && eastHub) {
            edge = ShoulderEdge.SOUTHWEST;
            if (southRoad && westRoad) {
                corner = ShoulderCorner.SOUTHWEST;
            } else if (southRoad) {
                corner = ShoulderCorner.WEST;
            } else if (westRoad) {
                corner = ShoulderCorner.SOUTH;
            }
        } else if (!southHub && !eastHub && northHub && westHub) {
            edge = ShoulderEdge.SOUTHEAST;
            if (southRoad && eastRoad) {
                corner = ShoulderCorner.SOUTHEAST;
            } else if (southRoad) {
                corner = ShoulderCorner.EAST;
            } else if (eastRoad) {
                corner = ShoulderCorner.SOUTH;
            }
        } else if (!northHub && southHub) {
            if (!isRoad(level, pos.north())) edge = ShoulderEdge.NORTH;
        } else if (!southHub && northHub) {
            if (!isRoad(level, pos.south())) edge = ShoulderEdge.SOUTH;
        } else if (!eastHub && westHub) {
            if (!isRoad(level, pos.east())) edge = ShoulderEdge.EAST;
        } else if (!westHub && eastHub) {
            if (!isRoad(level, pos.west())) edge = ShoulderEdge.WEST;
        }

        boolean hasVertical = (north.dist < 20 ^ south.dist < 20);
        boolean hasHorizontal = (east.dist < 20 ^ west.dist < 20);

        int totalConnections = (north.dist < 20 ? 1 : 0) + (south.dist < 20 ? 1 : 0) + (east.dist < 20 ? 1 : 0) + (west.dist < 20 ? 1 : 0);

        if (hasVertical && hasHorizontal && totalConnections == 2) {
            AnchorInfo verticalAnchor = (north.dist < 20) ? north : south;
            AnchorInfo horizontalAnchor = (east.dist < 20) ? east : west;

            int verticalDivider = (verticalAnchor.width % 2 != 0) ? (verticalAnchor.laneIndex == verticalAnchor.width / 2 ? 3 : 0) : (verticalAnchor.laneIndex == (verticalAnchor.width / 2) - 1 ? 1 : (verticalAnchor.laneIndex == verticalAnchor.width / 2 ? 2 : 0));
            int horizontalDivider = (horizontalAnchor.width % 2 != 0) ? (horizontalAnchor.laneIndex == horizontalAnchor.width / 2 ? 3 : 0) : (horizontalAnchor.laneIndex == (horizontalAnchor.width / 2) - 1 ? 1 : (horizontalAnchor.laneIndex == horizontalAnchor.width / 2 ? 2 : 0));

            if (verticalAnchor.dist <= limit && horizontalAnchor.dist <= limit && (verticalDivider > 0 || horizontalDivider > 0)) {
                if (verticalDivider > 0 && horizontalDivider > 0) {
                    if (north.dist < 20 && west.dist < 20) {
                        junctionShape = JunctionShape.L_BEND_NORTHWEST;
                    } else if (north.dist < 20 && east.dist < 20) {
                        junctionShape = JunctionShape.L_BEND_NORTHEAST;
                    } else if (south.dist < 20 && east.dist < 20) {
                        junctionShape = JunctionShape.L_BEND_SOUTHEAST;
                    } else if (south.dist < 20 && west.dist < 20) {
                        junctionShape = JunctionShape.L_BEND_SOUTHWEST;
                    }

                    if (junctionShape == JunctionShape.L_BEND_SOUTHEAST)
                        quadrant = (verticalDivider == 1) ? (horizontalDivider == 1 ? "TOP_LEFT" : "BOTTOM_LEFT") : (horizontalDivider == 1 ? "TOP_RIGHT" : "BOTTOM_RIGHT");
                    else if (junctionShape == JunctionShape.L_BEND_SOUTHWEST)
                        quadrant = (verticalDivider == 1) ? (horizontalDivider == 1 ? "BOTTOM_LEFT" : "BOTTOM_RIGHT") : (horizontalDivider == 1 ? "TOP_LEFT" : "TOP_RIGHT");
                    else if (junctionShape == JunctionShape.L_BEND_NORTHWEST)
                        quadrant = (verticalDivider == 1) ? (horizontalDivider == 1 ? "BOTTOM_RIGHT" : "TOP_RIGHT") : (horizontalDivider == 1 ? "BOTTOM_LEFT" : "TOP_LEFT");
                    else if (junctionShape == JunctionShape.L_BEND_NORTHEAST)
                        quadrant = (verticalDivider == 1) ? (horizontalDivider == 1 ? "TOP_RIGHT" : "TOP_LEFT") : (horizontalDivider == 1 ? "BOTTOM_RIGHT" : "BOTTOM_LEFT");
                } else if (verticalDivider > 0) {
                    junctionShape = (verticalDivider == 3) ? JunctionShape.L_ODD_NORTH_SOUTH : (verticalDivider == 1 ? JunctionShape.L_EVEN_NORTH_SOUTH_LEFT : JunctionShape.L_EVEN_NORTH_SOUTH_RIGHT);
                } else if (horizontalDivider > 0) {
                    junctionShape = (horizontalDivider == 3) ? JunctionShape.L_ODD_EAST_WEST : (horizontalDivider == 1 ? JunctionShape.L_EVEN_EAST_WEST_LEFT : JunctionShape.L_EVEN_EAST_WEST_RIGHT);
                }
            }
        }

        if (roadWidth == 1 && totalConnections == 3) {
            if (north.dist >= 20) junctionShape = JunctionShape.T_NORTH;
            else if (south.dist >= 20) junctionShape = JunctionShape.T_SOUTH;
            else if (east.dist >= 20) junctionShape = JunctionShape.T_EAST;
            else if (west.dist >= 20) junctionShape = JunctionShape.T_WEST;
        }

        return getRoadTexture(state, junctionShape, quadrant, corner, edge, false, !isRoad(level, pos.north()), !isRoad(level, pos.south()), !isRoad(level, pos.west()), !isRoad(level, pos.east()), 0, 0, roadWidth);
    }

    private BlockState getSlopeInheritance(Level level, BlockPos pos, BlockState state, Direction.Axis axis, RoadTexture inherited) {
        SlopeState slope = SlopeState.NONE;
        if (axis == Direction.Axis.X) {
            if (isRoadAxis(level, pos.east(), axis)) slope = SlopeState.STRAIGHT_EAST;
            else if (isRoadAxis(level, pos.west(), axis)) slope = SlopeState.STRAIGHT_WEST;
        } else if (axis == Direction.Axis.Z) {
            if (isRoadAxis(level, pos.north(), axis)) slope = SlopeState.STRAIGHT_NORTH;
            else if (isRoadAxis(level, pos.south(), axis)) slope = SlopeState.STRAIGHT_SOUTH;
        }
        return state.setValue(SLOPE, slope).setValue(TEXTURE, inherited);
    }

    private BlockState getRoadTexture(BlockState state, JunctionShape junctionShape, String quadrant, ShoulderCorner corner, ShoulderEdge edge, boolean crosswalk, boolean northRoad, boolean southRoad, boolean westRoad, boolean eastRoad, int leftWidth, int rightWidth, int totalWidth) {
        Direction.Axis axis = state.getValue(AXIS);
        RoadTexture texture;

        if(axis.isVertical()) {
            texture = switch(junctionShape) {
                case NONE -> resolveVerticalShoulder(corner, edge);
                case L_ODD_NORTH_SOUTH -> RoadTexture.ODD_DIVIDER_Y_NORTHSOUTH;
                case L_ODD_EAST_WEST -> RoadTexture.ODD_DIVIDER_Y_EASTWEST;
                case L_EVEN_NORTH_SOUTH_LEFT -> RoadTexture.EVEN_DIVIDER_LEFT_Y_NORTHSOUTH;
                case L_EVEN_NORTH_SOUTH_RIGHT -> RoadTexture.EVEN_DIVIDER_RIGHT_Y_NORTHSOUTH;
                case L_EVEN_EAST_WEST_LEFT -> RoadTexture.EVEN_DIVIDER_LEFT_Y_EASTWEST;
                case L_EVEN_EAST_WEST_RIGHT -> RoadTexture.EVEN_DIVIDER_RIGHT_Y_EASTWEST;
                default -> resolveJunctionTexture(junctionShape, quadrant, totalWidth);
            };
        } else {
            texture = resolveHorizontalTexture(crosswalk, totalWidth, northRoad || westRoad, southRoad || eastRoad, leftWidth);
        }

        return state.setValue(TEXTURE, texture);
    }

    private boolean isRoad(Level level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof RoadBlock;
    }

    private boolean isRoadAxis(Level level, BlockPos pos, Direction.Axis axis) {
        return level.getBlockState(pos).is(this) && level.getBlockState(pos).getValue(AXIS) == axis;
    }

    private boolean isRoadHorizontal(Level level, BlockPos pos) {
        return level.getBlockState(pos).is(this) && level.getBlockState(pos).getValue(AXIS).isHorizontal();
    }

    private RoadTexture resolveCorner(ShoulderCorner corner, RoadTexture inside, RoadTexture cardinal1, RoadTexture cardinal2, RoadTexture outside) {
        if (corner.name().equals(outside.name().substring(24))) return inside;
        if (corner == ShoulderCorner.NORTH || corner == ShoulderCorner.SOUTH) return cardinal1;
        if (corner == ShoulderCorner.EAST || corner == ShoulderCorner.WEST) return cardinal2;
        return outside;
    }

    private RoadTexture resolveHorizontalTexture(boolean crosswalk, int totalWidth, boolean leftSide, boolean rightSide, int divider) {
        if (crosswalk) {
            if (totalWidth == 1) return RoadTexture.CROSSWALK_SINGLE;
            if (leftSide) return RoadTexture.CROSSWALK_SHOULDER_LEFT;
            if (rightSide) return RoadTexture.CROSSWALK_SHOULDER_RIGHT;
            return RoadTexture.CROSSWALK;
        }

        if (totalWidth == 1) return RoadTexture.SINGLE;
        if (totalWidth == 2) return leftSide ? RoadTexture.SHOULDER_DIVIDER_LEFT : RoadTexture.SHOULDER_DIVIDER_RIGHT;
        if (leftSide) return RoadTexture.SHOULDER_LEFT;
        if (rightSide) return RoadTexture.SHOULDER_RIGHT;

        if (totalWidth % 2 != 0 && divider == totalWidth / 2) return RoadTexture.ODD_DIVIDER;
        if (totalWidth % 2 == 0) {
            if (divider == totalWidth / 2 - 1) return RoadTexture.EVEN_DIVIDER_LEFT;
            if (divider == totalWidth / 2) return RoadTexture.EVEN_DIVIDER_RIGHT;
        }

        return RoadTexture.LANE;
    }

    private RoadTexture resolveJunctionTexture(JunctionShape junctionShape, String quadrant, int totalWidth) {
        String name = junctionShape.name();
        if (name.startsWith("T_")) {
            return RoadTexture.valueOf("SHOULDER_SINGLE_" + name);
        } else if (name.startsWith("L_BEND")) {
            String orient = name.substring(7);
            if (totalWidth == 1) return RoadTexture.valueOf("SHOULDER_SINGLE_L_" + orient);
            if (totalWidth % 2 != 0) return RoadTexture.valueOf("ODD_DIVIDER_L_" + orient);
            String prefix = (totalWidth == 2) ? "DOUBLE_DIVIDER_L_" : "EVEN_DIVIDER_L_";
            return RoadTexture.valueOf(prefix + quadrant + "_" + orient);
        }
        return RoadTexture.LANE;
    }

    private RoadTexture resolveVerticalShoulder(ShoulderCorner corner, ShoulderEdge edge) {
        if (corner == ShoulderCorner.FULL) return RoadTexture.SHOULDER_SINGLE;

        return switch (edge) {
            case NORTHWEST -> resolveCorner(corner, RoadTexture.SHOULDER_INSIDE_CORNER_NORTHWEST, RoadTexture.SHOULDER_EDGE_NORTH, RoadTexture.SHOULDER_EDGE_WEST, RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHWEST);
            case NORTHEAST -> resolveCorner(corner, RoadTexture.SHOULDER_INSIDE_CORNER_NORTHEAST, RoadTexture.SHOULDER_EDGE_NORTH, RoadTexture.SHOULDER_EDGE_EAST, RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHEAST);
            case SOUTHEAST -> resolveCorner(corner, RoadTexture.SHOULDER_INSIDE_CORNER_SOUTHEAST, RoadTexture.SHOULDER_EDGE_SOUTH, RoadTexture.SHOULDER_EDGE_EAST, RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHEAST);
            case SOUTHWEST -> resolveCorner(corner, RoadTexture.SHOULDER_INSIDE_CORNER_SOUTHWEST, RoadTexture.SHOULDER_EDGE_SOUTH, RoadTexture.SHOULDER_EDGE_WEST, RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHWEST);
            case NORTH -> RoadTexture.SHOULDER_EDGE_NORTH;
            case EAST -> RoadTexture.SHOULDER_EDGE_EAST;
            case SOUTH -> RoadTexture.SHOULDER_EDGE_SOUTH;
            case WEST -> RoadTexture.SHOULDER_EDGE_WEST;
            default -> RoadTexture.LANE;
        };
    }

    private record AnchorInfo(int dist, int width, int laneIndex) {}

    private AnchorInfo scanAnchor(Level level, BlockPos pos, Direction dir) {
        for (int i = 1; i <= 16; i++) {
            BlockPos checkPos = pos.relative(dir, i);
            BlockState state = level.getBlockState(checkPos);
            if (!state.is(this)) return new AnchorInfo(99, 1, 0);
            if (state.getValue(AXIS) != Direction.Axis.Y) {
                Direction widthDir = (dir.getAxis() == Direction.Axis.X) ? Direction.NORTH : Direction.WEST;
                int left = 0;
                while (left < 8 && isRoadAxis(level, checkPos.relative(widthDir, left + 1), state.getValue(AXIS))) left++;
                int right = 0;
                while (right < 8 && isRoadAxis(level, checkPos.relative(widthDir.getOpposite(), right + 1), state.getValue(AXIS))) right++;
                return new AnchorInfo(i, left + right + 1, left);
            }
        }
        return new AnchorInfo(99, 1, 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, SLOPE, TEXTURE);
    }

    public enum JunctionShape {
        NONE,
        L_EVEN_NORTH_SOUTH_LEFT,
        L_EVEN_NORTH_SOUTH_RIGHT,
        L_EVEN_EAST_WEST_LEFT,
        L_EVEN_EAST_WEST_RIGHT,
        L_ODD_NORTH_SOUTH,
        L_ODD_EAST_WEST,
        L_BEND_NORTHWEST,
        L_BEND_NORTHEAST,
        L_BEND_SOUTHWEST,
        L_BEND_SOUTHEAST,
        T_NORTH,
        T_SOUTH,
        T_EAST,
        T_WEST;
    }

    public enum RoadTexture implements StringRepresentable {
        CROSSWALK("crosswalk"),
        CROSSWALK_SHOULDER_LEFT("crosswalk_shoulder_left"),
        CROSSWALK_SHOULDER_RIGHT("crosswalk_shoulder_right"),
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
        STRAIGHT_NORTH("straight_north"),
        STRAIGHT_EAST("straight_east"),
        STRAIGHT_SOUTH("straight_south"),
        STRAIGHT_WEST("straight_west");

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