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

/**
 * A dynamic road block system that automatically determines textures based on
 * surrounding geometry, supporting multiblock intersections from 1x1 to 8x8+.
 */
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

    // --- CORE OVERRIDES ---

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide) {
            updateRadius(level, pos, 16);
        }
    }

    /**
     * Recursively updates all road blocks within a specific radius to ensure
     * the multiblock logic remains synchronized after a placement change.
     */
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
        if (level instanceof Level world && !world.isClientSide) {
            return this.calculateState(world, pos, state);
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    // --- TOP-LEVEL LOGIC ---

    /**
     * The entry point for all blockstate logic. Diverts based on the block's Axis property.
     */
    private BlockState calculateState(Level world, BlockPos pos, BlockState state) {
        Direction.Axis axis = state.getValue(AXIS);
        if (axis == Direction.Axis.Y) {
            return calculateIntersection(world, pos, state);
        } else {
            return calculateStraightRoad(world, pos, state, axis);
        }
    }

    /**
     * The primary decision engine for Axis.Y (Intersection) blocks.
     */
    private BlockState calculateIntersection(Level world, BlockPos pos, BlockState state) {
        // 1. ISOLATION FILTERS: Handle standalone blocks
        if (!isRoad(world, pos.north().east()) && !isRoad(world, pos.north().west()) &&
                !isRoad(world, pos.south().east()) && !isRoad(world, pos.south().west())) {
            return handleSingleIntersection(world, pos, state);
        }

        // 2. 2x2 HUB HANDSHAKE: Specialized cluster logic
        if (isDoubleHub(world, pos)) {
            return handleDoubleIntersection(world, pos, state);
        }

        // 3. MULTIBLOCK PERIMETER FILTER: Handles shoulders/sidewalks for large intersections
        int diagNonRoads = countDiagonalNonRoads(world, pos);
        if (diagNonRoads > 0) {
            return resolveShoulderTexture(world, pos, state, diagNonRoads);
        }

        // 4. MULTIBLOCK DISCOVERY: Logic for center-road dividers and lanes
        int anchorNorth = getConnectedAnchorIndex(world, pos, Direction.NORTH);
        int anchorSouth = getConnectedAnchorIndex(world, pos, Direction.SOUTH);
        int anchorEast = getConnectedAnchorIndex(world, pos, Direction.EAST);
        int anchorWest = getConnectedAnchorIndex(world, pos, Direction.WEST);

        int connectionCount = (anchorNorth != 0 ? 1 : 0) + (anchorSouth != 0 ? 1 : 0) +
                (anchorEast != 0 ? 1 : 0) + (anchorWest != 0 ? 1 : 0);

        // Junction Shield: T-Junctions and 4-Ways default to clean asphalt center
        if (connectionCount > 2) {
            return state.setValue(TEXTURE, RoadTexture.LANE);
        }

        // Multiblock L-Turn Paint Logic
        boolean isPerpendicular = (anchorNorth != 0 || anchorSouth != 0) && (anchorEast != 0 || anchorWest != 0);
        if (connectionCount == 2 && isPerpendicular) {
            if (anchorNorth > 0 || anchorSouth > 0 || anchorEast > 0 || anchorWest > 0) {
                return handleLTurnPaint(world, pos, state, anchorNorth, anchorSouth, anchorEast, anchorWest);
            }
        }

        return state.setValue(TEXTURE, RoadTexture.LANE);
    }

    // --- HUB SPECIALISTS ---

    /**
     * Specialized handler for 2x2 clusters using the structural handshake.
     */
    private BlockState handleDoubleIntersection(Level world, BlockPos pos, BlockState state) {
        int anchorNorth = getConnectedAnchorIndex(world, pos, Direction.NORTH);
        int anchorSouth = getConnectedAnchorIndex(world, pos, Direction.SOUTH);
        int anchorEast = getConnectedAnchorIndex(world, pos, Direction.EAST);
        int anchorWest = getConnectedAnchorIndex(world, pos, Direction.WEST);

        int connectionCount = (anchorNorth != 0 ? 1 : 0) + (anchorSouth != 0 ? 1 : 0) +
                (anchorEast != 0 ? 1 : 0) + (anchorWest != 0 ? 1 : 0);
        boolean isPerpendicular = (anchorNorth != 0 || anchorSouth != 0) && (anchorEast != 0 || anchorWest != 0);

        if (connectionCount == 0) {
            return resolveShoulderTexture(world, pos, state, 3);
        }

        if (connectionCount == 1) {
            Direction roadDir = (anchorNorth != 0) ? Direction.NORTH : (anchorSouth != 0) ? Direction.SOUTH :
                    (anchorEast != 0) ? Direction.EAST : Direction.WEST;
            return resolveDoubleStub(world, pos, state, roadDir);
        }

        if (connectionCount == 2 && isPerpendicular) {
            int vIdxRaw = (anchorNorth != 0) ? anchorNorth : anchorSouth;
            Direction verticalDir = (anchorNorth != 0) ? Direction.NORTH : Direction.SOUTH;
            int hIdxRaw = (anchorEast != 0) ? anchorEast : anchorWest;
            Direction horizontalDir = (anchorEast != 0) ? Direction.EAST : Direction.WEST;

            int vFinal = (vIdxRaw == -1) ? resolveMicroLaneIndex(world, pos, verticalDir) : vIdxRaw;
            int hFinal = (hIdxRaw == -1) ? resolveMicroLaneIndex(world, pos, horizontalDir) : hIdxRaw;

            if (vFinal > 0 && hFinal > 0) {
                String orientation = resolveOrientation(verticalDir, horizontalDir);
                String quadrant = resolveQuadrant(orientation, vFinal, hFinal);
                return state.setValue(TEXTURE, RoadTexture.valueOf("DOUBLE_DIVIDER_L_" + quadrant + "_" + orientation));
            }
        }

        if (connectionCount == 3) return resolveDoubleT(world, pos, state);
        if (connectionCount == 4) return resolveDoubleCross(world, pos, state);

        return resolveDoubleLTurn(world, pos, state);
    }

    /**
     * Assigns high-fidelity divider paint for L-Turns in large multiblock intersections.
     */
    private BlockState handleLTurnPaint(Level world, BlockPos pos, BlockState state, int iN, int iS, int iE, int iW) {
        int vIdx = (iN != 0) ? iN : iS;
        Direction vDir = (iN != 0) ? Direction.NORTH : Direction.SOUTH;
        int hIdx = (iE != 0) ? iE : iW;
        Direction hDir = (iE != 0) ? Direction.EAST : Direction.WEST;

        int distV = getDistToAnchor(world, pos, vDir);
        int distH = getDistToAnchor(world, pos, hDir);
        int roadWidth = determineIntersectionWidth(world, pos);

        float limit = (roadWidth % 2 != 0) ? (roadWidth / 2.0f + 0.5f) : (roadWidth / 2.0f + 1.0f);
        String orientation = resolveOrientation(vDir, hDir);

        if (distV > limit || distH > limit) return state.setValue(TEXTURE, RoadTexture.LANE);

        if (vIdx > 0 && hIdx > 0) {
            if (roadWidth % 2 != 0) {
                if (vIdx == 3 && hIdx == 3) return state.setValue(TEXTURE, RoadTexture.valueOf("ODD_DIVIDER_L_" + orientation));
            } else if (vIdx < 3 && hIdx < 3) {
                String pref = (roadWidth == 2) ? "DOUBLE_DIVIDER_L_" : "EVEN_DIVIDER_L_";
                return state.setValue(TEXTURE, RoadTexture.valueOf(pref + resolveQuadrant(orientation, vIdx, hIdx) + "_" + orientation));
            }
        }

        if (vIdx > 0) {
            if (vIdx == 3) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_Y_NORTHSOUTH);
            return state.setValue(TEXTURE, (vIdx == 1) ? RoadTexture.EVEN_DIVIDER_LEFT_Y_NORTHSOUTH : RoadTexture.EVEN_DIVIDER_RIGHT_Y_NORTHSOUTH);
        }

        if (hIdx > 0) {
            if (hIdx == 3) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_Y_EASTWEST);
            return state.setValue(TEXTURE, (hIdx == 1) ? RoadTexture.EVEN_DIVIDER_LEFT_Y_EASTWEST : RoadTexture.EVEN_DIVIDER_RIGHT_Y_EASTWEST);
        }

        return state.setValue(TEXTURE, RoadTexture.LANE);
    }

    /**
     * Logic for standalone 1x1 road segments or hub segments acting as isolation.
     */
    private BlockState handleSingleIntersection(Level world, BlockPos pos, BlockState state) {
        int connections = countSimpleXzConnections(world, pos);
        if (connections == 2) return resolveSingleLTurn(world, pos, state);
        if (connections == 3) return resolveSingleT(world, pos, state);
        return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE);
    }

    // --- SHAPE RESOLVERS ---

    /**
     * Logic for perimeter shoulder textures (Straight edges, inner corners, and outer corners).
     */
    private BlockState resolveShoulderTexture(Level world, BlockPos pos, BlockState state, int diags) {
        boolean noNorth = !isRoad(world, pos.north());
        boolean noSouth = !isRoad(world, pos.south());
        boolean noEast = !isRoad(world, pos.east());
        boolean noWest = !isRoad(world, pos.west());

        if (diags == 2) {
            if (noNorth) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_NORTH);
            if (noSouth) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_SOUTH);
            if (noEast) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_EAST);
            return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_WEST);
        }

        if (diags == 1) {
            if (!isRoad(world, pos.north().west())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHWEST);
            if (!isRoad(world, pos.north().east())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHEAST);
            if (!isRoad(world, pos.south().west())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHWEST);
            return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHEAST);
        }

        if (!noNorth && !noWest) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHWEST);
        if (!noNorth && !noEast) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHEAST);
        if (!noSouth && !noWest) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHWEST);
        return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHEAST);
    }

    /**
     * Logic for 2x2 "Stub" hubs with exactly one connection.
     */
    private BlockState resolveDoubleStub(Level world, BlockPos pos, BlockState state, Direction roadDir) {
        boolean isNorthPos = isRoadAxis(world, pos.south(), Direction.Axis.Y);
        boolean isWestPos = isRoadAxis(world, pos.east(), Direction.Axis.Y);

        if (roadDir == Direction.NORTH) {
            if (isNorthPos) return state.setValue(TEXTURE, isWestPos ? RoadTexture.SHOULDER_EDGE_WEST : RoadTexture.SHOULDER_EDGE_EAST);
            return state.setValue(TEXTURE, isWestPos ? RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHEAST : RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHWEST);
        }
        if (roadDir == Direction.SOUTH) {
            if (!isNorthPos) return state.setValue(TEXTURE, isWestPos ? RoadTexture.SHOULDER_EDGE_WEST : RoadTexture.SHOULDER_EDGE_EAST);
            return state.setValue(TEXTURE, isWestPos ? RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHEAST : RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHWEST);
        }
        if (roadDir == Direction.EAST) {
            if (!isWestPos) return state.setValue(TEXTURE, isNorthPos ? RoadTexture.SHOULDER_EDGE_NORTH : RoadTexture.SHOULDER_EDGE_SOUTH);
            return state.setValue(TEXTURE, isNorthPos ? RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHEAST : RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHEAST);
        }
        if (roadDir == Direction.WEST) {
            if (isWestPos) return state.setValue(TEXTURE, isNorthPos ? RoadTexture.SHOULDER_EDGE_NORTH : RoadTexture.SHOULDER_EDGE_SOUTH);
            return state.setValue(TEXTURE, isNorthPos ? RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHWEST : RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHWEST);
        }
        return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE);
    }

    /**
     * Logic for 2x2 T-Junction shoulders.
     */
    private BlockState resolveDoubleT(Level world, BlockPos pos, BlockState state) {
        Direction missing = null;
        for (Direction d : Direction.Plane.HORIZONTAL) {
            if (getConnectedAnchorIndex(world, pos, d) == 0) {
                missing = d;
                break;
            }
        }
        if (missing == null) return resolveDoubleCross(world, pos, state);

        boolean isNorthPos = isRoadAxis(world, pos.south(), Direction.Axis.Y);
        boolean isWestPos = isRoadAxis(world, pos.east(), Direction.Axis.Y);

        if ((missing == Direction.NORTH && isNorthPos) || (missing == Direction.SOUTH && !isNorthPos) ||
                (missing == Direction.EAST && !isWestPos) || (missing == Direction.WEST && isWestPos)) {
            if (missing == Direction.NORTH) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_NORTH);
            if (missing == Direction.SOUTH) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_SOUTH);
            if (missing == Direction.EAST) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_EAST);
            return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_WEST);
        }

        if (isNorthPos && isWestPos) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHWEST);
        if (isNorthPos && !isWestPos) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHEAST);
        if (!isNorthPos && isWestPos) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHWEST);
        return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHEAST);
    }

    private BlockState resolveDoubleCross(Level world, BlockPos pos, BlockState state) {
        if (!isRoad(world, pos.north().west())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHWEST);
        if (!isRoad(world, pos.north().east())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHEAST);
        if (!isRoad(world, pos.south().west())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHWEST);
        return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHEAST);
    }

    private BlockState resolveDoubleLTurn(Level world, BlockPos pos, BlockState state) {
        int distN = getDistToAnchor(world, pos, Direction.NORTH);
        int distE = getDistToAnchor(world, pos, Direction.EAST);
        int distS = getDistToAnchor(world, pos, Direction.SOUTH);
        int distW = getDistToAnchor(world, pos, Direction.WEST);
        String orientation = (distS < 3 && distE < 3) ? "NORTHWEST" : (distS < 3 && distW < 3) ? "NORTHEAST" : (distN < 3 && distW < 3) ? "SOUTHEAST" : "SOUTHWEST";
        int vertical = (distN == 1 || distS == 2) ? 1 : 2;
        int horizontal = (distE == 1 || distW == 2) ? 1 : 2;
        return state.setValue(TEXTURE, RoadTexture.valueOf("DOUBLE_DIVIDER_L_" + resolveQuadrant(orientation, vertical, horizontal) + "_" + orientation));
    }

    private BlockState resolveSingleLTurn(Level world, BlockPos pos, BlockState state) {
        boolean north = isRoad(world, pos.north()), south = isRoad(world, pos.south()), east = isRoad(world, pos.east()), west = isRoad(world, pos.west());
        if (south && east) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_NORTHWEST);
        if (south && west) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_NORTHEAST);
        if (north && west) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_SOUTHEAST);
        return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_SOUTHWEST);
    }

    private BlockState resolveSingleT(Level world, BlockPos pos, BlockState state) {
        if (!isRoad(world, pos.north())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_NORTH);
        if (!isRoad(world, pos.south())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_SOUTH);
        if (!isRoad(world, pos.east())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_EAST);
        return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_WEST);
    }

    // --- STRAIGHT ROAD LOGIC ---

    /**
     * logic engine for Axis.X and Axis.Z blocks. Handles lanes, shoulders, and crosswalks.
     */
    private BlockState calculateStraightRoad(Level world, BlockPos pos, BlockState state, Direction.Axis axis) {
        Direction widthDir = (axis == Direction.Axis.X) ? Direction.SOUTH : Direction.EAST;
        Direction flowDir = (axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;

        int leftWidth = 0;
        while (leftWidth < 8 && isRoadAxis(world, pos.relative(widthDir, leftWidth + 1), axis)) leftWidth++;
        int rightWidth = 0;
        while (rightWidth < 8 && isRoadAxis(world, pos.relative(widthDir.getOpposite(), rightWidth + 1), axis)) rightWidth++;

        int totalWidth = leftWidth + rightWidth + 1;
        int laneIndex = leftWidth;

        if (!isRoadAxis(world, pos.relative(widthDir), axis) && !isRoadAxis(world, pos.relative(widthDir.getOpposite()), axis)) {
            return handleSingleIntersection(world, pos, state);
        }
        if (totalWidth == 2) return handleDoubleStraight(world, pos, state, axis, flowDir, laneIndex);

        BlockPos forwardHub = pos.relative(flowDir);
        BlockPos backwardHub = pos.relative(flowDir.getOpposite());
        if (isRoadAxis(world, forwardHub, Direction.Axis.Y) || isRoadAxis(world, backwardHub, Direction.Axis.Y)) {
            BlockPos hubPos = isRoadAxis(world, forwardHub, Direction.Axis.Y) ? forwardHub : backwardHub;
            if (countHubConnections(world, hubPos, totalWidth) >= 3) {
                if (laneIndex == 0) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CROSSWALK_LEFT);
                if (laneIndex == totalWidth - 1) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CROSSWALK_RIGHT);
                return state.setValue(TEXTURE, RoadTexture.CROSSWALK);
            }
        }

        if (laneIndex == 0) return state.setValue(TEXTURE, RoadTexture.SHOULDER_LEFT);
        if (laneIndex == totalWidth - 1) return state.setValue(TEXTURE, RoadTexture.SHOULDER_RIGHT);
        if (totalWidth >= 3 && totalWidth % 2 != 0 && laneIndex == totalWidth / 2) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER);
        if (totalWidth >= 4 && totalWidth % 2 == 0) {
            if (laneIndex == (totalWidth / 2) - 1) return state.setValue(TEXTURE, RoadTexture.EVEN_DIVIDER_LEFT);
            if (laneIndex == totalWidth / 2) return state.setValue(TEXTURE, RoadTexture.EVEN_DIVIDER_RIGHT);
        }
        return state.setValue(TEXTURE, RoadTexture.LANE);
    }

    private BlockState handleDoubleStraight(Level world, BlockPos pos, BlockState state, Direction.Axis axis, Direction flowDir, int laneIndex) {
        BlockPos forward = pos.relative(flowDir), backward = pos.relative(flowDir.getOpposite());
        if (isRoadAxis(world, forward, Direction.Axis.Y) || isRoadAxis(world, backward, Direction.Axis.Y)) {
            BlockPos hubPos = isRoadAxis(world, forward, Direction.Axis.Y) ? forward : backward;
            if (countHubConnections(world, hubPos, 2) >= 3) {
                return state.setValue(TEXTURE, laneIndex == 0 ? RoadTexture.SHOULDER_CROSSWALK_LEFT : RoadTexture.SHOULDER_CROSSWALK_RIGHT);
            }
        }
        return state.setValue(TEXTURE, laneIndex == 0 ? RoadTexture.SHOULDER_DIVIDER_LEFT : RoadTexture.SHOULDER_DIVIDER_RIGHT);
    }

    // --- STRUCTURAL SCANNING ---

    /**
     * The "Laser Scanner". Finds a parent road in a cardinal direction and returns
     * this block's relative lane index (1, 2, 3, or -1). Terminates on non-road gaps.
     */
    private int getConnectedAnchorIndex(Level world, BlockPos pos, Direction scanDir) {
        Direction widthDir = (scanDir.getAxis() == Direction.Axis.X) ? Direction.NORTH : Direction.WEST;
        for (int i = 1; i <= 16; i++) {
            BlockPos checkPos = pos.relative(scanDir, i);
            BlockState currentState = world.getBlockState(checkPos);
            if (!currentState.is(this)) return 0;
            if (currentState.getValue(AXIS) == Direction.Axis.Y) continue;

            Direction.Axis roadAxis = currentState.getValue(AXIS);
            int leftWidth = 0;
            while (leftWidth < 8 && isRoadAxis(world, checkPos.relative(widthDir, leftWidth + 1), roadAxis)) leftWidth++;
            int rightWidth = 0;
            while (rightWidth < 8 && isRoadAxis(world, checkPos.relative(widthDir.getOpposite(), rightWidth + 1), roadAxis)) rightWidth++;

            int totalWidth = leftWidth + rightWidth + 1;
            if (totalWidth % 2 != 0 && leftWidth == rightWidth) return 3;
            if (totalWidth % 2 == 0) {
                if (leftWidth == (totalWidth / 2) - 1) return 1;
                if (leftWidth == totalWidth / 2) return 2;
            }
            return -1;
        }
        return 0;
    }

    /**
     * Helper for 2x2 hubs to identify lane index based on the immediate neighbor.
     */
    private int resolveMicroLaneIndex(Level world, BlockPos pos, Direction scanDir) {
        Direction widthDir = (scanDir.getAxis() == Direction.Axis.X) ? Direction.NORTH : Direction.WEST;
        boolean hasLeft = isRoad(world, pos.relative(widthDir));
        boolean hasRight = isRoad(world, pos.relative(widthDir.getOpposite()));
        if (hasLeft && !hasRight) return 2;
        if (!hasLeft && hasRight) return 1;
        return 0;
    }

    private String resolveQuadrant(String orientation, int verticalIndex, int horizontalIndex) {
        if (orientation.equals("NORTHWEST")) return (verticalIndex == 1) ? (horizontalIndex == 1 ? "TOP_LEFT" : "BOTTOM_LEFT") : (horizontalIndex == 1 ? "TOP_RIGHT" : "BOTTOM_RIGHT");
        if (orientation.equals("NORTHEAST")) return (verticalIndex == 1) ? (horizontalIndex == 1 ? "BOTTOM_LEFT" : "BOTTOM_RIGHT") : (horizontalIndex == 1 ? "TOP_LEFT" : "TOP_RIGHT");
        if (orientation.equals("SOUTHEAST")) return (verticalIndex == 1) ? (horizontalIndex == 1 ? "BOTTOM_RIGHT" : "TOP_RIGHT") : (horizontalIndex == 1 ? "BOTTOM_LEFT" : "TOP_LEFT");
        return (verticalIndex == 1) ? (horizontalIndex == 1 ? "TOP_RIGHT" : "TOP_LEFT") : (horizontalIndex == 1 ? "BOTTOM_RIGHT" : "BOTTOM_LEFT");
    }

    private String resolveOrientation(Direction vertical, Direction horizontal) {
        if (vertical == Direction.SOUTH && horizontal == Direction.EAST) return "NORTHWEST";
        if (vertical == Direction.SOUTH && horizontal == Direction.WEST) return "NORTHEAST";
        if (vertical == Direction.NORTH && horizontal == Direction.WEST) return "SOUTHEAST";
        return "SOUTHWEST";
    }

    // --- GEOMETRY & NEIGHBORS ---

    /**
     * Performs a 4-way handshake between blocks to verify they form a perfect 2x2 cluster.
     */
    private boolean isDoubleHub(Level world, BlockPos pos) {
        Direction vertical = null, horizontal = null;
        int cardinalY = 0;
        for (Direction d : Direction.Plane.HORIZONTAL) {
            if (isRoadAxis(world, pos.relative(d), Direction.Axis.Y)) {
                cardinalY++;
                if (d.getAxis() == Direction.Axis.Z) vertical = d;
                else horizontal = d;
            }
        }
        if (cardinalY != 2 || vertical == null || horizontal == null) return false;
        BlockPos diagPos = pos.relative(vertical).relative(horizontal);
        if (!isRoadAxis(world, diagPos, Direction.Axis.Y)) return false;
        return countCardinalYNeighbors(world, pos.relative(vertical)) == 2 &&
                countCardinalYNeighbors(world, pos.relative(horizontal)) == 2 &&
                countCardinalYNeighbors(world, diagPos) == 2;
    }

    private int countCardinalYNeighbors(Level world, BlockPos pos) {
        int count = 0;
        for (Direction d : Direction.Plane.HORIZONTAL) {
            if (isRoadAxis(world, pos.relative(d), Direction.Axis.Y)) count++;
        }
        return count;
    }

    private int determineIntersectionWidth(Level world, BlockPos pos) {
        int distN = getDistToAnchor(world, pos, Direction.NORTH), distS = getDistToAnchor(world, pos, Direction.SOUTH),
                distE = getDistToAnchor(world, pos, Direction.EAST), distW = getDistToAnchor(world, pos, Direction.WEST);
        if (distN < 20 && distS < 20) return distN + distS - 1;
        if (distE < 20 && distW < 20) return distE + distW - 1;
        if (distN < 20) return getRoadWidthAtAnchor(world, pos, Direction.NORTH);
        if (distS < 20) return getRoadWidthAtAnchor(world, pos, Direction.SOUTH);
        if (distE < 20) return getRoadWidthAtAnchor(world, pos, Direction.EAST);
        return getRoadWidthAtAnchor(world, pos, Direction.WEST);
    }

    private int getRoadWidthAtAnchor(Level world, BlockPos pos, Direction dir) {
        for (int i = 1; i <= 16; i++) {
            BlockPos blockPos = pos.relative(dir, i);
            BlockState state = world.getBlockState(blockPos);
            if (!state.is(this)) break;
            if (state.getValue(AXIS) != Direction.Axis.Y) {
                Direction widthDir = (dir.getAxis() == Direction.Axis.X) ? Direction.NORTH : Direction.WEST;
                int leftWidth = 0;
                while (leftWidth < 8 && isRoadAxis(world, blockPos.relative(widthDir, leftWidth + 1), state.getValue(AXIS))) leftWidth++;
                int rightWidth = 0;
                while (rightWidth < 8 && isRoadAxis(world, blockPos.relative(widthDir.getOpposite(), rightWidth + 1), state.getValue(AXIS))) rightWidth++;
                return leftWidth + rightWidth + 1;
            }
        }
        return 1;
    }

    private int getDistToAnchor(Level world, BlockPos pos, Direction scanDir) {
        for (int i = 1; i <= 16; i++) {
            BlockPos blockPos = pos.relative(scanDir, i);
            BlockState state = world.getBlockState(blockPos);
            if (!state.is(this)) return 99;
            if (state.getValue(AXIS) != Direction.Axis.Y) return i;
        } return 99;
    }

    private boolean isRoadAxis(Level level, BlockPos pos, Direction.Axis axis) {
        BlockState state = level.getBlockState(pos);
        return state.is(this) && state.getValue(AXIS) == axis;
    }

    private boolean isRoad(Level level, BlockPos pos) {
        return level.getBlockState(pos).is(this);
    }

    private int countDiagonalNonRoads(Level world, BlockPos pos) {
        int count = 0;
        if (!isRoad(world, pos.north().east())) count++;
        if (!isRoad(world, pos.north().west())) count++;
        if (!isRoad(world, pos.south().east())) count++;
        if (!isRoad(world, pos.south().west())) count++;
        return count;
    }

    private int countSimpleXzConnections(Level world, BlockPos pos) {
        int count = 0;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (isRoad(world, pos.relative(direction)) && world.getBlockState(pos.relative(direction)).getValue(AXIS) != Direction.Axis.Y) {
                count++;
            }
        }
        return count;
    }

    private int countHubConnections(Level world, BlockPos hub, int width) {
        int count = 0;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            for (int i = 1; i <= width + 1; i++) {
                BlockState state = world.getBlockState(hub.relative(direction, i));
                if (state.is(this) && state.getValue(AXIS) != Direction.Axis.Y) {
                    count++;
                    break;
                }
                if (!state.is(this)) break;
            }
        }
        return count;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, TEXTURE);
    }
}