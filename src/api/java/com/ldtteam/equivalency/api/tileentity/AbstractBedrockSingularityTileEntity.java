package com.ldtteam.equivalency.api.tileentity;

import com.ldtteam.equivalency.api.util.ModBlocks;
import com.ldtteam.equivalency.api.util.ModRecipeTypes;
import com.ldtteam.equivalency.api.recipe.singularity.transmutation.ISingularityTransmutationRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class AbstractBedrockSingularityTileEntity extends TileEntity implements IRecipeHolder, ITickableTileEntity
{

    private static final int DEFAULT_MASS = 40;
    private static final int  MINIMAL_SPLIT_MASS = 75;
    protected final      UUID throwerId;

    private float currentMass = DEFAULT_MASS;
    private ItemStackHandler itemHandler = createHandler();
    private RecipeWrapper    iInventory  = new RecipeWrapper(itemHandler);

    public AbstractBedrockSingularityTileEntity(final TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
        throwerId = UUID.randomUUID();
    }

    public abstract void onItemStackDroppedIn(final ItemStack stack, @Nullable final PlayerEntity thrower);

    @Nullable
    protected ISingularityTransmutationRecipe findRecipe() {
        return this.world.getRecipeManager().getRecipe(ModRecipeTypes.SINGULARITY_TRANSMUTATION, getIInventory(), this.world).orElse(null);
    }

    protected ItemStackHandler getItemHandler()
    {
        return itemHandler;
    }

    protected RecipeWrapper getIInventory()
    {
        return iInventory;
    }

    public void changeMass(final float change) {
        this.currentMass += change;
        if (currentMass > MINIMAL_SPLIT_MASS) {
            final float massDelta = currentMass - MINIMAL_SPLIT_MASS;
            final int splitChanceMax = (int) (100 - massDelta);

            boolean shouldSplit = splitChanceMax <= 0;
            final float subtractedMass = massDelta + getWorld().getRandom().nextInt(MINIMAL_SPLIT_MASS / 2);
            if (splitChanceMax > 1) {
                final int splitRandom = getWorld().getRandom().nextInt(splitChanceMax);
                shouldSplit = splitRandom == 0;
            }

            if (shouldSplit) {
                this.currentMass -= subtractedMass;
                final Direction splitDirection = Direction.byIndex(world.getRandom().nextInt(Direction.values().length));
                final BlockPos splitPos = getPos().offset(splitDirection);

                final BlockState splitState = getWorld().getBlockState(splitPos);
                if (splitState.getBlock() == ModBlocks.BEDROCK_SINGULARITY) {
                    final TileEntity splitTargetTileEntity = getWorld().getTileEntity(splitPos);
                    if (splitTargetTileEntity instanceof AbstractBedrockSingularityTileEntity) {
                        final AbstractBedrockSingularityTileEntity singularityTileEntity = (AbstractBedrockSingularityTileEntity) splitTargetTileEntity;
                        singularityTileEntity.changeMass(subtractedMass);
                    }
                }
                else if (splitState.getBlock() == Blocks.BEDROCK)
                {
                    getWorld().setBlockState(splitPos, ModBlocks.BEDROCK_SINGULARITY.getDefaultState());
                    final TileEntity newTileEntity = getWorld().getTileEntity(splitPos);
                    if (newTileEntity instanceof AbstractBedrockSingularityTileEntity) {
                        final AbstractBedrockSingularityTileEntity newSingularityTileEntity = (AbstractBedrockSingularityTileEntity) newTileEntity;
                        newSingularityTileEntity.changeMass(subtractedMass - DEFAULT_MASS);
                    }
                }
            }
        }
    }

    public UUID getThrowerId()
    {
        return throwerId;
    }

    @Override
    public void read(final BlockState state, final CompoundNBT nbt)
    {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        currentMass = nbt.getFloat("mass");
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(final CompoundNBT compound)
    {
        compound.put("inv", itemHandler.serializeNBT());
        compound.putFloat("mass", currentMass);
        return super.write(compound);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(64) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                markDirty();
            }
        };
    }
}
