package com.ldtteam.equivalency.bootstrap;

import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModBiomeFeatureNames;
import com.ldtteam.equivalency.api.util.ModPlacementNames;
import com.ldtteam.equivalency.biome.placements.Height0to3;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.feature.ReplaceBlockFeature;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class PlacementRegistrar
{

    private PlacementRegistrar()
    {
        throw new IllegalStateException("Tried to initialize: PlacementRegistrar but this is a Utility class.");
    }

    @SubscribeEvent
    public static void registerPlacements(@NotNull final RegistryEvent.Register<Placement<?>> event)
    {
        final IForgeRegistry<Placement<?>> registry = event.getRegistry();

        registry.registerAll(
          new Height0to3(NoPlacementConfig.field_236555_a_).setRegistryName(ModPlacementNames.Name.BEDROCK_SINGULARITY)
        );
    }
}
