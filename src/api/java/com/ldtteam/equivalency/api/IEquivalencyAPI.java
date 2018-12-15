package com.ldtteam.equivalency.api;

import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.compound.container.wrapper.registry.ICompoundContainerWrapperFactoryRegistry;
import com.ldtteam.equivalency.api.recipe.IEquivalencyRecipeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * The API for Equivalency.
 * Retrieved via an IMC with a callback.
 */
public interface IEquivalencyAPI
{

    ICompoundContainerWrapperFactoryRegistry getCompoundContainerWrapperFactoryRegistry();

    IEquivalencyRecipeRegistry getEquivalencyRecipeRegistry();

    IForgeRegistry<ICompoundType> getCompoundTypeRegistry();
}
