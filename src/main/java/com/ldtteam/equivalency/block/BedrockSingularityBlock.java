package com.ldtteam.equivalency.block;

import com.ldtteam.equivalency.api.tileentity.AbstractBedrockSingularityTileEntity;
import com.ldtteam.equivalency.tileentity.BedrockSingularityTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BedrockSingularityBlock extends ContainerBlock
{
    protected static final VoxelShape COLLISION_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);
    protected static final VoxelShape OUTLINE_SHAPE   = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public BedrockSingularityBlock(final Properties properties)
    {
        super(properties);
    }

    @SuppressWarnings("deprecation") //Can be override, should just not be called.
    @Override
    public void onEntityCollision(final BlockState state, final World worldIn, final BlockPos pos, final Entity entityIn)
    {
        if (!(worldIn instanceof ServerWorld))
            return;

        if (!(entityIn instanceof ItemEntity) && !(entityIn instanceof PlayerEntity))
        {
            entityIn.onKillCommand();
            return;
        }

        if (entityIn instanceof PlayerEntity)
        {
            if (!((PlayerEntity) entityIn).isCreative())
            {
                entityIn.onKillCommand();
            }

            return;
        }

        final ItemEntity itemEntity = (ItemEntity) entityIn;
        final ItemStack stack = itemEntity.getItem();

        final ServerWorld serverWorld = (ServerWorld) worldIn;
        final TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (!(tileEntity instanceof AbstractBedrockSingularityTileEntity))
            return;

        final AbstractBedrockSingularityTileEntity singularityTileEntity = (AbstractBedrockSingularityTileEntity) tileEntity;
        final UUID throwerId = itemEntity.getThrowerId();
        if (throwerId.equals(singularityTileEntity.getThrowerId()))
            return;

        itemEntity.onKillCommand();
        final Entity throwingEntity = serverWorld.getEntityByUuid(throwerId);
        final PlayerEntity throwingPlayer = throwingEntity instanceof PlayerEntity ? (PlayerEntity) throwingEntity : null;

        singularityTileEntity.onItemStackDroppedIn(stack, throwingPlayer);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(final IBlockReader worldIn)
    {
        return new BedrockSingularityTileEntity();
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return COLLISION_SHAPE;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return OUTLINE_SHAPE;
    }
}
