package com.ldtteam.equivalency.api.compound;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface ILockedCompoundWrapperToTypeRegistry
{
    void resetForWorld(@NotNull final World world);

    ILockedCompoundWrapperToTypeRegistry registerLocking(@NotNull final World world, @NotNull final ICompoundContainerWrapper<?> wrapper, @NotNull final Set<ICompoundInstance> instances);

    <T> ILockedCompoundWrapperToTypeRegistry registerLocking(@NotNull final World world, @NotNull final T tInstance, @NotNull final Set<ICompoundInstance> instances);

    ImmutableMap<ICompoundContainerWrapper<?>, ImmutableSet<ICompoundInstance>> getLockedInformation(@NotNull final World world);
}
