package com.nosiphus.nosiphuscustomblocks.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

public class RoadBlock extends Block {

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final EnumProperty<RoadTexture> TEXTURE = EnumProperty.create("texture", RoadTexture.class);

    public RoadBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AXIS, Direction.Axis.X)
                .setValue(TEXTURE, RoadTexture.LANE));
    }

    public enum RoadTexture implements StringRepresentable {
        CROSSWALK("crosswalk"),
        CROSSWALK_SINGLE("crosswalk_single"),
        EVEN_DIVIDER_L_BOTTOM_LEFT("even_divider_l_bottom_left"),
        EVEN_DIVIDER_L_BOTTOM_RIGHT("even_divider_l_bottom_right"),
        EVEN_DIVIDER_L_TOP_LEFT("even_divider_l_top_left"),
        EVEN_DIVIDER_L_TOP_RIGHT("even_divider_l_top_right"),
        EVEN_DIVIDER_LEFT("even_divider_left"),
        EVEN_DIVIDER_RIGHT("even_divider_right"),
        LANE("lane"),
        ODD_DIVIDER("odd_divider"),
        ODD_DIVIDER_L_NORTHWEST("odd_divider_l_northwest"),
        ODD_DIVIDER_L_NORTHEAST("odd_divider_l_northeast"),
        ODD_DIVIDER_L_SOUTHEAST("odd_divider_l_southeast"),
        ODD_DIVIDER_L_SOUTHWEST("odd_divider_l_southwest"),
        ODD_DIVIDER_Y_NORTHSOUTH("odd_divider_y_northsouth"),
        ODD_DIVIDER_Y_EASTWEST("odd_divider_y_eastwest"),
        SINGLE("single"),
        SHOULDER_CORNER_NORTHWEST("shoulder_corner_northwest"),
        SHOULDER_CORNER_NORTHEAST("shoulder_corner_northeast"),
        SHOULDER_CORNER_SOUTHEAST("shoulder_corner_southeast"),
        SHOULDER_CORNER_SOUTHWEST("shoulder_corner_southwest"),
        SHOULDER_CROSSWALK_LEFT("shoulder_crosswalk_left"),
        SHOULDER_CROSSWALK_RIGHT("shoulder_crosswalk_right"),
        SHOULDER_DIVIDER_LEFT("shoulder_divider_left"),
        SHOULDER_DIVIDER_RIGHT("shoulder_divider_right"),
        SHOULDER_EDGE_NORTH("shoulder_edge_north"),
        SHOULDER_EDGE_SOUTH("shoulder_edge_south"),
        SHOULDER_EDGE_EAST("shoulder_edge_east"),
        SHOULDER_EDGE_WEST("shoulder_edge_west"),
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

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if(!level.isClientSide) {
            updateRadius(level, pos, 16);
        }
    }

    private void updateRadius(Level level, BlockPos center, int radius) {
        for (BlockPos target : BlockPos.betweenClosed(center.offset(-radius, -2, -radius), center.offset(radius, 2, radius))) {
            BlockState state = level.getBlockState(target);
            if (state.is(this)) {
                level.setBlock(target, this.calculateState(level, target, state), 3);
            }
        }
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.calculateState(context.getLevel(), context.getClickedPos(), this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (level instanceof Level world && !world.isClientSide) {
            return this.calculateState(world, pos, state);
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    private BlockState calculateState(Level world, BlockPos pos, BlockState state) {
        Direction.Axis axis = state.getValue(AXIS);

        if (axis == Direction.Axis.Y) {
            boolean n = isRoad(world, pos.north()), s = isRoad(world, pos.south());
            boolean e = isRoad(world, pos.east()), w = isRoad(world, pos.west());
            boolean nw = isRoad(world, pos.north().west()), ne = isRoad(world, pos.north().east());
            boolean sw = isRoad(world, pos.south().west()), se = isRoad(world, pos.south().east());
            int cards = (n?1:0) + (s?1:0) + (e?1:0) + (w?1:0);
            boolean allEight = n && s && e && w && nw && ne && sw && se;

            if (!nw && !ne && !sw && !se) {
                if (cards == 4) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE);
                if (cards == 3) {
                    if (!n) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_NORTH);
                    if (!s) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_SOUTH);
                    if (!e) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_EAST);
                    return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_WEST);
                }
                if (cards == 2) {
                    if (!n && !w) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_NORTHWEST);
                    if (!n && !e) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_NORTHEAST);
                    if (!s && !e) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_SOUTHEAST);
                    return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_SOUTHWEST);
                }
                return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE);
            }

            boolean mN = checkAnchorStrict(world, pos, Direction.NORTH, Direction.Axis.Z);
            boolean mS = checkAnchorStrict(world, pos, Direction.SOUTH, Direction.Axis.Z);
            boolean mE = checkAnchorStrict(world, pos, Direction.EAST, Direction.Axis.X);
            boolean mW = checkAnchorStrict(world, pos, Direction.WEST, Direction.Axis.X);
            int count = (mN?1:0) + (mS?1:0) + (mE?1:0) + (mW?1:0);

            if (count == 2 && ((mN || mS) && (mE || mW))) {
                if (mN && mW) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_L_SOUTHEAST);
                if (mN && mE) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_L_SOUTHWEST);
                if (mS && mE) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_L_NORTHWEST);
                return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_L_NORTHEAST);
            }

            if (count == 1) {
                if (mN && isVertexNearby(world, pos, Direction.SOUTH)) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_Y_NORTHSOUTH);
                if (mS && isVertexNearby(world, pos, Direction.NORTH)) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_Y_NORTHSOUTH);
                if (mE && isVertexNearby(world, pos, Direction.WEST)) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_Y_EASTWEST);
                if (mW && isVertexNearby(world, pos, Direction.EAST)) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_Y_EASTWEST);
            }

            if (allEight) return state.setValue(TEXTURE, RoadTexture.LANE);
            return calculateChain(world, pos, state, n, s, e, w, cards);
        }

        return calculateStandardXZ(world, pos, state, axis);
    }

    private boolean isVertexNearby(Level world, BlockPos pos, Direction scanDir) {
        for (int i = 1; i <= 7; i++) {
            BlockPos check = pos.relative(scanDir, i);
            BlockState s = world.getBlockState(check);
            if (!s.is(this) || s.getValue(AXIS) != Direction.Axis.Y) break;
            boolean mN = checkAnchorStrict(world, check, Direction.NORTH, Direction.Axis.Z);
            boolean mS = checkAnchorStrict(world, check, Direction.SOUTH, Direction.Axis.Z);
            boolean mE = checkAnchorStrict(world, check, Direction.EAST, Direction.Axis.X);
            boolean mW = checkAnchorStrict(world, check, Direction.WEST, Direction.Axis.X);
            if (((mN?1:0) + (mS?1:0) + (mE?1:0) + (mW?1:0)) == 2 && ((mN || mS) && (mE || mW))) return true;
        }
        return false;
    }

    private boolean checkAnchorStrict(Level world, BlockPos pos, Direction scanDir, Direction.Axis anchorAxis) {
        Direction widthDir = (anchorAxis == Direction.Axis.X) ? Direction.NORTH : Direction.WEST;
        for (int i = 1; i <= 16; i++) {
            BlockPos anchorPos = pos.relative(scanDir, i);
            BlockState s = world.getBlockState(anchorPos);
            if (!s.is(this)) break;
            if (s.getValue(AXIS) == anchorAxis) {
                int l = 0, r = 0;
                while (l < 6 && isRoadAxis(world, anchorPos.relative(widthDir, l + 1), anchorAxis)) l++;
                while (r < 6 && isRoadAxis(world, anchorPos.relative(widthDir.getOpposite(), r + 1), anchorAxis)) r++;
                int w = l + r + 1;
                return i <= (w + 1) / 2 && w % 2 != 0 && l == r;
            }
            if (s.getValue(AXIS) != Direction.Axis.Y) break;
        }
        return false;
    }

    private BlockState calculateStandardXZ(Level world, BlockPos pos, BlockState state, Direction.Axis axis) {
        Direction widthDir = (axis == Direction.Axis.X) ? Direction.SOUTH : Direction.EAST;
        Direction flowDir = (axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;
        int l = 0, r = 0;
        while (l < 6 && isRoadAxis(world, pos.relative(widthDir, l + 1), axis)) l++;
        while (r < 6 && isRoadAxis(world, pos.relative(widthDir.getOpposite(), r + 1), axis)) r++;
        int width = l + r + 1, myPos = l;

        boolean isCrosswalk = false;
        BlockPos fwd = pos.relative(flowDir), bwd = pos.relative(flowDir.getOpposite());
        if (isY(world, fwd) || isY(world, bwd)) {
            BlockPos hub = isY(world, fwd) ? fwd : bwd;
            int roadsAroundHub = 0;
            for (Direction d : Direction.Plane.HORIZONTAL) {
                for (int i = 1; i <= 8; i++) {
                    BlockState s = world.getBlockState(hub.relative(d, i));
                    if (!s.is(this)) break;
                    if (s.getValue(AXIS) != Direction.Axis.Y) { roadsAroundHub++; break; }
                }
            }
            if (roadsAroundHub >= 3) isCrosswalk = true;
        }

        if (isCrosswalk) {
            if (width == 1) return state.setValue(TEXTURE, RoadTexture.CROSSWALK_SINGLE);
            if (myPos == 0) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CROSSWALK_LEFT);
            if (myPos == width - 1) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CROSSWALK_RIGHT);
            return state.setValue(TEXTURE, RoadTexture.CROSSWALK);
        }

        if (width >= 3 && width % 2 != 0 && myPos == width / 2) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER);
        if (width == 1) return state.setValue(TEXTURE, RoadTexture.SINGLE);
        if (width == 2) return state.setValue(TEXTURE, (myPos == 0) ? RoadTexture.SHOULDER_DIVIDER_LEFT : RoadTexture.SHOULDER_DIVIDER_RIGHT);
        if (myPos == 0) return state.setValue(TEXTURE, RoadTexture.SHOULDER_LEFT);
        if (myPos == width - 1) return state.setValue(TEXTURE, RoadTexture.SHOULDER_RIGHT);
        if (width >= 4 && width % 2 == 0) {
            if (myPos == (width / 2) - 1) return state.setValue(TEXTURE, RoadTexture.EVEN_DIVIDER_LEFT);
            if (myPos == width / 2) return state.setValue(TEXTURE, RoadTexture.EVEN_DIVIDER_RIGHT);
        }
        return state.setValue(TEXTURE, RoadTexture.LANE);
    }

    private BlockState calculateChain(Level world, BlockPos pos, BlockState state, boolean n, boolean s, boolean e, boolean w, int cards) {
        if (isRoadAxis(world, pos.south(), Direction.Axis.Z) && isRoadAxis(world, pos.east(), Direction.Axis.X)) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHWEST);
        if (isRoadAxis(world, pos.south(), Direction.Axis.Z) && isRoadAxis(world, pos.west(), Direction.Axis.X)) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHEAST);
        if (isRoadAxis(world, pos.north(), Direction.Axis.Z) && isRoadAxis(world, pos.west(), Direction.Axis.X)) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHEAST);
        if (isRoadAxis(world, pos.north(), Direction.Axis.Z) && isRoadAxis(world, pos.east(), Direction.Axis.X)) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHWEST);
        if (cards == 3) {
            if (!n) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_NORTH);
            if (!s) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_SOUTH);
            if (!e) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_EAST);
            return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_WEST);
        }
        if (cards == 2) {
            if (!n && !w) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHWEST);
            if (!n && !e) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHEAST);
            if (!s && !e) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHEAST);
            return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHWEST);
        }
        return state.setValue(TEXTURE, RoadTexture.LANE);
    }

    private boolean isRoadAxis(Level world, BlockPos pos, Direction.Axis axis) {
        BlockState state = world.getBlockState(pos);
        return state.is(this) && state.getValue(AXIS) == axis;
    }
    private boolean isY(Level world, BlockPos pos) { return isRoadAxis(world, pos, Direction.Axis.Y); }
    private boolean isRoad(Level world, BlockPos pos) { return world.getBlockState(pos).is(this); }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, TEXTURE);
    }
}
