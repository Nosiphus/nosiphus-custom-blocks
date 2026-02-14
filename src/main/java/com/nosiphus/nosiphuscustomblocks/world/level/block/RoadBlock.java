package com.nosiphus.nosiphuscustomblocks.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final IntegerProperty WIDTH = IntegerProperty.create("width", 1, 7);
    public static final IntegerProperty POSITION = IntegerProperty.create("position", 0, 6);

    public RoadBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AXIS, Direction.Axis.X)
                .setValue(WIDTH, 1)
                .setValue(POSITION, 0));
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
        // Determine the "Scanning" direction perpendicular to the road's flow
        Direction checkDir = (axis == Direction.Axis.X) ? Direction.SOUTH : Direction.EAST;
        Direction oppDir = checkDir.getOpposite();

        int left = 0;
        int right = 0;

        // Scan "Left"
        while (left < 6 && world.getBlockState(pos.relative(checkDir, left + 1)).getBlock() instanceof RoadBlock) {
            left++;
        }
        // Scan "Right"
        while (right < 6 && world.getBlockState(pos.relative(oppDir, right + 1)).getBlock() instanceof RoadBlock) {
            right++;
        }

        int totalWidth = Math.min(7, left + right + 1);
        int myPos = Math.min(6, left);

        return state.setValue(WIDTH, totalWidth).setValue(POSITION, myPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, WIDTH, POSITION);
    }
}
