package com.ldtteam.equivalency.api.compound.container.wrapper.registry;

import com.google.gson.Gson;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapperFactory;
import org.jetbrains.annotations.NotNull;

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
     */
    <T> void registerFactory(@NotNull ICompoundContainerWrapperFactory<T> tiCompoundContainerWrapperFactory);

    /**
     * Utility method to check if a given class of a compound container can be wrapped properly.
     *
     * @param clazz The class to check
     * @param <T> The type of the compound container to check.
     * @return True when a factory for type T is registered false when not.
     */
    <T> boolean canBeWrapped(@NotNull Class<T> clazz);

    /**
     * Utility method to check if a given instance of a possible compound container can be wrapped properly.
     *
     * @param tInstance The instance to check
     * @param <T> The type of the compound container to check.
     * @return True when a factory for type T is registered false when not.
     */
    <T> boolean canBeWrapped(@NotNull T tInstance);

    /**
     * Wraps the given compound container.
     *
     * @param tInstance The instance of T to wrap. Will be brought to unit length by the factory (ItemStacks will be copied and have stack size 1).
     * @param count The count to store in the container. For ItemStacks this will be the stacks size.
     * @param <T> The type of the compound container to create.
     * @throws IllegalArgumentException When T can not be wrapped properly {@code canBeWrapped(tInstance) == false;}
     *
     * @return The wrapped instance.
     */
    @NotNull
    <T> ICompoundContainerWrapper<T> wrapInContainer(@NotNull T tInstance, @NotNull Double count) throws IllegalArgumentException;

    /**
     * Returns a JSON handler that is capable of reading and writing, all instances to disk.
     * Both collections as well as individual instances are supported.
     *
     * @return The JSON handler.
     */
    @NotNull
    Gson getJsonHandler();
}
