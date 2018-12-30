package com.ldtteam.equivalency.itemstack.equivalent;

import com.google.common.collect.Sets;
import com.ldtteam.equivalency.api.itemstack.equivalent.IItemStackEquivalentHelperRegistry;
import com.ldtteam.equivalency.api.util.ItemStackUtils;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

public class ItemStackEquivalentHelperRegistry implements IItemStackEquivalentHelperRegistry
{
    private final Set<BiFunction<ItemStack, ItemStack, Optional<Boolean>>> handlers = Sets.newHashSet();

    @Override
    public boolean areItemStacksEquivalentExceptForStack(
      @NotNull final ItemStack stackOne, @NotNull final ItemStack stackTwo)
    {
        return handlers
          .stream()
          .map(handler -> handler.apply(stackOne, stackTwo))
          .filter(Optional::isPresent)
          .map(Optional::get)
          .findFirst()
          .orElse(ItemStackUtils.compareItemStacksIgnoreStackSize(stackOne, stackTwo));
    }

    @Override
    public IItemStackEquivalentHelperRegistry registerNewHandler(@NotNull final BiFunction<ItemStack, ItemStack, Optional<Boolean>> handler)
    {
        handlers.add(handler);
        return this;
    }
}