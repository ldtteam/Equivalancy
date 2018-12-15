package com.ldtteam.equivalency.api;

import com.ldtteam.equivalency.analyzer.EquivalencyRecipeRegistry;
import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.compound.container.wrapper.registry.ICompoundContainerWrapperFactoryRegistry;
import com.ldtteam.equivalency.api.recipe.IEquivalencyRecipeRegistry;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.compound.container.registry.CompoundContainerWrapperFactoryRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryManager;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class EquivalencyApi implements IEquivalencyAPI
{
    private static EquivalencyApi ourInstance = new EquivalencyApi();

    public static EquivalencyApi getInstance()
    {
        return ourInstance;
    }

    private final ICompoundContainerWrapperFactoryRegistry compoundContainerWrapperFactoryRegistry = new CompoundContainerWrapperFactoryRegistry();
    private final IEquivalencyRecipeRegistry equivalencyRecipeRegistry = new EquivalencyRecipeRegistry();
    private IForgeRegistry<ICompoundType> compoundTypeRegistry = null;

    private EquivalencyApi()
    {
    }

    @Override
    public ICompoundContainerWrapperFactoryRegistry getCompoundContainerWrapperFactoryRegistry()
    {
        return compoundContainerWrapperFactoryRegistry;
    }

    @Override
    public IEquivalencyRecipeRegistry getEquivalencyRecipeRegistry()
    {
        return equivalencyRecipeRegistry;
    }

    @Override
    public IForgeRegistry<ICompoundType> getCompoundTypeRegistry()
    {
        return compoundTypeRegistry;
    }

    @SubscribeEvent
    public static void onRegistryNewRegistry(final RegistryEvent.NewRegistry event)
    {
        EquivalencyApi.getInstance().compoundTypeRegistry = new RegistryBuilder<ICompoundType>()
          .setName(new ResourceLocation(Constants.MOD_ID, "recipe"))
          .allowModification()
          .create();
    }
}
