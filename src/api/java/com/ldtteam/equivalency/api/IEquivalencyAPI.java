package com.ldtteam.equivalency.api;

import com.ldtteam.equivalency.api.compound.ILockedCompoundInformationRegistry;
import com.ldtteam.equivalency.api.compound.container.information.IValidCompoundTypeInformationProviderRegistry;
import com.ldtteam.equivalency.api.compound.container.registry.ICompoundContainerFactoryRegistry;
import com.ldtteam.equivalency.api.compound.container.registry.ICompoundContainerSerializerRegistry;
import com.ldtteam.equivalency.api.equivalency.IEquivalencyInformationCache;
import com.ldtteam.equivalency.api.gameobject.equivalent.IGameObjectEquivalencyHandlerRegistry;
import com.ldtteam.equivalency.api.recipe.IEquivalencyRecipeRegistry;
import com.ldtteam.equivalency.api.tags.ITagEquivalencyRegistry;
import com.ldtteam.equivalency.api.gameobject.loottable.ILootTableAnalyserRegistry;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * The API for Equivalency.
 * Retrieved via an IMC with a callback.
 */
public interface IEquivalencyAPI
{

    /**
     * Gives access to the registry that handles the callbacks that convert game objects to their wrapped instances.
     * @return The registry that handles the callbacks used to convert game objects into wrapped counterparts.
     */
    ICompoundContainerFactoryRegistry getCompoundContainerFactoryRegistry();

    /**
     * Gives access to the registry that handles the callbacks that serialize and deserialize the game object with compounds
     * from and to disk.
     *
     * @return The registry that handles the callbacks for serialization and deserialization of wrapped game objects.
     */
    ICompoundContainerSerializerRegistry getCompoundContainerSerializerRegistry();

    /**
     * Gives access to a registry which handles the registration of callbacks which can tell the system if two objects are equal to one another.
     *
     * @return The registry which handles callbacks for equivalency checks.
     */
    IGameObjectEquivalencyHandlerRegistry getGameObjectEquivalencyHandlerRegistry();

    /**
     * Gives access to a registry which handles analysers for equivalencies based on loottables.
     *
     * @return The loot table analyser registry.
     */
    ILootTableAnalyserRegistry getLootTableAnalyserRegistry();

    /**
     * Gives access to a registry which handles equivalencies via tags.
     * @return The registry which allows the analysis engine to take tags into account.
     */
    ITagEquivalencyRegistry getTagEquivalencyRegistry();

    /**
     * Gives access to the registry that holds the recipe information for a given world.
     * @param worldKey The world key to get the equivalency recipe information for.
     * @return The recipe registry for a given world.
     */
    IEquivalencyRecipeRegistry getEquivalencyRecipeRegistry(@NotNull final RegistryKey<World> worldKey);

    /**
     * Gives access to the registry that contains the locking information for game objects and wrappers in a given world.
     *
     * @param worldKey The world key that represents the world for which locking information registry is being retrieved.
     * @return The registry for locking type information for a given world.
     */
    ILockedCompoundInformationRegistry getLockedCompoundWrapperToTypeRegistry(@NotNull final RegistryKey<World> worldKey);

    /**
     * Gives access to the registry that contains the information providers that handle the compound validation logic during analysis for a given world.
     *
     * @param worldKey The world key.
     * @return The registry containing information providers that handle the compound validation logic for a given world.
     */
    IValidCompoundTypeInformationProviderRegistry getValidCompoundTypeInformationProviderRegistry(@NotNull final RegistryKey<World> worldKey);

    /**
     * Gives access to the cache that contains the equivalency information after calculation.
     *
     * @param dimensionType The dimension type to get the equivalency cache for.
     * @return The equivalency cache for a given dimension.
     */
    IEquivalencyInformationCache getEquivalencyInformationCache(@NotNull final DimensionType dimensionType);


}
