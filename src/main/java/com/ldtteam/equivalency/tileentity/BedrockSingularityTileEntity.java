package com.ldtteam.equivalency.tileentity;

import com.ldtteam.equivalency.api.tileentity.AbstractBedrockSingularityTileEntity;
import com.ldtteam.equivalency.api.util.*;
import com.ldtteam.equivalency.api.recipe.singularity.transmutation.ISingularityTransmutationRecipe;
import net.minecraft.block.Block;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class BedrockSingularityTileEntity extends AbstractBedrockSingularityTileEntity
{

    private static final int DEFAULT_CLEARING_DELAY = 40;

    private int clearingDelay = DEFAULT_CLEARING_DELAY;
    private ISingularityTransmutationRecipe currentRecipe;

    public BedrockSingularityTileEntity()
    {
        super(ModTileEntityTypes.SINGULARITY_TRANSLATION);
    }

    @Override
    public void onItemStackDroppedIn(final ItemStack stack, @Nullable final PlayerEntity thrower)
    {
        clearingDelay = DEFAULT_CLEARING_DELAY;
        InventoryUtil.insertIntoNextFreeOrDiscard(stack, getItemHandler());
        ISingularityTransmutationRecipe recipe = findRecipe();

        while(recipe != null) {
            setRecipeUsed(recipe);

            final ItemStack resultStack = RecipeUtil.handleCrafting(
              ModRecipeTypes.SINGULARITY_TRANSMUTATION,
              getIInventory(),
              this,
              recipe.getCraftingResult(getIInventory()),
              thrower,
              (stackToDrop) -> Block.spawnAsEntity(Objects.requireNonNull(getWorld()), pos, stackToDrop)
            );

            if (!resultStack.isEmpty())
            {
                final ItemEntity spawnedEntity = WorldUtils.spawnItemEntity(Objects.requireNonNull(getWorld()), pos, resultStack);

                spawnedEntity.setThrowerId(throwerId);

                this.changeMass(recipe.getMassIncrease());
                recipe = findRecipe();
            }
            else
            {
                recipe = null;
            }
        }
    }

    @Override
    public void setRecipeUsed(@Nullable final IRecipe<?> recipe)
    {
        if (recipe instanceof ISingularityTransmutationRecipe)
        {
            this.currentRecipe = (ISingularityTransmutationRecipe) recipe;
            return;
        }

        if (recipe == null)
        {
            this.currentRecipe = null;
            return;
        }

        throw new IllegalArgumentException("Recipe is not a singularity transmutation");
    }

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed()
    {
        return currentRecipe;
    }

    @Override
    public void tick()
    {
        clearingDelay--;
        if (clearingDelay == 0)
        {
            clearingDelay = DEFAULT_CLEARING_DELAY;
            getIInventory().clear();
        }
    }
}
