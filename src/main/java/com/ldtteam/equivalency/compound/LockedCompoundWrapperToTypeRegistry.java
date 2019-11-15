package com.ldtteam.equivalency.compound;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.api.compound.ICompoundInstance;
import com.ldtteam.equivalency.api.compound.ILockedCompoundWrapperToTypeRegistry;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LockedCompoundWrapperToTypeRegistry implements ILockedCompoundWrapperToTypeRegistry
{
    private final Map<World, Map<ICompoundContainerWrapper<?>, ImmutableSet<ICompoundInstance>>> lockedInformation = new HashMap<>();

    @Override
    public void resetForWorld(@NotNull final World world)
    {
        lockedInformation.remove(world);
    }

    @Override
    public ILockedCompoundWrapperToTypeRegistry registerLocking(
      @NotNull final World world,
      @NotNull final ICompoundContainerWrapper<?> wrapper, @NotNull final Set<ICompoundInstance> instances)
    {
        if (wrapper.getContentsCount() != 1)
            throw new IllegalArgumentException("Can not set locked information with none unit stack.");

        lockedInformation.putIfAbsent(world, new HashMap<>());
        lockedInformation.get(world).put(wrapper, ImmutableSet.copyOf(instances));

        return this;
    }

    @Override
    public <T> ILockedCompoundWrapperToTypeRegistry registerLocking(
      @NotNull final World world,
      @NotNull final T tInstance, @NotNull final Set<ICompoundInstance> instances)
    {
        return registerLocking(
          world,
          EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().wrapInContainer(tInstance, 1d),
          instances
        );
    }

    @Override
    public ImmutableMap<ICompoundContainerWrapper<?>, ImmutableSet<ICompoundInstance>> getLockedInformation(@NotNull final World world)
    {
        if (!lockedInformation.containsKey(world))
            return null;

        return ImmutableMap.copyOf(lockedInformation.get(world));
    }
}
