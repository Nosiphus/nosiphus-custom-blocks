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
        SHOULDER_CROSSWALK_LEFT("shoulder_crosswalk_left"),
        SHOULDER_CROSSWALK_RIGHT("shoulder_crosswalk_right"),
        SHOULDER_DIVIDER_LEFT("shoulder_divider_left"),
        SHOULDER_DIVIDER_RIGHT("shoulder_divider_right"),
        SHOULDER_LEFT("shoulder_left"),
        SHOULDER_RIGHT("shoulder_right"),
        SHOULDER_SINGLE("shoulder_single"),
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

        if (axis == Direction.Axis.Y) {
            boolean n = world.getBlockState(pos.north()).getBlock() instanceof RoadBlock;
            boolean s = world.getBlockState(pos.south()).getBlock() instanceof RoadBlock;
            boolean e = world.getBlockState(pos.east()).getBlock() instanceof RoadBlock;
            boolean w = world.getBlockState(pos.west()).getBlock() instanceof RoadBlock;
            int neighbors = (n?1:0) + (s?1:0) + (e?1:0) + (w?1:0);

            if (neighbors == 3) {
                if (!n) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_NORTH);
                if (!s) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_SOUTH);
                if (!e) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_EAST);
                if (!w) return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE_T_WEST);
            }
            return state.setValue(TEXTURE, RoadTexture.SHOULDER_SINGLE);
        }

        Direction widthDir = (axis == Direction.Axis.X) ? Direction.SOUTH : Direction.EAST;
        Direction flowDir = (axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;

        int left = 0, right = 0;
        while (left < 6 && isRoadAxis(world, pos.relative(widthDir, left + 1), axis)) left++;
        while (right < 6 && isRoadAxis(world, pos.relative(widthDir.getOpposite(), right + 1), axis)) right++;
        int width = left + right + 1, myPos = left;

        BlockState f = world.getBlockState(pos.relative(flowDir)), b = world.getBlockState(pos.relative(flowDir.getOpposite()));
        if ((f.is(this) && f.getValue(AXIS) == Direction.Axis.Y) || (b.is(this) && b.getValue(AXIS) == Direction.Axis.Y)) {
            if (width == 1) return state.setValue(TEXTURE, RoadTexture.CROSSWALK_SINGLE);
            if (myPos == 0) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CROSSWALK_LEFT);
            if (myPos == width - 1) return state.setValue(TEXTURE, RoadTexture.SHOULDER_CROSSWALK_RIGHT);
            return state.setValue(TEXTURE, RoadTexture.CROSSWALK);
        }

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

    private boolean isRoadAxis(Level world, BlockPos pos, Direction.Axis axis) {
        BlockState state = world.getBlockState(pos);
        return state.is(this) && state.getValue(AXIS) == axis;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, TEXTURE);
    }
}
