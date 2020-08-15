package com.ldtteam.equivalency.bootstrap;

import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModBlocks;
import com.ldtteam.equivalency.api.util.ModTileEntityTypeNames;
import com.ldtteam.equivalency.tileentity.BedrockSingularityTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class TileEntityTypeRegistrar
{

    private TileEntityTypeRegistrar()
    {
        throw new IllegalStateException("Tried to initialize: TileEntityTypeRegistrar but this is a Utility class.");
    }

    @SubscribeEvent
    public static void registerTileEntityTypes(@NotNull final RegistryEvent.Register<TileEntityType<?>> event)
    {
        final IForgeRegistry<TileEntityType<?>> registry = event.getRegistry();

        registry.registerAll(
          TileEntityType.Builder.create(
            BedrockSingularityTileEntity::new,
            ModBlocks.BEDROCK_SINGULARITY
          ).build(null).setRegistryName(ModTileEntityTypeNames.Name.SINGULARITY_TRANSMUTATION)
        );
    }
}
