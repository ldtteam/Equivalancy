package com.ldtteam.equivalency.api.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.NonNullList;

import java.util.function.Consumer;

import static net.minecraftforge.common.ForgeHooks.setCraftingPlayer;

public final class RecipeUtil
{

    private RecipeUtil()
    {
        throw new IllegalStateException("Tried to initialize: RecipeUtil but this is a Utility class.");
    }

    public static <R extends IRecipe<I>, I extends IInventory> ItemStack handleCrafting(
      final IRecipeType<R> recipeType,
      final I recipeWrapper,
      final IRecipeHolder holder,
      final ItemStack resultStack,
      final PlayerEntity player,
      final Consumer<ItemStack> dropHandler)
    {
        if (resultStack.isEmpty())
            return ItemStack.EMPTY;

        onCrafting(recipeWrapper, holder, resultStack, player);

        setCraftingPlayer(player);
        NonNullList<ItemStack> remainingInventoryStacks = player == null ?
                                                            copyInventory(recipeWrapper) :
                                                                                           player.world.getRecipeManager()
                                                                                             .getRecipeNonNull(recipeType,
                                                                                               recipeWrapper,
                                                                                               player.world);
        setCraftingPlayer(null);

        for (int i = 0; i < remainingInventoryStacks.size(); ++i)
        {
            ItemStack currentStack = recipeWrapper.getStackInSlot(i);
            ItemStack remainingInventoryStack = remainingInventoryStacks.get(i);
            if (!currentStack.isEmpty())
            {
                recipeWrapper.decrStackSize(i, 1);
                currentStack = recipeWrapper.getStackInSlot(i);
            }

            if (!remainingInventoryStack.isEmpty())
            {
                if (currentStack.isEmpty())
                {
                    recipeWrapper.setInventorySlotContents(i, remainingInventoryStack);
                }
                else if (ItemStack.areItemsEqual(currentStack, remainingInventoryStack) && ItemStack.areItemStackTagsEqual(currentStack, remainingInventoryStack))
                {
                    remainingInventoryStack.grow(currentStack.getCount());
                    recipeWrapper.setInventorySlotContents(i, remainingInventoryStack);
                }
                else if (player != null && !player.inventory.addItemStackToInventory(remainingInventoryStack))
                {
                    player.dropItem(remainingInventoryStack, false);
                }
                else if (player == null)
                {
                    dropHandler.accept(remainingInventoryStack);
                }
            }
        }

        return resultStack;
    }

    private static <I extends IInventory> void onCrafting(final I recipeWrapper, final IRecipeHolder holder, final ItemStack resultStack, final PlayerEntity player)
    {
        if (resultStack.getCount() > 0 && player != null)
        {
            resultStack.onCrafting(player.world, player, resultStack.getCount());
            net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerCraftingEvent(player, resultStack, recipeWrapper);
        }

        if (holder != null && player != null)
        {
            holder.onCrafting(player);
        }
    }

    private static NonNullList<ItemStack> copyInventory(IInventory inventory)
    {
        final NonNullList<ItemStack> copy = NonNullList.withSize(inventory.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < inventory.getSizeInventory(); i++)
        {
            final ItemStack stack = inventory.getStackInSlot(i);
            copy.set(i, stack);
        }

        return copy;
    }
}
