package com.ldtteam.equivalency.api.compound.container.wrapper;

import net.minecraft.util.ITabCompleter;

import java.util.Set;

/**
 * Holds a object that is made up out of compounds.
 * @param <T>
 */
public interface ICompoundContainerWrapper<T> extends Comparable<ICompoundContainerWrapper<?>>
{

    /**
     * The contents of this container.
     * Set to the 1 unit of the content type {@link T}
     *
     * @return The contents.
     */
    T getContents();

    /**
     * The amount of {@link T}s contained in this wrapper.
     * @return The amount.
     */
    Double getContentsCount();

    /**
     * Returns a set of wrappers that this wrapper is equivalent to.
     * Allows for equivalency between object types (different ItemStacks (oredic), but also ItemStack -> BlockState mappings)
     *
     * @return The equivalent mappings.
     */
    Set<ICompoundContainerWrapper<?>> isEquivalentTo();
}
