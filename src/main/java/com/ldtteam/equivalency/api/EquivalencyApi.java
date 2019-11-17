package com.ldtteam.equivalency.api;

import com.ldtteam.equivalency.analyzer.EquivalencyRecipeRegistry;
import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.compound.ILockedCompoundInformationRegistry;
import com.ldtteam.equivalency.api.compound.container.information.IValidCompoundTypeInformationProviderRegistry;
import com.ldtteam.equivalency.api.compound.container.registry.ICompoundContainerFactoryRegistry;
import com.ldtteam.equivalency.api.compound.container.registry.ICompoundContainerSerializerRegistry;
import com.ldtteam.equivalency.api.equivalency.IEquivalencyInformationCache;
import com.ldtteam.equivalency.api.gameobject.equivalent.IGameObjectEquivalencyHandlerRegistry;
import com.ldtteam.equivalency.api.recipe.IEquivalencyRecipeRegistry;
import com.ldtteam.equivalency.api.tags.ITagEquivalencyRegistry;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.compound.LockedCompoundInformationRegistry;
import com.ldtteam.equivalency.compound.container.registry.CompoundContainerFactoryRegistry;
import com.ldtteam.equivalency.compound.container.registry.CompoundContainerSerializerRegistry;
import com.ldtteam.equivalency.compound.information.ValidCompoundTypeInformationProviderRegistry;
import com.ldtteam.equivalency.equivalency.EquivalencyInformationCache;
import com.ldtteam.equivalency.gameobject.equivalent.GameObjectEquivalencyHandlerRegistry;
import com.ldtteam.equivalency.tags.TagEquivalencyRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;
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
    public ITagEquivalencyRegistry getTagEquivalencyRegistry()
    {
        return TagEquivalencyRegistry.getInstance();
    }

    @Override
    public IEquivalencyRecipeRegistry getEquivalencyRecipeRegistry(@NotNull final DimensionType dimensionType)
    {
        return EquivalencyRecipeRegistry.getInstance(dimensionType);
    }

    @Override
    public ILockedCompoundInformationRegistry getLockedCompoundWrapperToTypeRegistry(@NotNull final DimensionType dimensionType)
    {
        return LockedCompoundInformationRegistry.getInstance(dimensionType);
    }

    @Override
    public IValidCompoundTypeInformationProviderRegistry getValidCompoundTypeInformationProviderRegistry(@NotNull final DimensionType dimensionType)
    {
        return ValidCompoundTypeInformationProviderRegistry.getInstance(dimensionType);
    }

    @Override
    public IEquivalencyInformationCache getEquivalencyInformationCache(@NotNull final DimensionType dimensionType)
    {
        return EquivalencyInformationCache.getInstance(dimensionType);
    }

    public static void onRegisterNewRegistry(final RegistryEvent.NewRegistry event)
    {
        new RegistryBuilder<ICompoundType>()
          .setType(ICompoundType.class)
          .setName(new ResourceLocation(Constants.MOD_ID, "compound_type"))
          .allowModification()
          .create();
    }
}
