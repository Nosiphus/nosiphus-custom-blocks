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

        System.out.println("[ROAD DEBUG] Pos: " + pos.toShortString() + " | Axis: " + currentAxis + " | Final Texture: " + finalState.getValue(TEXTURE));
        return finalState;
    }

    private BlockState calculateIntersection(Level world, BlockPos pos, BlockState state) {
        boolean north = isRoad(world, pos.north());
        boolean south = isRoad(world, pos.south());
        boolean east = isRoad(world, pos.east());
        boolean west = isRoad(world, pos.west());
        boolean roadNW = isRoad(world, pos.north().west());
        boolean roadNE = isRoad(world, pos.north().east());
        boolean roadSW = isRoad(world, pos.south().west());
        boolean roadSE = isRoad(world, pos.south().east());

        int connectedRoads = (north ? 1 : 0) + (south ? 1 : 0) + (east ? 1 : 0) + (west ? 1 : 0);

        // STEP 1: 1x1 Determination (No diagonals)
        if (!roadNW && !roadNE && !roadSW && !roadSE) {
            return handleSingleRoad(state, world, pos, connectedRoads);
        }

        // STEP 2: Large Intersection Logic (Odd-Width)
        boolean anchorN = checkAnchorStrict(world, pos, Direction.NORTH, Direction.Axis.Z);
        boolean anchorS = checkAnchorStrict(world, pos, Direction.SOUTH, Direction.Axis.Z);
        boolean anchorE = checkAnchorStrict(world, pos, Direction.EAST, Direction.Axis.X);
        boolean anchorW = checkAnchorStrict(world, pos, Direction.WEST, Direction.Axis.X);
        int anchorCount = (anchorN ? 1 : 0) + (anchorS ? 1 : 0) + (anchorE ? 1 : 0) + (anchorW ? 1 : 0);

        if (anchorCount == 2 && ((anchorN || anchorS) && (anchorE || anchorW))) {
            if (anchorN && anchorW) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_L_SOUTHEAST);
            if (anchorN && anchorE) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_L_SOUTHWEST);
            if (anchorS && anchorE) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_L_NORTHWEST);
            return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_L_NORTHEAST);
        }

        if (anchorCount == 1) {
            if (anchorN && isVertexNearby(world, pos, Direction.SOUTH)) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_Y_NORTHSOUTH);
            if (anchorS && isVertexNearby(world, pos, Direction.NORTH)) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_Y_NORTHSOUTH);
            if (anchorE && isVertexNearby(world, pos, Direction.WEST)) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_Y_EASTWEST);
            if (anchorW && isVertexNearby(world, pos, Direction.EAST)) return state.setValue(TEXTURE, RoadTexture.ODD_DIVIDER_Y_EASTWEST);
        }

        // STEP 3: Fallback (Junction Interiors and Shoulders)
        boolean allEight = north && south && east && west && roadNW && roadNE && roadSW && roadSE;
        if (allEight) return state.setValue(TEXTURE, RoadTexture.LANE);

        return handleShoulderChain(world, pos, state, connectedRoads);
    }

    private BlockState calculateStraightRoad(Level world, BlockPos pos, BlockState state, Direction.Axis axis) {
        Direction widthDir = (axis == Direction.Axis.X) ? Direction.SOUTH : Direction.EAST;
        Direction flowDir = (axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;

        int lanesToLeft = 0, lanesToRight = 0;
        while (lanesToLeft < 6 && isRoadAxis(world, pos.relative(widthDir, lanesToLeft + 1), axis)) lanesToLeft++;
        while (lanesToRight < 6 && isRoadAxis(world, pos.relative(widthDir.getOpposite(), lanesToRight + 1), axis)) lanesToRight++;

        int roadWidth = lanesToLeft + lanesToRight + 1;
        int currentLaneIndex = lanesToLeft;

        // Crosswalk Discovery
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

        // Divider and Shoulder Selection
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
        boolean roadN = isRoad(world, pos.north()), roadS = isRoad(world, pos.south());
        boolean roadE = isRoad(world, pos.east()), roadW = isRoad(world, pos.west());

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
        for (int i = 1; i <= 7; i++) {
            BlockPos check = pos.relative(scanDir, i);
            if (!isRoadAxis(world, check, Direction.Axis.Y)) break;
            boolean mN = checkAnchorStrict(world, check, Direction.NORTH, Direction.Axis.Z);
            boolean mS = checkAnchorStrict(world, check, Direction.SOUTH, Direction.Axis.Z);
            boolean mE = checkAnchorStrict(world, check, Direction.EAST, Direction.Axis.X);
            boolean mW = checkAnchorStrict(world, check, Direction.WEST, Direction.Axis.X);
            if (((mN ? 1 : 0) + (mS ? 1 : 0) + (mE ? 1 : 0) + (mW ? 1 : 0)) == 2 && ((mN || mS) && (mE || mW))) return true;
        }
        return false;
    }

    private boolean checkAnchorStrict(Level world, BlockPos pos, Direction scanDir, Direction.Axis anchorAxis) {
        Direction widthDir = (anchorAxis == Direction.Axis.X) ? Direction.NORTH : Direction.WEST;
        for (int i = 1; i <= 16; i++) {
            BlockPos anchorPos = pos.relative(scanDir, i);
            BlockState state = world.getBlockState(anchorPos);
            if (!state.is(this)) break;
            if (state.getValue(AXIS) == anchorAxis) {
                int l = 0, r = 0;
                while (l < 6 && isRoadAxis(world, anchorPos.relative(widthDir, l + 1), anchorAxis)) l++;
                while (r < 6 && isRoadAxis(world, anchorPos.relative(widthDir.getOpposite(), r + 1), anchorAxis)) r++;
                int w = l + r + 1;
                return i <= (w + 1) / 2 && w % 2 != 0 && l == r;
            }
            if (state.getValue(AXIS) != Direction.Axis.Y) break;
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