package com.ldtteam.equivalency.api.compound.container.wrapper;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.ldtteam.equivalency.api.compound.container.ICompoundContainer;
import org.jetbrains.annotations.NotNull;

/**
 * Wraps compound containers (like ItemStacks, FluidStacks, etc) into their relevant wrapper implementation.
 *
 * This class is also responsible for reading and writing compound container implementations to disk.
 *
 * @param <T> The type of compound container this can wrap.
 */
public interface ICompoundContainerWrapperFactory<T> extends JsonSerializer<ICompoundContainer<T>>, JsonDeserializer<ICompoundContainer<T>>
{

    /**
     * Returns a class that defines what type is contained in the container that this factory produces.
     * If {@code T == ItemStack} for example this will need to return {@code ItemStack.class}
     *
     * @return The class of T
     */
    @NotNull
    Class<T> getContainedTypeClass();

    /**
     * Method used to wrap the instance of T into a wrapper that holds the relevant information and allows for sorting and
     * efficient storage and compare of two wrapped instances.
     *
     * This method should "clone" the given instance and make the clone have unit length. So:
     * {@code final ItemStack clone = ItemStack.copy(); clone.setStackSize(1);}
     * for an ItemStack. Adapt for relevant T implementation.
     *
     * @param instance The instance to wrap.
     * @param count The count to wrap.
     * @return The wrapped instance.
     */
    @NotNull
    ICompoundContainer<T> wrap(@NotNull final Object instance, @NotNull final double count);
}
