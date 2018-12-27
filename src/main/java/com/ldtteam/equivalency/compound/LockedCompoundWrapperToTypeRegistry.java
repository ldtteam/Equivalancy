package com.ldtteam.equivalency.compound;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.api.compound.ICompoundInstance;
import com.ldtteam.equivalency.api.compound.ILockedCompoundWrapperToTypeRegistry;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LockedCompoundWrapperToTypeRegistry implements ILockedCompoundWrapperToTypeRegistry
{
    private final Map<ICompoundContainerWrapper<?>, ImmutableSet<ICompoundInstance>> lockedInformation = new HashMap<>();

    @Override
    public ILockedCompoundWrapperToTypeRegistry registerLocking(
      @NotNull final ICompoundContainerWrapper<?> wrapper, @NotNull final Set<ICompoundInstance> instances)
    {
        if (wrapper.getContentsCount() != 1)
            throw new IllegalArgumentException("Can not set locked information with none unit stack.");

        lockedInformation.put(wrapper, ImmutableSet.copyOf(instances));

        return this;
    }

    @Override
    public <T> ILockedCompoundWrapperToTypeRegistry registerLocking(
      @NotNull final T tInstance, @NotNull final Set<ICompoundInstance> instances)
    {
        return registerLocking(
          EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().wrapInContainer(tInstance, 1d),
          instances
        );
    }

    @Override
    public ImmutableMap<ICompoundContainerWrapper<?>, ImmutableSet<ICompoundInstance>> getLockedInformation()
    {
        return ImmutableMap.copyOf(lockedInformation);
    }
}
