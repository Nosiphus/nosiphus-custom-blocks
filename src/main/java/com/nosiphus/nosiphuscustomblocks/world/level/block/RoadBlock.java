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
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

public class RoadBlock extends Block {
    private static final Logger LOGGER = LogUtils.getLogger();
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

    private void logDebug(BlockPos pos, String message) {
        LOGGER.info("[RoadDebug] At {}: {}", pos.toShortString(), message);
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
        if (level instanceof Level world && !world.isClientSide) {
            return this.calculateState(world, pos, state);
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    // --- MAIN LOGIC FLOW ---

    private BlockState calculateState(Level world, BlockPos pos, BlockState state) {
        Direction.Axis axis = state.getValue(AXIS);
        if (axis == Direction.Axis.Y) {
            return calculateIntersection(world, pos, state);
        } else {
            return calculateStraightRoad(world, pos, state, axis);
        }
    }

    private BlockState calculateIntersection(Level world, BlockPos pos, BlockState state) {
        // 1. ISOLATION FILTERS
        if (!isRoad(world, pos.north().east()) && !isRoad(world, pos.north().west()) &&
                !isRoad(world, pos.south().east()) && !isRoad(world, pos.south().west())) {
            return handleSingleIntersection(world, pos, state);
        }

        if (countYNeighbors(world, pos, 1) == 3 && isDoubleHub(world, pos)) {
            return handleDoubleIntersection(world, pos, state);
        }

        // 2. PERIMETER FILTER (Shoulders/Sidewalks)
        int diagNonRoads = countDiagonalNonRoads(world, pos);
        if (diagNonRoads > 0) {
            return resolveShoulderTexture(world, pos, state, diagNonRoads);
        }

        // 3. AGNOSTIC CONNECTION DISCOVERY
        int iN = getConnectedAnchorIndex(world, pos, Direction.NORTH);
        int iS = getConnectedAnchorIndex(world, pos, Direction.SOUTH);
        int iE = getConnectedAnchorIndex(world, pos, Direction.EAST);
        int iW = getConnectedAnchorIndex(world, pos, Direction.WEST);

        int connectionCount = (iN != 0 ? 1 : 0) + (iS != 0 ? 1 : 0) + (iE != 0 ? 1 : 0) + (iW != 0 ? 1 : 0);

        String neighbors = String.format("N:%d S:%d E:%d W:%d (Total:%d)", iN, iS, iE, iW, connectionCount);

        // GATE A: JUNCTION SHIELD
        if (connectionCount > 2) {
            logDebug(pos, neighbors + " -> JUNCTION (LANE)");
            return state.setValue(TEXTURE, RoadTexture.LANE);
        }

        // GATE B: L-TURN PAINT ROOM
        boolean isPerpendicular = (iN != 0 || iS != 0) && (iE != 0 || iW != 0);
        if (connectionCount == 2 && isPerpendicular) {
            if (iN > 0 || iS > 0 || iE > 0 || iW > 0) {
                BlockState result = handleLTurnPaint(world, pos, state, iN, iS, iE, iW);
                logDebug(pos, neighbors + " -> L-TURN PAINT (" + result.getValue(TEXTURE).getSerializedName() + ")");
                return result;
            }
        }

        logDebug(pos, neighbors + " -> FALLBACK (LANE)");
        return state.setValue(TEXTURE, RoadTexture.LANE);
    }

    private BlockState handleLTurnPaint(Level world, BlockPos pos, BlockState state, int iN, int iS, int iE, int iW) {
        int vIdx = (iN != 0) ? iN : iS;
        Direction vDir = (iN != 0) ? Direction.NORTH : Direction.SOUTH;
        int hIdx = (iE != 0) ? iE : iW;
        Direction hDir = (iE != 0) ? Direction.EAST : Direction.WEST;

        int dV = getDistToAnchor(world, pos, vDir);
        int dH = getDistToAnchor(world, pos, hDir);
        int roadWidth = determineIntersectionWidth(world, pos);

        float limit = (roadWidth % 2 != 0) ? (roadWidth / 2.0f + 0.5f) : (roadWidth / 2.0f + 1.0f);
        String orient = resolveOrientation(vDir, hDir);

        // Midpoint Shield
        if (dV > limit || dH > limit) {
            return state.setValue(TEXTURE, RoadTexture.LANE);
        }

        // Corner Logic
        if (vIdx > 0 && hIdx > 0) {
            if (roadWidth % 2 != 0) {
                if (vIdx == 3 && hIdx == 3) {
                    return state.setValue(TEXTURE, RoadTexture.valueOf("ODD_DIVIDER_L_" + orient));
                }
            } else if (vIdx < 3 && hIdx < 3) {
                String pref = (roadWidth == 2) ? "DOUBLE_DIVIDER_L_" : "EVEN_DIVIDER_L_";
                return state.setValue(TEXTURE, RoadTexture.valueOf(pref + resolveQuadrant(orient, vIdx, hIdx) + "_" + orient));
            }
        }

        // Leg Dividers
        if (vIdx > 0) {
            if (vIdx == 3) {
                return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_Y_NORTHSOUTH);
            }
            return state.setValue(TEXTURE, (vIdx == 1) ? RoadTexture.EVEN_DIVIDER_LEFT_Y_NORTHSOUTH : RoadTexture.EVEN_DIVIDER_RIGHT_Y_NORTHSOUTH);
        }

        if (hIdx > 0) {
            if (hIdx == 3) {
                return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_Y_EASTWEST);
            }
            return state.setValue(TEXTURE, (hIdx == 1) ? RoadTexture.EVEN_DIVIDER_LEFT_Y_EASTWEST : RoadTexture.EVEN_DIVIDER_RIGHT_Y_EASTWEST);
        }

        return state.setValue(TEXTURE, RoadTexture.LANE);
    }

