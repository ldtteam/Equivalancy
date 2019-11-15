package com.ldtteam.equivalency.compound.information;

import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.api.compound.ICompoundInstance;
import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.compound.container.information.IValidCompoundTypeInformationProvider;
import com.ldtteam.equivalency.api.compound.container.information.IValidCompoundTypeInformationProviderRegistry;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;

public class ValidCompoundTypeInformationProviderRegistry implements IValidCompoundTypeInformationProviderRegistry
{

    private final Map<World, Map<Class<?>, Set<IValidCompoundTypeInformationProvider<?>>>> providers = new HashMap<>();

    @Override
    public void resetForWorld(@NotNull final World world)
    {
        providers.remove(world);
    }

    @Override
    public IValidCompoundTypeInformationProviderRegistry registerNewProvider(@NotNull final World world, @NotNull final IValidCompoundTypeInformationProvider<?> provider)
    {
        providers.putIfAbsent(world, new HashMap<>());
        providers.get(world).putIfAbsent(provider.getWrappedContentType(), new HashSet<>());
        providers.get(world).get(provider.getWrappedContentType()).add(provider);

        return this;
    }

    @Override
    public <T> IValidCompoundTypeInformationProviderRegistry registerNewProvider(@NotNull final World world,
      @NotNull final Class<T> clazz, @NotNull final BiFunction<ICompoundContainerWrapper<T>, ICompoundType, Optional<Boolean>> decider)
    {
        return this.registerNewProvider(world, new SimpleBiFunctionBasedValidCompoundTypeInformationProvider<>(clazz, decider));
    }

    @Override
    public <T> boolean isCompoundTypeValidForWrapper(@NotNull final World world, @NotNull final ICompoundContainerWrapper<T> wrapper, @NotNull final ICompoundType type)
    {
        final Set<ICompoundInstance> lockedInformation = EquivalencyApi.getInstance().getLockedCompoundWrapperToTypeRegistry().getLockedInformation(world).get(wrapper);
        if (lockedInformation != null)
        {
            return lockedInformation.stream().anyMatch(compoundInstance -> compoundInstance.getType().equals(type));
        }

        if (!providers.containsKey(world))
            return true;

        if (!providers.get(world).containsKey(wrapper.getContents().getClass()))
            return true;

        return providers
          .get(world)
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
