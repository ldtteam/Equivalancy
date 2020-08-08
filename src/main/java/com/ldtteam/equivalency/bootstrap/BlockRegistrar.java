package com.ldtteam.equivalency.bootstrap;

import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModBlockNames;
import com.ldtteam.equivalency.api.util.ModBlocks;
import com.ldtteam.equivalency.api.util.ModItemGroups;
import com.ldtteam.equivalency.block.tnt.stick.ShapedTNTStickBlock;
import com.ldtteam.equivalency.block.tnt.stick.UnshapedTNTStickBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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
          new UnshapedTNTStickBlock(),
          new ShapedTNTStickBlock()
        );
    }

    @SubscribeEvent
    public static void registerItems(@NotNull final RegistryEvent.Register<Item> event)
    {
        final IForgeRegistry<Item> registry = event.getRegistry();

        registry.registerAll(
          new BlockItem(
            ModBlocks.UNSHAPED_TNT_STICK,
            (new Item.Properties()).group(ModItemGroups.TNT)
          ).setRegistryName(ModBlockNames.Name.Block.UNSHAPED_TNT_STICK),
          new BlockItem(
            ModBlocks.SHAPED_TNT_STICK,
            (new Item.Properties()).group(ModItemGroups.TNT)
          ).setRegistryName(ModBlockNames.Name.Block.SHAPED_TNT_STICK)
        );
    }
}