    private BlockState calculateStraightRoad(Level world, BlockPos pos, BlockState state, Direction.Axis axis) {
        Direction widthDir = (axis == Direction.Axis.X) ? Direction.SOUTH : Direction.EAST;
        Direction flowDir = (axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;

        int l = 0;
        while (l < 8 && isRoadAxis(world, pos.relative(widthDir, l + 1), axis)) {
            l++;
        }

        int r = 0;
        while (r < 8 && isRoadAxis(world, pos.relative(widthDir.getOpposite(), r + 1), axis)) {
            r++;
        }

        int totalWidth = l + r + 1;
        int laneIndex = l;

        if (!isRoadAxis(world, pos.relative(widthDir), axis) && !isRoadAxis(world, pos.relative(widthDir.getOpposite()), axis)) {
            return handleSingleStraight(world, pos, state, flowDir);
        }

        if (totalWidth == 2) {
            return handleDoubleStraight(world, pos, state, axis, flowDir, laneIndex);
        }

        BlockPos fHub = pos.relative(flowDir);
        BlockPos bHub = pos.relative(flowDir.getOpposite());
        if (isRoadAxis(world, fHub, Direction.Axis.Y) || isRoadAxis(world, bHub, Direction.Axis.Y)) {
            BlockPos hub = isRoadAxis(world, fHub, Direction.Axis.Y) ? fHub : bHub;
            if (countHubConnections(world, hub, totalWidth) >= 3) {
                if (laneIndex == 0) {
                    return state.setValue(TEXTURE, RoadTexture.SHOULDER_CROSSWALK_LEFT);
                }
                if (laneIndex == totalWidth - 1) {
                    return state.setValue(TEXTURE, RoadTexture.SHOULDER_CROSSWALK_RIGHT);
                }
                return state.setValue(TEXTURE, RoadTexture.CROSSWALK);
            }
        }

        if (laneIndex == 0) {
            return state.setValue(TEXTURE, RoadTexture.SHOULDER_LEFT);
        }
        if (laneIndex == totalWidth - 1) {
            return state.setValue(TEXTURE, RoadTexture.SHOULDER_RIGHT);
        }
        if (totalWidth >= 3 && totalWidth % 2 != 0 && laneIndex == totalWidth / 2) {
            return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER);
        }
        if (totalWidth >= 4 && totalWidth % 2 == 0) {
            if (laneIndex == (totalWidth / 2) - 1) {
                return state.setValue(TEXTURE, RoadTexture.EVEN_DIVIDER_LEFT);
            }
            if (laneIndex == totalWidth / 2) {
                return state.setValue(TEXTURE, RoadTexture.EVEN_DIVIDER_RIGHT);
            }
        }

        return state.setValue(TEXTURE, RoadTexture.LANE);
    }

