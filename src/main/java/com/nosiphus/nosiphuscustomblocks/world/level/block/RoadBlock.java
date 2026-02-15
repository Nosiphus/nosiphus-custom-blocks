package com.nosiphus.nosiphuscustomblocks.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
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
        EVEN_DIVIDER_LEFT("even_divider_left"),
        EVEN_DIVIDER_RIGHT("even_divider_right"),
        LANE("lane"),
        ODD_DIVIDER("odd_divider"),
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

        // --- Y-AXIS (Intersection / Hub / Turn Logic) ---
        if (axis == Direction.Axis.Y) {
            boolean n = isRoad(world, pos.north()), s = isRoad(world, pos.south());
            boolean e = isRoad(world, pos.east()), w = isRoad(world, pos.west());

            // Diagonal neighbors used for the 1x1 isolation test
            boolean nw = isRoad(world, pos.north().west()), ne = isRoad(world, pos.north().east());
            boolean sw = isRoad(world, pos.south().west()), se = isRoad(world, pos.south().east());

            int cards = (n?1:0) + (s?1:0) + (e?1:0) + (w?1:0);

            // 1. ISOLATED 1x1 LOGIC
            // If all four diagonals are empty of roads, it is a single-block junction or turn.
            if (!nw && !ne && !sw && !se) {
                // A. 1x1 Hub (Crossroads)
                if (cards == 4) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE);

                // B. 1x1 T-Junctions
                if (cards == 3) {
                    if (!n) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_NORTH);
                    if (!s) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_SOUTH);
                    if (!e) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_EAST);
                    if (!w) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_WEST);
                }

                // C. 1x1 L-Turns
                if (cards == 2) {
                    if (!n && !w) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_NORTHWEST);
                    if (!n && !e) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_NORTHEAST);
                    if (!s && !e) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_SOUTHEAST);
                    if (!s && !w) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_SOUTHWEST);
                }

                return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE);
            }

            // 2. MULTI-LANE / LARGE ROAD LOGIC
            // Triggered if any diagonals exist, indicating a multi-lane junction.

            // A. Interior Asphalt: All 8 neighbors present
            if (n && s && e && w && nw && ne && sw && se) return state.setValue(TEXTURE, RoadTexture.LANE);

            // B. Edge Logic: Large road perimeter
            if (cards == 3) {
                if (!n) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_NORTH);
                if (!s) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_SOUTH);
                if (!e) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_EAST);
                if (!w) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_WEST);
            }

            // C. Outside Corner: Far edges of multi-lane turns
            if (cards == 2) {
                if (!n && !w) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHWEST);
                if (!n && !e) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHEAST);
                if (!s && !e) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHEAST);
                if (!s && !w) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHWEST);
            }

            // D. Inside Corner / Vertex: Meeting point of X and Z roads in large junctions
            if (isRoadAxis(world, pos.south(), Direction.Axis.Z) && isRoadAxis(world, pos.east(), Direction.Axis.X))
                return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHWEST);
            if (isRoadAxis(world, pos.south(), Direction.Axis.Z) && isRoadAxis(world, pos.west(), Direction.Axis.X))
                return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHEAST);
            if (isRoadAxis(world, pos.north(), Direction.Axis.Z) && isRoadAxis(world, pos.west(), Direction.Axis.X))
                return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHEAST);
            if (isRoadAxis(world, pos.north(), Direction.Axis.Z) && isRoadAxis(world, pos.east(), Direction.Axis.X))
                return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHWEST);

            // E. Hub Logic: 4-way intersection centers
            if (cards == 4 && isY(world, pos.north()) && isY(world, pos.south()) && isY(world, pos.east()) && isY(world, pos.west())) {
                return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE);
            }

            return state.setValue(TEXTURE, RoadTexture.LANE);
        }

        // --- X/Z-AXIS (Standard Road Logic) ---
        Direction widthDir = (axis == Direction.Axis.X) ? Direction.SOUTH : Direction.EAST;
        Direction flowDir = (axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;

        int left = 0, right = 0;
        while (left < 6 && isRoadAxis(world, pos.relative(widthDir, left + 1), axis)) left++;
        while (right < 6 && isRoadAxis(world, pos.relative(widthDir.getOpposite(), right + 1), axis)) right++;
        int width = left + right + 1, myPos = left;

        // Crosswalk detection: Requires a Y-axis block with at least 3 connections
        if (isTrueIntersection(world, pos.relative(flowDir)) || isTrueIntersection(world, pos.relative(flowDir.getOpposite()))) {
            if (width == 1) return state.setValue(TEXTURE, RoadTexture.CROSSWALK_SINGLE);
            if (myPos == 0) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CROSSWALK_LEFT);
            if (myPos == width - 1) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CROSSWALK_RIGHT);
            return state.setValue(TEXTURE, RoadTexture.CROSSWALK);
        }

        // Standard road markings logic
        if (width == 1) return state.setValue(TEXTURE, RoadTexture.SINGLE);
        if (width == 2) return state.setValue(TEXTURE, (myPos == 0) ? RoadTexture.SHOULDER_DIVIDER_LEFT : RoadTexture.SHOULDER_DIVIDER_RIGHT);
        if (myPos == 0) return state.setValue(TEXTURE, RoadTexture.SHOULDER_LEFT);
        if (myPos == width - 1) return state.setValue(TEXTURE, RoadTexture.SHOULDER_RIGHT);
        if (width % 2 != 0 && myPos == width / 2) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER);
        if (width % 2 == 0) {
            if (myPos == (width / 2) - 1) return state.setValue(TEXTURE, RoadTexture.EVEN_DIVIDER_LEFT);
            if (myPos == width / 2) return state.setValue(TEXTURE, RoadTexture.EVEN_DIVIDER_RIGHT);
        }
        return state.setValue(TEXTURE, RoadTexture.LANE);
    }

    private boolean isRoad(Level world, BlockPos pos) {
        return world.getBlockState(pos).is(this);
    }

    private boolean isY(Level world, BlockPos pos) {
        return isRoadAxis(world, pos, Direction.Axis.Y);
    }

    private boolean isRoadAxis(Level world, BlockPos pos, Direction.Axis axis) {
        BlockState state = world.getBlockState(pos);
        return state.is(this) && state.getValue(AXIS) == axis;
    }
    private boolean isTrueIntersection(Level world, BlockPos pos) {
        if (!isY(world, pos)) return false;
        return (isRoad(world, pos.north())?1:0) + (isRoad(world, pos.south())?1:0) + (isRoad(world, pos.east())?1:0) + (isRoad(world, pos.west())?1:0) >= 3;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, TEXTURE);
    }
}
