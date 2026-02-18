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
        if (!level.isClientSide) updateRadius(level, pos, 16);
    }

    private void updateRadius(Level level, BlockPos center, int radius) {
        for (BlockPos target : BlockPos.betweenClosed(center.offset(-radius, -2, -radius), center.offset(radius, 2, radius))) {
            BlockState targetState = level.getBlockState(target);
            if (targetState.is(this)) {
                level.setBlock(target, this.calculateState(level, target, targetState), 2);
            }
        }
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.calculateState(context.getLevel(), context.getClickedPos(), this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (level instanceof Level world && !world.isClientSide) return this.calculateState(world, pos, state);
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    private BlockState calculateState(Level level, BlockPos pos, BlockState state) {
        Direction.Axis axis = state.getValue(AXIS);
        BlockState finalState;

        if (axis == Direction.Axis.Y) {
            return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE);
        } else {
            finalState = calculateStraightRoad(level, pos, state, axis);
        }
        return finalState;
    }

    private BlockState calculateStraightRoad(Level world, BlockPos pos, BlockState state, Direction.Axis axis) {
        // --- STEP 1: GATHER DATA ---
        Direction widthDir = (axis == Direction.Axis.X) ? Direction.SOUTH : Direction.EAST;
        Direction flowDir = (axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;

        // Determine positioning within the road
        int lanesLeft = 0;
        while (lanesLeft < 8 && isRoadAxis(world, pos.relative(widthDir, lanesLeft + 1), axis)) lanesLeft++;
        int lanesRight = 0;
        while (lanesRight < 8 && isRoadAxis(world, pos.relative(widthDir.getOpposite(), lanesRight + 1), axis)) lanesRight++;

        int totalWidth = lanesLeft + lanesRight + 1;
        int currentLaneIndex = lanesLeft; // 0 is one edge, totalWidth-1 is the other

        // --- STEP 2: ISOLATION FILTERS ---
        // 1x1 Detection: Neighbors on both sides are non-roads
        if (!isRoadAxis(world, pos.relative(widthDir), axis) && !isRoadAxis(world, pos.relative(widthDir.getOpposite()), axis)) {
            return handleSingleStraight(world, pos, state, flowDir);
        }

        // 2x2 Detection: Edge on one side, scan 2 blocks opposite to find other edge
        if (totalWidth == 2) {
            return handleDoubleStraight(world, pos, state, axis, flowDir, currentLaneIndex);
        }

        // --- STEP 3-5: MULTIBLOCK LOGIC ---
        // Crosswalk Test
        BlockPos forwardHub = pos.relative(flowDir);
        BlockPos backwardHub = pos.relative(flowDir.getOpposite());
        boolean isNearHub = isRoadAxis(world, forwardHub, Direction.Axis.Y) || isRoadAxis(world, backwardHub, Direction.Axis.Y);

        if (isNearHub) {
            BlockPos hubPos = isRoadAxis(world, forwardHub, Direction.Axis.Y) ? forwardHub : backwardHub;
            int hubConnections = countHubConnections(world, hubPos, totalWidth);

            if (hubConnections >= 3) {
                // Edge Test (integrated into crosswalk result)
                if (currentLaneIndex == 0) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CROSSWALK_LEFT);
                if (currentLaneIndex == totalWidth - 1) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CROSSWALK_RIGHT);
                return state.setValue(TEXTURE, RoadTexture.CROSSWALK);
            }
        }

        // Edge Test (Standard Shoulder)
        if (currentLaneIndex == 0) return state.setValue(TEXTURE, RoadTexture.SHOULDER_LEFT);
        if (currentLaneIndex == totalWidth - 1) return state.setValue(TEXTURE, RoadTexture.SHOULDER_RIGHT);

        // Divider Test
        if (totalWidth >= 3 && totalWidth % 2 != 0 && currentLaneIndex == totalWidth / 2) {
            return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER);
        }
        if (totalWidth >= 4 && totalWidth % 2 == 0) {
            if (currentLaneIndex == (totalWidth / 2) - 1) return state.setValue(TEXTURE, RoadTexture.EVEN_DIVIDER_LEFT);
            if (currentLaneIndex == totalWidth / 2) return state.setValue(TEXTURE, RoadTexture.EVEN_DIVIDER_RIGHT);
        }

        // --- STEP 6: FALLBACK ---
        return state.setValue(TEXTURE, RoadTexture.LANE);
    }

    private BlockState handleSingleStraight(Level world, BlockPos pos, BlockState state, Direction flowDir) {
        BlockPos forwardHub = pos.relative(flowDir);
        BlockPos backwardHub = pos.relative(flowDir.getOpposite());

        if (isRoadAxis(world, forwardHub, Direction.Axis.Y) || isRoadAxis(world, backwardHub, Direction.Axis.Y)) {
            BlockPos hub = isRoadAxis(world, forwardHub, Direction.Axis.Y) ? forwardHub : backwardHub;
            if (countSimpleHubConnections(world, hub) >= 3) {
                return state.setValue(TEXTURE, RoadTexture.CROSSWALK_SINGLE);
            }
        }
        return state.setValue(TEXTURE, RoadTexture.SINGLE);
    }

    private BlockState handleDoubleStraight(Level world, BlockPos pos, BlockState state, Direction.Axis axis, Direction flowDir, int laneIndex) {
        BlockPos forwardHub = pos.relative(flowDir);
        BlockPos backwardHub = pos.relative(flowDir.getOpposite());

        if (isRoadAxis(world, forwardHub, Direction.Axis.Y) || isRoadAxis(world, backwardHub, Direction.Axis.Y)) {
            BlockPos hub = isRoadAxis(world, forwardHub, Direction.Axis.Y) ? forwardHub : backwardHub;
            if (countHubConnections(world, hub, 2) >= 3) {
                // Returns shoulder crosswalk because 2x2 is "all edge"
                return state.setValue(TEXTURE, laneIndex == 0 ? RoadTexture.SHOULDER_CROSSWALK_LEFT : RoadTexture.SHOULDER_CROSSWALK_RIGHT);
            }
        }
        // Fallback to even dividers (shoulder variants)
        return state.setValue(TEXTURE, laneIndex == 0 ? RoadTexture.SHOULDER_DIVIDER_LEFT : RoadTexture.SHOULDER_DIVIDER_RIGHT);
    }

    private int countHubConnections(Level world, BlockPos hubPos, int roadWidth) {
        int connections = 0;
        int scanRange = roadWidth + 1;

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            for (int i = 1; i <= scanRange; i++) {
                BlockPos checkPos = hubPos.relative(dir, i);
                BlockState checkState = world.getBlockState(checkPos);

                if (!checkState.is(this)) continue; // Keep scanning if it's air or other blocks

                // If we hit an X or Z road, count it and stop scanning this direction
                if (checkState.getValue(AXIS) != Direction.Axis.Y) {
                    connections++;
                    break;
                }
            }
        }
        return connections;
    }

    private int countSimpleHubConnections(Level world, BlockPos hubPos) {
        int connections = 0;
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            if (isRoad(world, hubPos.relative(dir)) && world.getBlockState(hubPos.relative(dir)).getValue(AXIS) != Direction.Axis.Y) {
                connections++;
            }
        }
        return connections;
    }

    private boolean isRoadAxis(Level level, BlockPos pos, Direction.Axis axis) {
        BlockState state = level.getBlockState(pos);
        return state.is(this) && state.getValue(AXIS) == axis;
    }
    private boolean isRoad(Level level, BlockPos pos) {
        return level.getBlockState(pos).is(this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, TEXTURE);
    }
}