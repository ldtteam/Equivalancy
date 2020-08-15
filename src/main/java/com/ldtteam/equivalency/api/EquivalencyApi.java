package com.ldtteam.equivalency.api;

import com.ldtteam.equivalency.analyzer.EquivalencyRecipeRegistry;
import com.ldtteam.equivalency.api.compound.ILockedCompoundInformationRegistry;
import com.ldtteam.equivalency.api.compound.container.information.IValidCompoundTypeInformationProviderRegistry;
import com.ldtteam.equivalency.api.compound.container.registry.ICompoundContainerFactoryRegistry;
import com.ldtteam.equivalency.api.compound.container.registry.ICompoundContainerSerializerRegistry;
import com.ldtteam.equivalency.api.equivalency.IEquivalencyInformationCache;
import com.ldtteam.equivalency.api.gameobject.equivalent.IGameObjectEquivalencyHandlerRegistry;
import com.ldtteam.equivalency.api.recipe.equivalency.IEquivalencyRecipeRegistry;
import com.ldtteam.equivalency.api.tags.ITagEquivalencyRegistry;
import com.ldtteam.equivalency.compound.information.LockedCompoundInformationRegistry;
import com.ldtteam.equivalency.compound.container.registry.CompoundContainerFactoryRegistry;
import com.ldtteam.equivalency.compound.container.registry.CompoundContainerSerializerRegistry;
import com.ldtteam.equivalency.compound.information.ValidCompoundTypeInformationProviderRegistry;
import com.ldtteam.equivalency.equivalency.EquivalencyInformationCache;
import com.ldtteam.equivalency.gameobject.equivalent.GameObjectEquivalencyHandlerRegistry;
import com.ldtteam.equivalency.api.gameobject.loottable.ILootTableAnalyserRegistry;
import com.ldtteam.equivalency.gameobject.loottable.LootTableAnalyserRegistry;
import com.ldtteam.equivalency.tags.TagEquivalencyRegistry;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class EquivalencyApi implements IEquivalencyAPI
{
    private static EquivalencyApi ourInstance = new EquivalencyApi();

    public static EquivalencyApi getInstance()
    {
        return ourInstance;
    }

    private EquivalencyApi()
    {
    }

    @Override
    public ICompoundContainerFactoryRegistry getCompoundContainerFactoryRegistry()
    {
        return CompoundContainerFactoryRegistry.getInstance();
    }

    @Override
    public ICompoundContainerSerializerRegistry getCompoundContainerSerializerRegistry()
    {
        return CompoundContainerSerializerRegistry.getInstance();
    }

    @Override
    public IGameObjectEquivalencyHandlerRegistry getGameObjectEquivalencyHandlerRegistry()
    {
        return GameObjectEquivalencyHandlerRegistry.getInstance();
    }

    @Override
    public ILootTableAnalyserRegistry getLootTableAnalyserRegistry()
    {
        return LootTableAnalyserRegistry.getInstance();
    }

    @Override
    public ITagEquivalencyRegistry getTagEquivalencyRegistry()
    {
        return TagEquivalencyRegistry.getInstance();
    }

    @Override
    public IEquivalencyRecipeRegistry getEquivalencyRecipeRegistry(@NotNull final RegistryKey<World> worldKey)
    {
        return EquivalencyRecipeRegistry.getInstance(worldKey);
    }

    @Override
    public ILockedCompoundInformationRegistry getLockedCompoundWrapperToTypeRegistry(@NotNull final RegistryKey<World> worldKey)
    {
        return LockedCompoundInformationRegistry.getInstance(worldKey);
    }

    @Override
    public IValidCompoundTypeInformationProviderRegistry getValidCompoundTypeInformationProviderRegistry(@NotNull final RegistryKey<World> worldKey)
    {
        return ValidCompoundTypeInformationProviderRegistry.getInstance(worldKey);
    }

    @Override
    public IEquivalencyInformationCache getEquivalencyInformationCache(@NotNull final DimensionType dimensionType)
    {
        return EquivalencyInformationCache.getInstance(dimensionType);
    }
}
