package com.ldtteam.equivalency.api.compound.container.information;

import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public interface IValidCompoundTypeInformationProviderRegistry
{

    void resetForWorld(@NotNull final World world);

    IValidCompoundTypeInformationProviderRegistry registerNewProvider(@NotNull final World world, @NotNull final IValidCompoundTypeInformationProvider<?> provider);

    <T> IValidCompoundTypeInformationProviderRegistry registerNewProvider(@NotNull final World world, @NotNull final Class<T> clazz, @NotNull final BiFunction<ICompoundContainerWrapper<T>, ICompoundType, Optional<Boolean>> decider);

    <T> boolean isCompoundTypeValidForWrapper(@NotNull final World world, @NotNull final ICompoundContainerWrapper<T> wrapper, @NotNull final ICompoundType type);
}
