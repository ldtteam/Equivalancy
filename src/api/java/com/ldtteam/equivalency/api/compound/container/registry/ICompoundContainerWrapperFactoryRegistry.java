package com.ldtteam.equivalency.api.compound.container.registry;

import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapperFactory;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A Registry type for factories that handle wrapping compound containers, like ItemStacks.
 * Making this a registry instead of hardcoding allows for easier expansion of the compound system into new and several types, like entities and power.
 */
public interface ICompoundContainerWrapperFactoryRegistry
{
    /**
     * Registers a new container factory to this registry.
     *
     * @param tiCompoundContainerWrapperFactory The compound container factory to register.
     * @param <T> The type of compound container (ItemStack, FluidStack, IBlockState etc).
     * @return The registry with the factory added.
     */
    <T> ICompoundContainerWrapperFactoryRegistry registerFactory(@NotNull ICompoundContainerWrapperFactory<T> tiCompoundContainerWrapperFactory);

    /**
     * Registers a conversion function that allows the system to convert one game object into another type before it is being wrapped into a container.
     * Allowed a single container to be used in multiple cases by converting one game object into another.
     *
     * Examples are items to itemstacks, and blocks to their default state.
     *
     * @param input The class representing the input type for the converter.
     * @param output The class representing the output type for the converter.
     * @param converter The converter.
     * @param <T> The input type.
     * @param <R> The output type.
     * @return The registry with the converter added.
     */
    <T, R> ICompoundContainerWrapperFactoryRegistry registerConverter(@NotNull final Class<T> input, @NotNull final Class<R> output, @NotNull final Function<T, R> converter);
}
