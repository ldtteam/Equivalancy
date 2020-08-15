package com.ldtteam.equivalency.bootstrap;

import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModBlockNames;
import com.ldtteam.equivalency.api.util.ModBlocks;
import com.ldtteam.equivalency.block.BedrockSingularityBlock;
import com.ldtteam.equivalency.block.IngestionTableBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class BlockRegistrar
{

    private BlockRegistrar()
    {
        throw new IllegalStateException("Tried to initialize: BlockRegistrar but this is a Utility class.");
    }

    @SubscribeEvent
    public static void registerBlocks(@NotNull final RegistryEvent.Register<Block> event)
    {
        final IForgeRegistry<Block> registry = event.getRegistry();

        registry.registerAll(
          new BedrockSingularityBlock(AbstractBlock.Properties.create(Material.PORTAL, MaterialColor.BLACK).hardnessAndResistance(-1.0F, 3600000.0F).setLightLevel(state -> 15).noDrops().setAllowsSpawn((p_test_1_, p_test_2_, p_test_3_, p_test_4_) -> false)).setRegistryName(ModBlockNames.Name.BEDROCK_SINGULARITY),
          new IngestionTableBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)).setRegistryName(ModBlockNames.Name.INGESTION_TABLE)
        );
    }

    @SubscribeEvent
    public static void registerItems(@NotNull final RegistryEvent.Register<Item> event)
    {
        final IForgeRegistry<Item> registry = event.getRegistry();

        registry.registerAll(
          new BlockItem(
            ModBlocks.BEDROCK_SINGULARITY,
            (new Item.Properties())
          ).setRegistryName(ModBlockNames.Name.BEDROCK_SINGULARITY),
          new BlockItem(ModBlocks.INGESTION_TABLE,
            (new Item.Properties()))
          .setRegistryName(ModBlockNames.Name.INGESTION_TABLE)
        );
    }
}
