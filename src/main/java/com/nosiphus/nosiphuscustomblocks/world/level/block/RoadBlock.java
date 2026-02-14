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

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final EnumProperty<RoadTexture> TEXTURE = EnumProperty.create("texture", RoadTexture.class);

    public RoadBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AXIS, Direction.Axis.X)
                .setValue(TEXTURE, RoadTexture.LANE));
    }

    public enum RoadTexture implements StringRepresentable {
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
        return this.calculateState(context.getLevel(), context.getClickedPos(), this.defaultBlockState().setValue(AXIS, context.getHorizontalDirection().getAxis()));
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
        Direction checkDir = (axis == Direction.Axis.X) ? Direction.SOUTH : Direction.EAST;
        Direction oppDir = checkDir.getOpposite();

        int left = 0;
        int right = 0;

        while (left < 6 && world.getBlockState(pos.relative(checkDir, left + 1)).getBlock() instanceof RoadBlock) {
            left++;
        }
        while (right < 6 && world.getBlockState(pos.relative(oppDir, right + 1)).getBlock() instanceof RoadBlock) {
            right++;
        }

        int totalWidth = left + right + 1;
        int myPos = left;
        RoadTexture tex = RoadTexture.LANE;

        if (totalWidth == 1) {
            tex = RoadTexture.SINGLE;
        } else if (totalWidth == 2) {
            tex = (myPos == 0) ? RoadTexture.SHOULDER_LEFT_DIVIDER : RoadTexture.SHOULDER_RIGHT_DIVIDER;
        } else {
            if (myPos == 0) {
                tex = RoadTexture.SHOULDER_LEFT;
            } else if (myPos == totalWidth - 1) {
                tex = RoadTexture.SHOULDER_RIGHT;
            } else if (totalWidth % 2 != 0 && myPos == totalWidth / 2) {
                tex = RoadTexture.DIVIDER;
            } else if (totalWidth % 2 == 0 && myPos == (totalWidth / 2) - 1) {
                tex = RoadTexture.DIVIDER_LEFT;
            } else if (totalWidth % 2 == 0 && myPos == totalWidth / 2) {
                tex = RoadTexture.DIVIDER_RIGHT;
            }
        }

        return state.setValue(TEXTURE, tex);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, TEXTURE);
    }
}
