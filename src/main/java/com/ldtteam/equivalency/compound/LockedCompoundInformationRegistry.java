package com.ldtteam.equivalency.compound;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.ldtteam.equivalency.api.compound.ICompoundInstance;
import com.ldtteam.equivalency.api.compound.ILockedCompoundInformationRegistry;
import com.ldtteam.equivalency.api.compound.container.ICompoundContainer;
import com.ldtteam.equivalency.compound.container.registry.CompoundContainerWrapperFactoryRegistry;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class LockedCompoundInformationRegistry implements ILockedCompoundInformationRegistry
{
    private static final Map<DimensionType, LockedCompoundInformationRegistry> INSTANCES = Maps.newConcurrentMap();

    public static LockedCompoundInformationRegistry getInstance(@NotNull final DimensionType dimensionType)
    {
        return INSTANCES.computeIfAbsent(dimensionType, (dimType) -> new LockedCompoundInformationRegistry());
    }

    private LockedCompoundInformationRegistry()
    {
    }

    private final Map<ICompoundContainer<?>, ImmutableSet<ICompoundInstance>> lockedInformation = Maps.newConcurrentMap();

    @Override
    public ILockedCompoundInformationRegistry registerLocking(
      @NotNull final ICompoundContainer<?> wrapper, @NotNull final Set<ICompoundInstance> instances)
    {
        if (wrapper.getContentsCount() != 1)
            throw new IllegalArgumentException("Can not set locked information with none unit stack.");

        lockedInformation.put(wrapper, ImmutableSet.copyOf(instances));

        return this;
    }

    @Override
    public <T> ILockedCompoundInformationRegistry registerLocking(
      @NotNull final T tInstance, @NotNull final Set<ICompoundInstance> instances)
    {
        return registerLocking(
          CompoundContainerWrapperFactoryRegistry.getInstance().wrapInContainer(tInstance, 1d),
          instances
        );
    }

    public void reset()
    {
        lockedInformation.clear();
    }

    public ImmutableMap<ICompoundContainer<?>, ImmutableSet<ICompoundInstance>> get()
    {
        return ImmutableMap.copyOf(lockedInformation);
    }
}
