package com.ldtteam.equivalency.compound.information;

import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.api.compound.ICompoundInstance;
import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.compound.container.information.IValidCompoundTypeInformationProvider;
import com.ldtteam.equivalency.api.compound.container.information.IValidCompoundTypeInformationProviderRegistry;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;

public class ValidCompoundTypeInformationProviderRegistry implements IValidCompoundTypeInformationProviderRegistry
{

    private final Map<Class<?>, Set<IValidCompoundTypeInformationProvider<?>>> providers = new HashMap<>();

    @Override
    public IValidCompoundTypeInformationProviderRegistry registerNewProvider(@NotNull final IValidCompoundTypeInformationProvider<?> provider)
    {
        providers.putIfAbsent(provider.getWrappedContentType(), new HashSet<>());
        providers.get(provider.getWrappedContentType()).add(provider);

        return this;
    }

    @Override
    public <T> IValidCompoundTypeInformationProviderRegistry registerNewProvider(
      @NotNull final Class<T> clazz, @NotNull final BiFunction<ICompoundContainerWrapper<T>, ICompoundType, Optional<Boolean>> decider)
    {
        return this.registerNewProvider(new SimpleBiFunctionBasedValidCompoundTypeInformationProvider<>(clazz, decider));
    }

    @Override
    public <T> boolean isCompoundTypeValidForWrapper(@NotNull final ICompoundContainerWrapper<T> wrapper, @NotNull final ICompoundType type)
    {
        final Set<ICompoundInstance> lockedInformation = EquivalencyApi.getInstance().getLockedCompoundWrapperToTypeRegistry().getLockedInformation().get(wrapper);
        if (lockedInformation != null)
        {
            return lockedInformation.stream().anyMatch(compoundInstance -> compoundInstance.getType().equals(type));
        }

        if (!providers.containsKey(wrapper.getContents().getClass()))
            return true;

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
