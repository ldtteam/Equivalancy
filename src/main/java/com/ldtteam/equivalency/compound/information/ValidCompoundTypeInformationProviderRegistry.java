package com.ldtteam.equivalency.compound.information;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ldtteam.equivalency.api.compound.ICompoundInstance;
import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.compound.container.information.IValidCompoundTypeInformationProvider;
import com.ldtteam.equivalency.api.compound.container.information.IValidCompoundTypeInformationProviderRegistry;
import com.ldtteam.equivalency.api.compound.container.ICompoundContainer;
import com.ldtteam.equivalency.compound.LockedCompoundInformationRegistry;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;

public class ValidCompoundTypeInformationProviderRegistry implements IValidCompoundTypeInformationProviderRegistry
{

    private static final Map<DimensionType, ValidCompoundTypeInformationProviderRegistry> INSTANCES = Maps.newConcurrentMap();

    public static ValidCompoundTypeInformationProviderRegistry getInstance(@NotNull final DimensionType dimensionType)
    {
        return INSTANCES.computeIfAbsent(dimensionType, ValidCompoundTypeInformationProviderRegistry::new);
    }

    private final Map<Class<?>, Set<IValidCompoundTypeInformationProvider<?>>> providers = Maps.newConcurrentMap();
    private final DimensionType                                                dimType;

    private ValidCompoundTypeInformationProviderRegistry(final DimensionType dimType)
    {
        this.dimType = dimType;
    }

    @Override
    public IValidCompoundTypeInformationProviderRegistry registerNewProvider(@NotNull final IValidCompoundTypeInformationProvider<?> provider)
    {
        providers.computeIfAbsent(provider.getWrappedContentType(), (type) -> Sets.newConcurrentHashSet()).add(provider);
        return this;
    }

    @Override
    public <T> IValidCompoundTypeInformationProviderRegistry registerNewProvider(
      @NotNull final Class<T> clazz, @NotNull final BiFunction<ICompoundContainer<T>, ICompoundType, Optional<Boolean>> decider)
    {
        return this.registerNewProvider(new SimpleBiFunctionBasedValidCompoundTypeInformationProvider<>(clazz, decider));
    }

    public void reset()
    {
        providers.clear();
    }

    public <T> boolean isCompoundTypeValidForWrapper(
      @NotNull final ICompoundContainer<T> wrapper,
      @NotNull final ICompoundType type
    )
    {
        final Set<ICompoundInstance> lockedInformation = LockedCompoundInformationRegistry.getInstance(dimType).get().get(wrapper);
        if (lockedInformation != null)
        {
            return lockedInformation.stream().anyMatch(compoundInstance -> compoundInstance.getType().equals(type));
        }

        if (!providers.containsKey(wrapper.getContents().getClass()))
        {
            return true;
        }

        return providers
                 .get(wrapper.getContents().getClass())
                 .stream()
                 .map(provider -> (IValidCompoundTypeInformationProvider<T>) provider)
                 .map(provider -> provider.canWrapperHaveCompound(wrapper, type))
                 .filter(Optional::isPresent)
                 .findFirst()
                 .orElse(Optional.of(true))
                 .orElse(true);
    }
}
