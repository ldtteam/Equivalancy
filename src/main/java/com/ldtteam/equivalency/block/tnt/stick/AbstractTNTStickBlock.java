package com.ldtteam.equivalency.block.tnt.stick;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import static net.minecraft.state.properties.BlockStateProperties.FACING;

public abstract class AbstractTNTStickBlock extends TNTBlock
{
    public AbstractTNTStickBlock() {
        super(
          Block.Properties
            .create(Material.TNT)
            .zeroHardnessAndResistance()
            .sound(SoundType.PLANT)
        );
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(UNSTABLE, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState()
                 .with(FACING, context.getFace().getOpposite());
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public BlockRenderType getRenderType(final BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.with(FACING, rotation.rotate(blockState.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.toRotation(blockState.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(UNSTABLE);
    }
}
