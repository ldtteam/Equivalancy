package com.ldtteam.equivalency.api.compound;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface ILockedCompoundWrapperToTypeRegistry
{

    ILockedCompoundWrapperToTypeRegistry registerLocking(@NotNull final ICompoundContainerWrapper<?> wrapper, @NotNull final Set<ICompoundInstance> instances);

    <T> ILockedCompoundWrapperToTypeRegistry registerLocking(@NotNull final T tInstance, @NotNull final Set<ICompoundInstance> instances);

    ImmutableMap<ICompoundContainerWrapper<?>, ImmutableSet<ICompoundInstance>> getLockedInformation();
}
