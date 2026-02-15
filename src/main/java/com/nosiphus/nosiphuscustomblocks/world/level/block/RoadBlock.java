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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
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
        CROSSWALK_SHOULDER_LEFT("crosswalk_shoulder_left"),
        CROSSWALK_SHOULDER_RIGHT("crosswalk_shoulder_right"),
        DIVIDER("divider"),
        DIVIDER_LEFT("divider_left"),
        DIVIDER_RIGHT("divider_right"),
        LANE("lane"),
        SINGLE("single"),
        SHOULDER_LEFT("shoulder_left"),
        SHOULDER_LEFT_DIVIDER("shoulder_left_divider"),
        SHOULDER_RIGHT("shoulder_right"),
        SHOULDER_RIGHT_DIVIDER("shoulder_right_divider");

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
            return state.setValue(TEXTURE, RoadTexture.LANE);
        }

        Direction flow = (axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;
        boolean nearIntersection = isY(world, pos.relative(flow)) || isY(world, pos.relative(flow.getOpposite()));

        Direction checkDir = (axis == Direction.Axis.X) ? Direction.SOUTH : Direction.EAST;
        int left = 0;
        int right = 0;
        while (left < 6 && isSameAxis(world, pos.relative(checkDir, left + 1), axis)) left++;
        while (right < 6 && isSameAxis(world, pos.relative(checkDir.getOpposite(), right + 1), axis)) right++;

        int totalWidth = left + right + 1;
        int myPos = left;

        if (nearIntersection) {
            if (myPos == 0) return state.setValue(TEXTURE, RoadTexture.CROSSWALK_SHOULDER_LEFT);
            if (myPos == totalWidth - 1) return state.setValue(TEXTURE, RoadTexture.CROSSWALK_SHOULDER_RIGHT);
            return state.setValue(TEXTURE, RoadTexture.CROSSWALK);
        }

        return state.setValue(TEXTURE, getRoadTexture(totalWidth, myPos));
    }

    private boolean isY(Level world, BlockPos pos) {
        BlockState s = world.getBlockState(pos);
        return s.getBlock() instanceof RoadBlock && s.getValue(AXIS) == Direction.Axis.Y;
    }

    private boolean isSameAxis(Level world, BlockPos pos, Direction.Axis axis) {
        BlockState state = world.getBlockState(pos);
        return state.getBlock() instanceof RoadBlock && state.getValue(AXIS) == axis;
    }

    private RoadTexture getRoadTexture(int width, int pos) {
        if (width == 1) return RoadTexture.SINGLE;
        if (width == 2) return (pos == 0) ? RoadTexture.SHOULDER_LEFT_DIVIDER : RoadTexture.SHOULDER_RIGHT_DIVIDER;
        if (pos == 0) return RoadTexture.SHOULDER_LEFT;
        if (pos == width - 1) return RoadTexture.SHOULDER_RIGHT;
        if (width % 2 != 0 && pos == width / 2) return RoadTexture.DIVIDER;
        if (width % 2 == 0 && pos == (width / 2) - 1) return RoadTexture.DIVIDER_LEFT;
        if (width % 2 == 0 && pos == width / 2) return RoadTexture.DIVIDER_RIGHT;
        return RoadTexture.LANE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, TEXTURE);
    }
}
