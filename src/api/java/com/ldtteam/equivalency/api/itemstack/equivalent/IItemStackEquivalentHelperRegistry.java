package com.ldtteam.equivalency.api.itemstack.equivalent;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.BiFunction;

public interface IItemStackEquivalentHelperRegistry
{

    boolean areItemStacksEquivalentExceptForStack(@NotNull final ItemStack stackOne, @NotNull final ItemStack stackTwo);

    IItemStackEquivalentHelperRegistry registerNewHandler(@NotNull final BiFunction<ItemStack, ItemStack, Optional<Boolean>> handler);
}
