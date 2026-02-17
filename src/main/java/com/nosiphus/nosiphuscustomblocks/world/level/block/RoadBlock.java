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
        if (!level.isClientSide) {
            updateRadius(level, pos, 16);
        }
    }

    private void updateRadius(Level level, BlockPos center, int radius) {
        for (BlockPos target : BlockPos.betweenClosed(center.offset(-radius, -2, -radius), center.offset(radius, 2, radius))) {
            BlockState targetState = level.getBlockState(target);
            if (targetState.is(this)) {
                // Flag 2 prevents recursive updates that cause stutter/crashes
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

    private BlockState calculateState(Level world, BlockPos pos, BlockState state) {
        Direction.Axis currentAxis = state.getValue(AXIS);
        BlockState finalState;

        if (currentAxis == Direction.Axis.Y) {
            finalState = calculateIntersection(world, pos, state);
        } else {
            finalState = calculateStraightRoad(world, pos, state, currentAxis);
        }

        System.out.println("[ROAD DEBUG] Pos: " + pos.toShortString() + " | Axis: " + currentAxis + " | Assigned Texture: " + finalState.getValue(TEXTURE));
        return finalState;
    }

    private BlockState calculateIntersection(Level world, BlockPos pos, BlockState state) {
        boolean roadExistsNorth = isRoad(world, pos.north());
        boolean roadExistsSouth = isRoad(world, pos.south());
        boolean roadExistsEast = isRoad(world, pos.east());
        boolean roadExistsWest = isRoad(world, pos.west());
        boolean roadExistsNW = isRoad(world, pos.north().west());
        boolean roadExistsNE = isRoad(world, pos.north().east());
        boolean roadExistsSW = isRoad(world, pos.south().west());
        boolean roadExistsSE = isRoad(world, pos.south().east());

        int connectedRoads = (roadExistsNorth ? 1 : 0) + (roadExistsSouth ? 1 : 0) + (roadExistsEast ? 1 : 0) + (roadExistsWest ? 1 : 0);

        // STEP 1: 1x1 Determination (No diagonals)
        if (!roadExistsNW && !roadExistsNE && !roadExistsSW && !roadExistsSE) {
            return singleRoad(state, world, pos, connectedRoads);
        }

        // STEP 2: Large Intersection Logic (Odd-Width Only)
        boolean anchorFoundNorth = checkAnchorStrict(world, pos, Direction.NORTH, Direction.Axis.Z);
        boolean anchorFoundSouth = checkAnchorStrict(world, pos, Direction.SOUTH, Direction.Axis.Z);
        boolean anchorFoundEast = checkAnchorStrict(world, pos, Direction.EAST, Direction.Axis.X);
        boolean anchorFoundWest = checkAnchorStrict(world, pos, Direction.WEST, Direction.Axis.X);
        int totalAnchorsFound = (anchorFoundNorth ? 1 : 0) + (anchorFoundSouth ? 1 : 0) + (anchorFoundEast ? 1 : 0) + (anchorFoundWest ? 1 : 0);

        switch (totalAnchorsFound) {
            case 2 -> {
                if ((anchorFoundNorth || anchorFoundSouth) && (anchorFoundEast || anchorFoundWest)) {
                    if (anchorFoundNorth && anchorFoundWest) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_L_SOUTHEAST);
                    if (anchorFoundNorth && anchorFoundEast) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_L_SOUTHWEST);
                    if (anchorFoundSouth && anchorFoundEast) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_L_NORTHWEST);
                    if (anchorFoundSouth && anchorFoundWest) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_L_NORTHEAST);
                }
            }
            case 1 -> {
                if (anchorFoundNorth && isVertexNearby(world, pos, Direction.SOUTH)) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_Y_NORTHSOUTH);
                if (anchorFoundSouth && isVertexNearby(world, pos, Direction.NORTH)) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_Y_NORTHSOUTH);
                if (anchorFoundEast && isVertexNearby(world, pos, Direction.WEST)) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_Y_EASTWEST);
                if (anchorFoundWest && isVertexNearby(world, pos, Direction.EAST)) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_Y_EASTWEST);
            }
        }

        // STEP 3: Fallback (Junction Interiors and Shoulders)
        boolean allEightNeighborsExist = roadExistsNorth && roadExistsSouth && roadExistsEast && roadExistsWest && roadExistsNW && roadExistsNE && roadExistsSW && roadExistsSE;
        if (allEightNeighborsExist) return state.setValue(TEXTURE, RoadTexture.LANE);

        return handleShoulderChain(world, pos, state, connectedRoads);
    }

    private BlockState calculateStraightRoad(Level world, BlockPos pos, BlockState state, Direction.Axis axis) {
        Direction widthScanDirection = (axis == Direction.Axis.X) ? Direction.SOUTH : Direction.EAST;
        Direction trafficFlowDirection = (axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;

        int lanesToLeft = 0, lanesToRight = 0;
        while (lanesToLeft < 6 && isRoadAxis(world, pos.relative(widthScanDirection, lanesToLeft + 1), axis)) lanesToLeft++;
        while (lanesToRight < 6 && isRoadAxis(world, pos.relative(widthScanDirection.getOpposite(), lanesToRight + 1), axis)) lanesToRight++;

        int roadWidth = lanesToLeft + lanesToRight + 1;
        int laneIndex = lanesToLeft;

        // Crosswalk Discovery
        BlockPos forwardBlock = pos.relative(trafficFlowDirection);
        BlockPos backwardBlock = pos.relative(trafficFlowDirection.getOpposite());
        if (isRoadAxis(world, forwardBlock, Direction.Axis.Y) || isRoadAxis(world, backwardBlock, Direction.Axis.Y)) {
            BlockPos hubPosition = isRoadAxis(world, forwardBlock, Direction.Axis.Y) ? forwardBlock : backwardBlock;
            int hubRoadConnections = 0;
            for (Direction searchDir : Direction.Plane.HORIZONTAL) {
                for (int distance = 1; distance <= 8; distance++) {
                    BlockState hubNeighbor = world.getBlockState(hubPosition.relative(searchDir, distance));
                    if (!hubNeighbor.is(this)) break;
                    if (hubNeighbor.getValue(AXIS) != Direction.Axis.Y) {
                        hubRoadConnections++;
                        break;
                    }
                }
            }
            if (hubRoadConnections >= 3) {
                if (roadWidth == 1) return state.setValue(TEXTURE, RoadTexture.CROSSWALK_SINGLE);
                if (laneIndex == 0) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CROSSWALK_LEFT);
                if (laneIndex == roadWidth - 1) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CROSSWALK_RIGHT);
                return state.setValue(TEXTURE, RoadTexture.CROSSWALK);
            }
        }

        // Divider and Shoulder Selection
        if (roadWidth >= 3 && roadWidth % 2 != 0 && laneIndex == roadWidth / 2) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER);
        if (roadWidth == 1) return state.setValue(TEXTURE, RoadTexture.SINGLE);
        if (roadWidth == 2) return state.setValue(TEXTURE, (laneIndex == 0) ? RoadTexture.SHOULDER_DIVIDER_LEFT : RoadTexture.SHOULDER_DIVIDER_RIGHT);
        if (laneIndex == 0) return state.setValue(TEXTURE, RoadTexture.SHOULDER_LEFT);
        if (laneIndex == roadWidth - 1) return state.setValue(TEXTURE, RoadTexture.SHOULDER_RIGHT);

        if (roadWidth >= 4 && roadWidth % 2 == 0) {
            if (laneIndex == (roadWidth / 2) - 1) return state.setValue(TEXTURE, RoadTexture.EVEN_DIVIDER_LEFT);
            if (laneIndex == roadWidth / 2) return state.setValue(TEXTURE, RoadTexture.EVEN_DIVIDER_RIGHT);
        }
        return state.setValue(TEXTURE, RoadTexture.LANE);
    }

    private BlockState handleShoulderChain(Level world, BlockPos pos, BlockState state, int connectionCount) {
        boolean roadExistsN = isRoad(world, pos.north());
        boolean roadExistsS = isRoad(world, pos.south());
        boolean roadExistsE = isRoad(world, pos.east());
        boolean roadExistsW = isRoad(world, pos.west());

        if (isRoadAxis(world, pos.south(), Direction.Axis.Z) && isRoadAxis(world, pos.east(), Direction.Axis.X)) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHWEST);
        if (isRoadAxis(world, pos.south(), Direction.Axis.Z) && isRoadAxis(world, pos.west(), Direction.Axis.X)) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHEAST);
        if (isRoadAxis(world, pos.north(), Direction.Axis.Z) && isRoadAxis(world, pos.west(), Direction.Axis.X)) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHEAST);
        if (isRoadAxis(world, pos.north(), Direction.Axis.Z) && isRoadAxis(world, pos.east(), Direction.Axis.X)) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHWEST);

        switch (connectionCount) {
            case 3 -> {
                if (!roadExistsN) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_NORTH);
                if (!roadExistsS) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_SOUTH);
                if (!roadExistsE) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_EAST);
                return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_WEST);
            }
            case 2 -> {
                if (!roadExistsN && !roadExistsW) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHWEST);
                if (!roadExistsN && !roadExistsE) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHEAST);
                if (!roadExistsS && !roadExistsE) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHEAST);
                return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHWEST);
            }
            default -> { return state.setValue(TEXTURE, RoadTexture.LANE); }
        }
    }

    private BlockState singleRoad(BlockState state, Level level, BlockPos pos, int connectedRoads) {
        boolean north = isRoad(level, pos.north());
        boolean east = isRoad(level, pos.east());
        boolean south = isRoad(level, pos.south());
        boolean west = isRoad(level, pos.west());

        switch (connectedRoads) {
            case 0 -> { return state.setValue(TEXTURE, RoadTexture.SINGLE); }
            case 1 -> { return state.setValue(TEXTURE, RoadTexture.LANE); }
            case 2 -> {
                if (!north && !west) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_NORTHWEST);
                if (!north && !east) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_NORTHEAST);
                if (!south && !east) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_SOUTHEAST);
                return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_SOUTHWEST);
            }
            case 3 -> {
                if (!north) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_NORTH);
                if (!south) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_SOUTH);
                if (!east) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_EAST);
                return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_WEST);
            }
            case 4 -> { return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE); }
            default -> { return state.setValue(TEXTURE, RoadTexture.LANE); }
        }
    }

    private boolean isVertexNearby(Level world, BlockPos pos, Direction scanDir) {
        for (int distance = 1; distance <= 7; distance++) {
            BlockPos checkPos = pos.relative(scanDir, distance);
            if (!isRoadAxis(world, checkPos, Direction.Axis.Y)) break;
            boolean anchorNorth = checkAnchorStrict(world, checkPos, Direction.NORTH, Direction.Axis.Z);
            boolean anchorSouth = checkAnchorStrict(world, checkPos, Direction.SOUTH, Direction.Axis.Z);
            boolean anchorEast = checkAnchorStrict(world, checkPos, Direction.EAST, Direction.Axis.X);
            boolean anchorWest = checkAnchorStrict(world, checkPos, Direction.WEST, Direction.Axis.X);
            if (((anchorNorth ? 1 : 0) + (anchorSouth ? 1 : 0) + (anchorEast ? 1 : 0) + (anchorWest ? 1 : 0)) == 2 && ((anchorNorth || anchorSouth) && (anchorEast || anchorWest))) return true;
        }
        return false;
    }

    private boolean checkAnchorStrict(Level world, BlockPos pos, Direction scanDir, Direction.Axis anchorAxis) {
        Direction widthDir = (anchorAxis == Direction.Axis.X) ? Direction.NORTH : Direction.WEST;
        for (int distance = 1; distance <= 16; distance++) {
            BlockPos scanPos = pos.relative(scanDir, distance);
            BlockState scanState = world.getBlockState(scanPos);
            if (!scanState.is(this)) break;
            if (scanState.getValue(AXIS) == anchorAxis) {
                int leftLanes = 0, rightLanes = 0;
                while (leftLanes < 6 && isRoadAxis(world, scanPos.relative(widthDir, leftLanes + 1), anchorAxis)) leftLanes++;
                while (rightLanes < 6 && isRoadAxis(world, scanPos.relative(widthDir.getOpposite(), rightLanes + 1), anchorAxis)) rightLanes++;
                int roadWidth = leftLanes + rightLanes + 1;
                return distance <= (roadWidth + 1) / 2 && roadWidth % 2 != 0 && leftLanes == rightLanes;
            }
            if (scanState.getValue(AXIS) != Direction.Axis.Y) break;
        }
        return false;
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