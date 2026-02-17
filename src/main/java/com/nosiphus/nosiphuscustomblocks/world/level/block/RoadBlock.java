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

        int connectedRoadCount = (roadExistsNorth ? 1 : 0) + (roadExistsSouth ? 1 : 0) + (roadExistsEast ? 1 : 0) + (roadExistsWest ? 1 : 0);

        // STEP 1: 1x1 Determination (No diagonals)
        if (!roadExistsNW && !roadExistsNE && !roadExistsSW && !roadExistsSE) {
            return handleSingleRoad(state, world, pos, connectedRoadCount);
        }

        // STEP 2: Anchor Discovery
        int anchorValN = getAnchorValue(world, pos, Direction.NORTH, Direction.Axis.Z);
        int anchorValS = getAnchorValue(world, pos, Direction.SOUTH, Direction.Axis.Z);
        int anchorValE = getAnchorValue(world, pos, Direction.EAST, Direction.Axis.X);
        int anchorValW = getAnchorValue(world, pos, Direction.WEST, Direction.Axis.X);

        int oddAnchors = (anchorValN == 3 ? 1 : 0) + (anchorValS == 3 ? 1 : 0) + (anchorValE == 3 ? 1 : 0) + (anchorValW == 3 ? 1 : 0);
        int evenAnchors = (anchorValN > 0 && anchorValN < 3 ? 1 : 0) + (anchorValS > 0 && anchorValS < 3 ? 1 : 0) + (anchorValE > 0 && anchorValE < 3 ? 1 : 0) + (anchorValW > 0 && anchorValW < 3 ? 1 : 0);

        boolean verticalThrough = (anchorValN > 0 && anchorValS > 0);
        boolean horizontalThrough = (anchorValE > 0 && anchorValW > 0);

        // STEP 3: Junction Exclusion
        if ((oddAnchors + evenAnchors) > 2 || verticalThrough || horizontalThrough) {
            return state.setValue(TEXTURE, RoadTexture.LANE);
        }

        // STEP 4: Odd L-Turn
        if (oddAnchors == 2 && ((anchorValN == 3 || anchorValS == 3) && (anchorValE == 3 || anchorValW == 3))) {
            if (anchorValN == 3 && anchorValW == 3) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_L_SOUTHEAST);
            if (anchorValN == 3 && anchorValE == 3) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_L_SOUTHWEST);
            if (anchorValS == 3 && anchorValE == 3) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_L_NORTHWEST);
            if (anchorValS == 3 && anchorValW == 3) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_L_NORTHEAST);
        }

        // STEP 5: Even L-Turn (The Final Diagonal Swap)
        int verticalPairSide = (anchorValN > 0 && anchorValN < 3) ? anchorValN : (anchorValS > 0 && anchorValS < 3 ? anchorValS : 0);
        int horizontalPairSide = (anchorValE > 0 && anchorValE < 3) ? anchorValE : (anchorValW > 0 && anchorValW < 3 ? anchorValW : 0);

        if (verticalPairSide > 0 && horizontalPairSide > 0) {
            String turnOrientation = "";
            String textureQuadrant = "";

            if (anchorValS > 0 && anchorValE > 0) { // NORTHWEST Turn
                turnOrientation = "NORTHWEST";
                if (verticalPairSide == 1) {
                    textureQuadrant = (horizontalPairSide == 1) ? "TOP_LEFT" : "BOTTOM_LEFT";
                } else {
                    textureQuadrant = (horizontalPairSide == 1) ? "TOP_RIGHT" : "BOTTOM_RIGHT";
                }
            } else if (anchorValS > 0 && anchorValW > 0) { // NORTHEAST Turn
                turnOrientation = "NORTHEAST";
                if (verticalPairSide == 1) {
                    textureQuadrant = (horizontalPairSide == 1) ? "BOTTOM_LEFT" : "BOTTOM_RIGHT";
                } else {
                    textureQuadrant = (horizontalPairSide == 1) ? "TOP_LEFT" : "TOP_RIGHT";
                }
            } else if (anchorValN > 0 && anchorValW > 0) { // SOUTHEAST Turn
                turnOrientation = "SOUTHEAST";
                if (verticalPairSide == 1) {
                    textureQuadrant = (horizontalPairSide == 1) ? "BOTTOM_RIGHT" : "TOP_RIGHT";
                } else {
                    textureQuadrant = (horizontalPairSide == 1) ? "BOTTOM_LEFT" : "TOP_LEFT";
                }
            } else if (anchorValN > 0 && anchorValE > 0) { // SOUTHWEST Turn
                turnOrientation = "SOUTHWEST";
                if (verticalPairSide == 1) {
                    textureQuadrant = (horizontalPairSide == 1) ? "TOP_RIGHT" : "TOP_LEFT";
                } else {
                    textureQuadrant = (horizontalPairSide == 1) ? "BOTTOM_RIGHT" : "BOTTOM_LEFT";
                }
            }

            if (!textureQuadrant.isEmpty()) {
                return state.setValue(TEXTURE, RoadTexture.valueOf("EVEN_DIVIDER_L_" + textureQuadrant + "_" + turnOrientation));
            }
        }

        // STEP 6: Leg Extensions
        if (oddAnchors == 1) {
            Direction dir = anchorValN == 3 ? Direction.SOUTH : (anchorValS == 3 ? Direction.NORTH : (anchorValE == 3 ? Direction.WEST : Direction.EAST));
            if (isVertexNearby(world, pos, dir, true)) return state.setValue(TEXTURE, dir.getAxis() == Direction.Axis.X ? RoadTexture.ODD_DIVIDER_Y_EASTWEST : RoadTexture.ODD_DIVIDER_Y_NORTHSOUTH);
        }
        if (evenAnchors == 1) {
            int currentSide = Math.max(verticalPairSide, horizontalPairSide);
            Direction dir = (anchorValN > 0 ? Direction.SOUTH : (anchorValS > 0 ? Direction.NORTH : (anchorValE > 0 ? Direction.WEST : Direction.EAST)));
            if (isVertexNearby(world, pos, dir, false)) return state.setValue(TEXTURE, currentSide == 1 ? RoadTexture.EVEN_DIVIDER_LEFT : RoadTexture.EVEN_DIVIDER_RIGHT);
        }

        // STEP 7: Shoulders and Fallbacks
        boolean allNeighbors = roadExistsNorth && roadExistsSouth && roadExistsEast && roadExistsWest && roadExistsNW && roadExistsNE && roadExistsSW && roadExistsSE;
        if (allNeighbors) return state.setValue(TEXTURE, RoadTexture.LANE);
        return handleShoulderChain(world, pos, state, connectedRoadCount);
    }

    private int getAnchorValue(Level world, BlockPos pos, Direction scanDir, Direction.Axis roadAxis) {
        Direction widthDir = (roadAxis == Direction.Axis.X) ? Direction.NORTH : Direction.WEST;

        for (int distance = 1; distance <= 16; distance++) {
            BlockPos checkPos = pos.relative(scanDir, distance);
            BlockState checkState = world.getBlockState(checkPos);

            if (!checkState.is(this)) break;

            if (checkState.getValue(AXIS) == roadAxis) {
                int leftLanes = 0, rightLanes = 0;
                while (leftLanes < 8 && isRoadAxis(world, checkPos.relative(widthDir, leftLanes + 1), roadAxis)) leftLanes++;
                while (rightLanes < 8 && isRoadAxis(world, checkPos.relative(widthDir.getOpposite(), rightLanes + 1), roadAxis)) rightLanes++;

                int totalWidth = leftLanes + rightLanes + 1;

                // Odd Width Center
                if (totalWidth % 2 != 0 && leftLanes == rightLanes) return 3;

                // Even Width Center Pair
                if (totalWidth % 2 == 0) {
                    if (leftLanes == (totalWidth / 2) - 1) return 1; // Low Coordinate Side
                    if (leftLanes == (totalWidth / 2)) return 2;     // High Coordinate Side
                }
                break;
            }
            // Stop if we hit a different road block that isn't a Y-axis intersection
            if (checkState.getValue(AXIS) != Direction.Axis.Y) break;
        }
        return 0;
    }

    private RoadTexture resolveEvenLTexture(int v, int h, String turn) {
        // v: 1=West (X-), 2=East (X+)
        // h: 1=North (Z-), 2=South (Z+)
        String quad;
        if (v == 1) {
            quad = (h == 1) ? "TOP_LEFT" : "BOTTOM_LEFT";
        } else {
            quad = (h == 1) ? "TOP_RIGHT" : "BOTTOM_RIGHT";
        }
        return RoadTexture.valueOf("EVEN_DIVIDER_L_" + quad + "_" + turn);
    }

    private boolean isVertexNearby(Level world, BlockPos pos, Direction dir, boolean odd) {
        for (int i = 0; i <= 8; i++) {
            BlockPos c = pos.relative(dir, i);
            if (!isRoad(world, c)) break;
            int n = getAnchorValue(world, c, Direction.NORTH, Direction.Axis.Z), s = getAnchorValue(world, c, Direction.SOUTH, Direction.Axis.Z);
            int e = getAnchorValue(world, c, Direction.EAST, Direction.Axis.X), w = getAnchorValue(world, c, Direction.WEST, Direction.Axis.X);
            boolean v = odd ? (n == 3 || s == 3) : (n > 0 && n < 3 || s > 0 && s < 3);
            boolean h = odd ? (e == 3 || w == 3) : (e > 0 && e < 3 || w > 0 && w < 3);
            if (v && h && !((n > 0 && s > 0) || (e > 0 && w > 0))) return true;
        }
        return false;
    }

    private BlockState calculateStraightRoad(Level world, BlockPos pos, BlockState state, Direction.Axis axis) {
        Direction widthDir = (axis == Direction.Axis.X) ? Direction.SOUTH : Direction.EAST;
        Direction flowDir = (axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;
        int lanesToLeft = 0, lanesToRight = 0;
        while (lanesToLeft < 6 && isRoadAxis(world, pos.relative(widthDir, lanesToLeft + 1), axis)) lanesToLeft++;
        while (lanesToRight < 6 && isRoadAxis(world, pos.relative(widthDir.getOpposite(), lanesToRight + 1), axis)) lanesToRight++;
        int roadWidth = lanesToLeft + lanesToRight + 1;
        int currentLaneIndex = lanesToLeft;

        BlockPos forward = pos.relative(flowDir), backward = pos.relative(flowDir.getOpposite());
        if (isRoadAxis(world, forward, Direction.Axis.Y) || isRoadAxis(world, backward, Direction.Axis.Y)) {
            BlockPos hub = isRoadAxis(world, forward, Direction.Axis.Y) ? forward : backward;
            int hubConnections = 0;
            for (Direction d : Direction.Plane.HORIZONTAL) {
                for (int i = 1; i <= 8; i++) {
                    BlockState s = world.getBlockState(hub.relative(d, i));
                    if (!s.is(this)) break;
                    if (s.getValue(AXIS) != Direction.Axis.Y) { hubConnections++; break; }
                }
            }
            if (hubConnections >= 3) {
                if (roadWidth == 1) return state.setValue(TEXTURE, RoadTexture.CROSSWALK_SINGLE);
                if (currentLaneIndex == 0) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CROSSWALK_LEFT);
                if (currentLaneIndex == roadWidth - 1) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CROSSWALK_RIGHT);
                return state.setValue(TEXTURE, RoadTexture.CROSSWALK);
            }
        }

        if (roadWidth >= 3 && roadWidth % 2 != 0 && currentLaneIndex == roadWidth / 2) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER);
        if (roadWidth == 1) return state.setValue(TEXTURE, RoadTexture.SINGLE);
        if (roadWidth == 2) return state.setValue(TEXTURE, (currentLaneIndex == 0) ? RoadTexture.SHOULDER_DIVIDER_LEFT : RoadTexture.SHOULDER_DIVIDER_RIGHT);
        if (currentLaneIndex == 0) return state.setValue(TEXTURE, RoadTexture.SHOULDER_LEFT);
        if (currentLaneIndex == roadWidth - 1) return state.setValue(TEXTURE, RoadTexture.SHOULDER_RIGHT);
        if (roadWidth >= 4 && roadWidth % 2 == 0) {
            if (currentLaneIndex == (roadWidth / 2) - 1) return state.setValue(TEXTURE, RoadTexture.EVEN_DIVIDER_LEFT);
            if (currentLaneIndex == roadWidth / 2) return state.setValue(TEXTURE, RoadTexture.EVEN_DIVIDER_RIGHT);
        }
        return state.setValue(TEXTURE, RoadTexture.LANE);
    }

    private BlockState handleShoulderChain(Level world, BlockPos pos, BlockState state, int cards) {
        boolean roadN = isRoad(world, pos.north()), roadS = isRoad(world, pos.south()), roadE = isRoad(world, pos.east()), roadW = isRoad(world, pos.west());
        if (isRoadAxis(world, pos.south(), Direction.Axis.Z) && isRoadAxis(world, pos.east(), Direction.Axis.X)) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHWEST);
        if (isRoadAxis(world, pos.south(), Direction.Axis.Z) && isRoadAxis(world, pos.west(), Direction.Axis.X)) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHEAST);
        if (isRoadAxis(world, pos.north(), Direction.Axis.Z) && isRoadAxis(world, pos.west(), Direction.Axis.X)) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHEAST);
        if (isRoadAxis(world, pos.north(), Direction.Axis.Z) && isRoadAxis(world, pos.east(), Direction.Axis.X)) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHWEST);
        switch (cards) {
            case 3 -> {
                if (!roadN) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_NORTH);
                if (!roadS) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_SOUTH);
                if (!roadE) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_EAST);
                return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_WEST);
            }
            case 2 -> {
                if (!roadN && !roadW) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHWEST);
                if (!roadN && !roadE) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHEAST);
                if (!roadS && !roadE) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHEAST);
                return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHWEST);
            }
            default -> { return state.setValue(TEXTURE, RoadTexture.LANE); }
        }
    }

    private BlockState handleSingleRoad(BlockState state, Level level, BlockPos pos, int connectedRoads) {
        boolean north = isRoad(level, pos.north()), east = isRoad(level, pos.east()), south = isRoad(level, pos.south()), west = isRoad(level, pos.west());
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
            default -> { return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE); }
        }
    }

    private boolean isRoadAxis(Level level, BlockPos pos, Direction.Axis axis) {
        BlockState state = level.getBlockState(pos);
        return state.is(this) && state.getValue(AXIS) == axis;
    }
    private boolean isRoad(Level level, BlockPos pos) { return level.getBlockState(pos).is(this); }

    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) { builder.add(AXIS, TEXTURE); }
}