    // --- HUB SUB-HANDLERS ---

    private BlockState handleDoubleIntersection(Level world, BlockPos pos, BlockState state) {
        // 1. AGNOSTIC DISCOVERY (The "Laser Scanner")
        int iN = getConnectedAnchorIndex(world, pos, Direction.NORTH);
        int iS = getConnectedAnchorIndex(world, pos, Direction.SOUTH);
        int iE = getConnectedAnchorIndex(world, pos, Direction.EAST);
        int iW = getConnectedAnchorIndex(world, pos, Direction.WEST);

        // Any non-zero result is a connection
        int connectionCount = (iN != 0 ? 1 : 0) + (iS != 0 ? 1 : 0) + (iE != 0 ? 1 : 0) + (iW != 0 ? 1 : 0);
        boolean isPerp = (iN != 0 || iS != 0) && (iE != 0 || iW != 0);

        // --- DIVIDER CHECK (PRIORITY) ---
        // If it's a 2-way turn, we force it to try and be a Double Divider.
        if (connectionCount == 2 && isPerp) {
            // Resolve active anchors
            int vIdxRaw = (iN != 0) ? iN : iS;
            Direction vDir = (iN != 0) ? Direction.NORTH : Direction.SOUTH;
            int hIdxRaw = (iE != 0) ? iE : iW;
            Direction hDir = (iE != 0) ? Direction.EAST : Direction.WEST;

            // In a 2x2, the scanner might return -1 if it doesn't "know" it's 2-wide yet.
            // We normalize these to 1 or 2 based on distances to the hub's edges.
            int vIdx = resolveMicroLaneIndex(world, pos, vDir);
            int hIdx = resolveMicroLaneIndex(world, pos, hDir);

            if (vIdx > 0 && hIdx > 0) {
                String orient = resolveOrientation(vDir, hDir);
                String quad = resolveQuadrant(orient, vIdx, hIdx);
                return state.setValue(TEXTURE, RoadTexture.valueOf("DOUBLE_DIVIDER_L_" + quad + "_" + orient));
            }
        }

        // --- SHOULDER FALLBACK ---
        // If it wasn't a 2-way turn (or the divider math failed), use standard edges.
        if (connectionCount == 2) {
            return resolveDoubleLTurn(world, pos, state);
        }
        if (connectionCount == 3) {
            return resolveDoubleT(world, pos, state);
        }
        if (connectionCount == 4) {
            return resolveDoubleCross(world, pos, state);
        }

        return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE);
    }

    private BlockState handleSingleIntersection(Level world, BlockPos pos, BlockState state) {
        int c = countSimpleXzConnections(world, pos);
        if (c == 2) {
            return resolveSingleLTurn(world, pos, state);
        }
        if (c == 3) {
            return resolveSingleT(world, pos, state);
        }
        return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE);
    }

    // --- SHAPE RESOLVERS ---

    private BlockState resolveShoulderTexture(Level world, BlockPos pos, BlockState state, int diags) {
        boolean n = !isRoad(world, pos.north());
        boolean s = !isRoad(world, pos.south());
        boolean e = !isRoad(world, pos.east());
        boolean w = !isRoad(world, pos.west());

        if (diags == 2) {
            if (n) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_NORTH);
            if (s) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_SOUTH);
            if (e) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_EAST);
            return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_WEST);
        }

        if (diags == 1) {
            if (!isRoad(world, pos.north().west())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHWEST);
            if (!isRoad(world, pos.north().east())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHEAST);
            if (!isRoad(world, pos.south().west())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHWEST);
            return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHEAST);
        }

        if (!n && !w) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHWEST);
        if (!n && !e) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_NORTHEAST);
        if (!s && !w) return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHWEST);
        return state.setValue(TEXTURE, RoadTexture.SHOULDER_OUTSIDE_CORNER_SOUTHEAST);
    }

    private BlockState resolveDoubleLTurn(Level world, BlockPos pos, BlockState state) {
        int dN = getDistToAnchor(world, pos, Direction.NORTH);
        int dE = getDistToAnchor(world, pos, Direction.EAST);
        int dS = getDistToAnchor(world, pos, Direction.SOUTH);
        int dW = getDistToAnchor(world, pos, Direction.WEST);

        String orient = (dS < 3 && dE < 3) ? "NORTHWEST" : (dS < 3 && dW < 3) ? "NORTHEAST" : (dN < 3 && dW < 3) ? "SOUTHEAST" : "SOUTHWEST";
        int v = (dN == 1 || dS == 2) ? 1 : 2;
        int h = (dE == 1 || dW == 2) ? 1 : 2;

        return state.setValue(TEXTURE, RoadTexture.valueOf("DOUBLE_DIVIDER_L_" + resolveQuadrant(orient, v, h) + "_" + orient));
    }

    private BlockState resolveDoubleT(Level world, BlockPos pos, BlockState state) {
        Direction m = null;
        for (Direction d : Direction.Plane.HORIZONTAL) {
            if (getDistToAnchor(world, pos, d) > 2) {
                m = d;
            }
        }

        if (m == Direction.NORTH) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_NORTH);
        if (m == Direction.SOUTH) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_SOUTH);
        if (m == Direction.EAST) return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_EAST);
        return state.setValue(TEXTURE, RoadTexture.SHOULDER_EDGE_WEST);
    }

    private BlockState resolveDoubleCross(Level world, BlockPos pos, BlockState state) {
        if (!isRoad(world, pos.north().west())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHWEST);
        if (!isRoad(world, pos.north().east())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_NORTHEAST);
        if (!isRoad(world, pos.south().west())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHWEST);
        return state.setValue(TEXTURE, RoadTexture.SHOULDER_CORNER_SOUTHEAST);
    }

    private int resolveMicroLaneIndex(Level world, BlockPos pos, Direction scanDir) {
        Direction widthDir = (scanDir.getAxis() == Direction.Axis.X) ? Direction.NORTH : Direction.WEST;
        
        boolean hasLeft = isRoad(world, pos.relative(widthDir));
        boolean hasRight = isRoad(world, pos.relative(widthDir.getOpposite()));

        if (hasLeft && !hasRight) return 2;
        if (!hasLeft && hasRight) return 1;

        return 0;
    }

    private BlockState resolveSingleLTurn(Level world, BlockPos pos, BlockState state) {
        boolean n = isRoad(world, pos.north());
        boolean s = isRoad(world, pos.south());
        boolean e = isRoad(world, pos.east());
        boolean w = isRoad(world, pos.west());

        if (s && e) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_NORTHWEST);
        if (s && w) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_NORTHEAST);
        if (n && w) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_SOUTHEAST);
        return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_L_SOUTHWEST);
    }

    private BlockState resolveSingleT(Level world, BlockPos pos, BlockState state) {
        if (!isRoad(world, pos.north())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_NORTH);
        if (!isRoad(world, pos.south())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_SOUTH);
        if (!isRoad(world, pos.east())) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_EAST);
        return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_WEST);
    }

    private BlockState handleSingleStraight(Level world, BlockPos pos, BlockState state, Direction fDir) {
        BlockPos f = pos.relative(fDir);
        BlockPos b = pos.relative(fDir.getOpposite());

        if (isRoadAxis(world, f, Direction.Axis.Y) || isRoadAxis(world, b, Direction.Axis.Y)) {
            if (countSimpleHubConnections(world, isRoadAxis(world, f, Direction.Axis.Y) ? f : b) >= 3) {
                return state.setValue(TEXTURE, RoadTexture.CROSSWALK_SINGLE);
            }
        }
        return state.setValue(TEXTURE, RoadTexture.SINGLE);
    }

    private BlockState handleDoubleStraight(Level world, BlockPos pos, BlockState state, Direction.Axis axis, Direction fDir, int lane) {
        BlockPos f = pos.relative(fDir);
        BlockPos b = pos.relative(fDir.getOpposite());

        if (isRoadAxis(world, f, Direction.Axis.Y) || isRoadAxis(world, b, Direction.Axis.Y)) {
            if (countHubConnections(world, isRoadAxis(world, f, Direction.Axis.Y) ? f : b, 2) >= 3) {
                return state.setValue(TEXTURE, lane == 0 ? RoadTexture.SHOULDER_CROSSWALK_LEFT : RoadTexture.SHOULDER_CROSSWALK_RIGHT);
            }
        }
        return state.setValue(TEXTURE, lane == 0 ? RoadTexture.SHOULDER_DIVIDER_LEFT : RoadTexture.SHOULDER_DIVIDER_RIGHT);
    }

    // --- GEOMETRY & SCANNING HELPERS ---

    private int getConnectedAnchorIndex(Level world, BlockPos pos, Direction scanDir) {
        Direction widthDir = (scanDir.getAxis() == Direction.Axis.X) ? Direction.NORTH : Direction.WEST;

        for (int i = 1; i <= 16; i++) {
            BlockPos check = pos.relative(scanDir, i);
            BlockState s = world.getBlockState(check);

            if (!s.is(this)) {
                return 0;
            }
            if (s.getValue(AXIS) == Direction.Axis.Y) {
                continue;
            }

            Direction.Axis roadAxis = s.getValue(AXIS);
            int l = 0;
            while (l < 8 && isRoadAxis(world, check.relative(widthDir, l + 1), roadAxis)) {
                l++;
            }
            int r = 0;
            while (r < 8 && isRoadAxis(world, check.relative(widthDir.getOpposite(), r + 1), roadAxis)) {
                r++;
            }

            int totalW = l + r + 1;

            if (totalW % 2 != 0 && l == r) {
                return 3;
            }
            if (totalW % 2 == 0) {
                if (l == (totalW / 2) - 1) return 1;
                if (l == totalW / 2) return 2;
            }
            return -1;
        }
        return 0;
    }

    private int determineIntersectionWidth(Level world, BlockPos pos) {
        int n = getDistToAnchor(world, pos, Direction.NORTH);
        int s = getDistToAnchor(world, pos, Direction.SOUTH);
        int e = getDistToAnchor(world, pos, Direction.EAST);
        int w = getDistToAnchor(world, pos, Direction.WEST);

        if (n < 20 && s < 20) return n + s - 1;
        if (e < 20 && w < 20) return e + w - 1;

        if (n < 20) return getRoadWidthAtAnchor(world, pos, Direction.NORTH);
        if (s < 20) return getRoadWidthAtAnchor(world, pos, Direction.SOUTH);
        if (e < 20) return getRoadWidthAtAnchor(world, pos, Direction.EAST);
        return getRoadWidthAtAnchor(world, pos, Direction.WEST);
    }

    private int getRoadWidthAtAnchor(Level world, BlockPos pos, Direction dir) {
        for (int i = 1; i <= 16; i++) {
            BlockPos p = pos.relative(dir, i);
            BlockState s = world.getBlockState(p);
            if (!s.is(this)) {
                break;
            }
            if (s.getValue(AXIS) != Direction.Axis.Y) {
                Direction widthDir = (dir.getAxis() == Direction.Axis.X) ? Direction.NORTH : Direction.WEST;
                int l = 0;
                while (l < 8 && isRoadAxis(world, p.relative(widthDir, l + 1), s.getValue(AXIS))) {
                    l++;
                }
                int r = 0;
                while (r < 8 && isRoadAxis(world, p.relative(widthDir.getOpposite(), r + 1), s.getValue(AXIS))) {
                    r++;
                }
                return l + r + 1;
            }
        }
        return 1;
    }

    private int getDistToAnchor(Level world, BlockPos pos, Direction scanDir) {
        for (int i = 1; i <= 16; i++) {
            BlockPos p = pos.relative(scanDir, i);
            BlockState s = world.getBlockState(p);
            if (!s.is(this)) return 99;
            if (s.getValue(AXIS) != Direction.Axis.Y) return i;
        }
        return 99;
    }

    // --- BASE UTILITIES ---

    private boolean isRoadAxis(Level level, BlockPos pos, Direction.Axis axis) {
        BlockState s = level.getBlockState(pos);
        return s.is(this) && s.getValue(AXIS) == axis;
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

    private String resolveQuadrant(String orient, int v, int h) {
        if (orient.equals("NORTHWEST")) return (v == 1) ? (h == 1 ? "TOP_LEFT" : "BOTTOM_LEFT") : (h == 1 ? "TOP_RIGHT" : "BOTTOM_RIGHT");
        if (orient.equals("NORTHEAST")) return (v == 1) ? (h == 1 ? "BOTTOM_LEFT" : "BOTTOM_RIGHT") : (h == 1 ? "TOP_LEFT" : "TOP_RIGHT");
        if (orient.equals("SOUTHEAST")) return (v == 1) ? (h == 1 ? "BOTTOM_RIGHT" : "TOP_RIGHT") : (h == 1 ? "BOTTOM_LEFT" : "TOP_LEFT");
        return (v == 1) ? (h == 1 ? "TOP_RIGHT" : "TOP_LEFT") : (h == 1 ? "BOTTOM_RIGHT" : "BOTTOM_LEFT");
    }

    private String resolveOrientation(Direction v, Direction h) {
        if (v == Direction.SOUTH && h == Direction.EAST) return "NORTHWEST";
        if (v == Direction.SOUTH && h == Direction.WEST) return "NORTHEAST";
        if (v == Direction.NORTH && h == Direction.WEST) return "SOUTHEAST";
        return "SOUTHWEST";
    }

    private int countYNeighbors(Level world, BlockPos pos, int r) {
        int c = 0;
        for (Direction d : Direction.Plane.HORIZONTAL) {
            if (isRoadAxis(world, pos.relative(d), Direction.Axis.Y)) {
                c++;
            }
        }
        return c;
    }

    private boolean isDoubleHub(Level world, BlockPos pos) {
        Direction missing = null;
        for (Direction d : Direction.Plane.HORIZONTAL) {
            if (!isRoadAxis(world, pos.relative(d), Direction.Axis.Y)) {
                missing = d;
            }
        }
        return missing != null && !isRoad(world, pos.relative(missing.getOpposite()).relative(missing.getClockWise()));
    }

    private int countSimpleXzConnections(Level world, BlockPos pos) {
        int c = 0;
        for (Direction d : Direction.Plane.HORIZONTAL) {
            if (isRoad(world, pos.relative(d)) && world.getBlockState(pos.relative(d)).getValue(AXIS) != Direction.Axis.Y) {
                c++;
            }
        }
        return c;
    }

    private int countScanConnections(Level world, BlockPos pos, int r) {
        int c = 0;
        for (Direction d : Direction.Plane.HORIZONTAL) {
            for (int i = 1; i <= r; i++) {
                BlockState s = world.getBlockState(pos.relative(d, i));
                if (s.is(this) && s.getValue(AXIS) != Direction.Axis.Y) {
                    c++;
                    break;
                }
                if (!s.is(this)) break;
            }
        }
        return c;
    }

    private int countHubConnections(Level world, BlockPos hub, int width) {
        int c = 0;
        for (Direction d : Direction.Plane.HORIZONTAL) {
            for (int i = 1; i <= width + 1; i++) {
                BlockState s = world.getBlockState(hub.relative(d, i));
                if (s.is(this) && s.getValue(AXIS) != Direction.Axis.Y) {
                    c++;
                    break;
                }
                if (!s.is(this)) break;
            }
        }
        return c;
    }

    private int countSimpleHubConnections(Level world, BlockPos hub) {
        int c = 0;
        for (Direction d : Direction.Plane.HORIZONTAL) {
            if (isRoad(world, hub.relative(d)) && world.getBlockState(hub.relative(d)).getValue(AXIS) != Direction.Axis.Y) {
                c++;
            }
        }
        return c;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, TEXTURE);
    }
}