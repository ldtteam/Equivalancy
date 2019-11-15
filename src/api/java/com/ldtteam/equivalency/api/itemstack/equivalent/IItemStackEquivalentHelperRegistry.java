package com.ldtteam.equivalency.api.itemstack.equivalent;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiFunction;

public interface IItemStackEquivalentHelperRegistry
{

    boolean areItemStacksEquivalentExceptForStack(@NotNull final ItemStack stackOne, @NotNull final ItemStack stackTwo);

    IItemStackEquivalentHelperRegistry registerNewHandler(@NotNull final BiFunction<ItemStack, ItemStack, Optional<Boolean>> handler);

    IItemStackEquivalentHelperRegistry registerTagNameToInclude(@NotNull final ResourceLocation location);

    Iterable<ResourceLocation> getTagNamesToInclude();
}
