package com.ldtteam.equivalency.bootstrap;

import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModBiomeFeatureNames;
import com.ldtteam.equivalency.api.util.ModRecipeSerializerNames;
import com.ldtteam.equivalency.recipe.singularity.transmutation.SingularityTransmutationRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.feature.ReplaceBlockFeature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class BiomeFeatureRegistrar
{

    private BiomeFeatureRegistrar()
    {
        throw new IllegalStateException("Tried to initialize: BiomeFeatureRegistrar but this is a Utility class.");
    }

    @SubscribeEvent
    public static void registerBiomeFeatures(@NotNull final RegistryEvent.Register<Feature<?>> event)
    {
        final IForgeRegistry<Feature<?>> registry = event.getRegistry();

        registry.registerAll(
          new ReplaceBlockFeature(ReplaceBlockConfig.field_236604_a_).setRegistryName(ModBiomeFeatureNames.Name.BEDROCK_SINGULARITY)
        );
    }
}
