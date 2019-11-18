package com.ldtteam.equivalency.bootstrap;

import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.util.ItemStackUtils;
import com.ldtteam.equivalency.api.util.ModCompoundTypes;
import com.ldtteam.equivalency.api.util.TranslationKeys;
import com.ldtteam.equivalency.compound.SimpleCompoundType;
import com.ldtteam.equivalency.compound.container.blockstate.BlockContainer;
import com.ldtteam.equivalency.compound.container.heat.HeatContainer;
import com.ldtteam.equivalency.compound.container.itemstack.ItemStackContainer;
import com.ldtteam.equivalency.compound.container.registry.CompoundContainerFactoryRegistry;
import com.ldtteam.equivalency.compound.container.registry.CompoundContainerSerializerRegistry;
import com.ldtteam.equivalency.gameobject.equivalent.GameObjectEquivalencyHandlerRegistry;
import com.ldtteam.equivalency.tags.TagEquivalencyRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Optional;

public class CommonBootstrapper
{
    public static void BootstrapCompoundTypes(final RegistryEvent.Register<ICompoundType> register)
    {
        register.getRegistry().register(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_AIR)).setRegistryName(new ResourceLocation("equivalency:air")));
        register.getRegistry().register(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_WATER)).setRegistryName(new ResourceLocation("equivalency:stone")));
        register.getRegistry().register(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_EARTH)).setRegistryName(new ResourceLocation("equivalency:earth")));
        register.getRegistry().register(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_FIRE)).setRegistryName(new ResourceLocation("equivalency:fire")));
        register.getRegistry().register(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_CHAOS)).setRegistryName(new ResourceLocation("equivalency:chaos")));
        register.getRegistry().register(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_ORDER)).setRegistryName(new ResourceLocation("equivalency:order")));

        register.getRegistry().register(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_TREE)).setRegistryName(new ResourceLocation("equivalency:tree")));
        register.getRegistry().register(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_BURNABLE)).setRegistryName(new ResourceLocation("equivalency:burnable")));
        register.getRegistry().register(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_METALIC)).setRegistryName(new ResourceLocation("equivalency:metalic")));
    }

    private static ICompoundType registerCompoundType(final IForgeRegistry<ICompoundType> registry, final ICompoundType type)
    {
        registry.register(type);
        return type;
    }

    public static void Bootstrap()
    {
        BootstrapWrapperFactories();
        BootstrapSerializerFactories();
        BootstrapEquivalencyHandler();
        BootstrapTagNames();
    }

    private static void BootstrapWrapperFactories()
    {
        CompoundContainerFactoryRegistry.getInstance().register(new ItemStackContainer.ItemStackFactory());
        CompoundContainerFactoryRegistry.getInstance().register(new ItemStackContainer.ItemFactory());
        CompoundContainerFactoryRegistry.getInstance().register(new HeatContainer.Factory());
        CompoundContainerFactoryRegistry.getInstance().register(new BlockContainer.BlockStateFactory());
        CompoundContainerFactoryRegistry.getInstance().register(new BlockContainer.BlockFactory());
    }

    private static void BootstrapSerializerFactories()
    {
        CompoundContainerSerializerRegistry.getInstance().register(new ItemStackContainer.Serializer());
        CompoundContainerSerializerRegistry.getInstance().register(new HeatContainer.Serializer());
        CompoundContainerSerializerRegistry.getInstance().register(new BlockContainer.Serializer());
    }
    
    private static void BootstrapTagNames()
    {
        TagEquivalencyRegistry.getInstance()
          .addTag(ItemTags.WOOL.getId())
          .addTag(ItemTags.PLANKS.getId())
          .addTag(ItemTags.STONE_BRICKS.getId())
          .addTag(ItemTags.WOODEN_BUTTONS.getId())
          .addTag(ItemTags.BUTTONS.getId())
          .addTag(ItemTags.CARPETS.getId())
          .addTag(ItemTags.WOODEN_DOORS.getId())
          .addTag(ItemTags.WOODEN_STAIRS.getId())
          .addTag(ItemTags.WOODEN_SLABS.getId())
          .addTag(ItemTags.WOODEN_FENCES.getId())
          .addTag(ItemTags.WOODEN_PRESSURE_PLATES.getId())
          .addTag(ItemTags.WOODEN_TRAPDOORS.getId())
          .addTag(ItemTags.SAPLINGS.getId())
          .addTag(ItemTags.LOGS.getId())
          .addTag(ItemTags.DARK_OAK_LOGS.getId())
          .addTag(ItemTags.OAK_LOGS.getId())
          .addTag(ItemTags.BIRCH_LOGS.getId())
          .addTag(ItemTags.ACACIA_LOGS.getId())
          .addTag(ItemTags.JUNGLE_LOGS.getId())
          .addTag(ItemTags.SPRUCE_LOGS.getId())
          .addTag(ItemTags.BANNERS.getId())
          .addTag(ItemTags.SAND.getId())
          .addTag(ItemTags.WALLS.getId())
          .addTag(ItemTags.ANVIL.getId())
          .addTag(ItemTags.LEAVES.getId())
          .addTag(ItemTags.SMALL_FLOWERS.getId())
          .addTag(ItemTags.BEDS.getId())
          .addTag(ItemTags.FISHES.getId())
          .addTag(ItemTags.SIGNS.getId())
          .addTag(ItemTags.MUSIC_DISCS.getId())
          .addTag(ItemTags.ARROWS.getId())

          .addTag(Tags.Items.ARROWS.getId())
          .addTag(Tags.Items.BONES.getId())
          .addTag(Tags.Items.BOOKSHELVES.getId())
          .addTag(Tags.Items.CHESTS_ENDER.getId())
          .addTag(Tags.Items.CHESTS_TRAPPED.getId())
          .addTag(Tags.Items.CHESTS_WOODEN.getId())
          .addTag(Tags.Items.COBBLESTONE.getId())
          .addTag(Tags.Items.CROPS_BEETROOT.getId())
          .addTag(Tags.Items.CROPS_CARROT.getId())
          .addTag(Tags.Items.CROPS_NETHER_WART.getId())
          .addTag(Tags.Items.CROPS_POTATO.getId())
          .addTag(Tags.Items.CROPS_WHEAT.getId())
          .addTag(Tags.Items.DUSTS_PRISMARINE.getId())
          .addTag(Tags.Items.DUSTS_REDSTONE.getId())
          .addTag(Tags.Items.DUSTS_GLOWSTONE.getId())

          .addTag(Tags.Items.DYES.getId())
          .addTag(Tags.Items.DYES_BLACK.getId())
          .addTag(Tags.Items.DYES_RED.getId())
          .addTag(Tags.Items.DYES_GREEN.getId())
          .addTag(Tags.Items.DYES_BROWN.getId())
          .addTag(Tags.Items.DYES_BLUE.getId())
          .addTag(Tags.Items.DYES_PURPLE.getId())
          .addTag(Tags.Items.DYES_CYAN.getId())
          .addTag(Tags.Items.DYES_LIGHT_GRAY.getId())
          .addTag(Tags.Items.DYES_GRAY.getId())
          .addTag(Tags.Items.DYES_PINK.getId())
          .addTag(Tags.Items.DYES_LIME.getId())
          .addTag(Tags.Items.DYES_YELLOW.getId())
          .addTag(Tags.Items.DYES_LIGHT_BLUE.getId())
          .addTag(Tags.Items.DYES_MAGENTA.getId())
          .addTag(Tags.Items.DYES_ORANGE.getId())
          .addTag(Tags.Items.DYES_WHITE.getId())

          .addTag(Tags.Items.EGGS.getId())
          .addTag(Tags.Items.ENDER_PEARLS.getId())
          .addTag(Tags.Items.FEATHERS.getId())
          .addTag(Tags.Items.FENCE_GATES.getId())
          .addTag(Tags.Items.FENCE_GATES_WOODEN.getId())
          .addTag(Tags.Items.FENCES.getId())
          .addTag(Tags.Items.FENCES_NETHER_BRICK.getId())
          .addTag(Tags.Items.FENCES_WOODEN.getId())
          .addTag(Tags.Items.GEMS_DIAMOND.getId())
          .addTag(Tags.Items.GEMS_EMERALD.getId())
          .addTag(Tags.Items.GEMS_LAPIS.getId())
          .addTag(Tags.Items.GEMS_PRISMARINE.getId())
          .addTag(Tags.Items.GEMS_QUARTZ.getId())

          .addTag(Tags.Items.GLASS.getId())
          .addTag(Tags.Items.GLASS_BLACK.getId())
          .addTag(Tags.Items.GLASS_BLUE.getId())
          .addTag(Tags.Items.GLASS_BROWN.getId())
          .addTag(Tags.Items.GLASS_COLORLESS.getId())
          .addTag(Tags.Items.GLASS_CYAN.getId())
          .addTag(Tags.Items.GLASS_GRAY.getId())
          .addTag(Tags.Items.GLASS_GREEN.getId())
          .addTag(Tags.Items.GLASS_LIGHT_BLUE.getId())
          .addTag(Tags.Items.GLASS_LIGHT_GRAY.getId())
          .addTag(Tags.Items.GLASS_LIME.getId())
          .addTag(Tags.Items.GLASS_MAGENTA.getId())
          .addTag(Tags.Items.GLASS_ORANGE.getId())
          .addTag(Tags.Items.GLASS_PINK.getId())
          .addTag(Tags.Items.GLASS_PURPLE.getId())
          .addTag(Tags.Items.GLASS_RED.getId())
          .addTag(Tags.Items.GLASS_WHITE.getId())
          .addTag(Tags.Items.GLASS_YELLOW.getId())

          .addTag(Tags.Items.GLASS_PANES.getId())
          .addTag(Tags.Items.GLASS_PANES_BLACK.getId())
          .addTag(Tags.Items.GLASS_PANES_BLUE.getId())
          .addTag(Tags.Items.GLASS_PANES_BROWN.getId())
          .addTag(Tags.Items.GLASS_PANES_COLORLESS.getId())
          .addTag(Tags.Items.GLASS_PANES_CYAN.getId())
          .addTag(Tags.Items.GLASS_PANES_GRAY.getId())
          .addTag(Tags.Items.GLASS_PANES_GREEN.getId())
          .addTag(Tags.Items.GLASS_PANES_LIGHT_BLUE.getId())
          .addTag(Tags.Items.GLASS_PANES_LIGHT_GRAY.getId())
          .addTag(Tags.Items.GLASS_PANES_LIME.getId())
          .addTag(Tags.Items.GLASS_PANES_MAGENTA.getId())
          .addTag(Tags.Items.GLASS_PANES_ORANGE.getId())
          .addTag(Tags.Items.GLASS_PANES_PINK.getId())
          .addTag(Tags.Items.GLASS_PANES_PURPLE.getId())
          .addTag(Tags.Items.GLASS_PANES_RED.getId())
          .addTag(Tags.Items.GLASS_PANES_WHITE.getId())
          .addTag(Tags.Items.GLASS_PANES_YELLOW.getId())

          .addTag(Tags.Items.GRAVEL.getId())
          .addTag(Tags.Items.GUNPOWDER.getId())
          .addTag(Tags.Items.HEADS.getId())
          .addTag(Tags.Items.INGOTS_BRICK.getId())
          .addTag(Tags.Items.INGOTS_GOLD.getId())
          .addTag(Tags.Items.INGOTS_IRON.getId())
          .addTag(Tags.Items.INGOTS_NETHER_BRICK.getId())
          .addTag(Tags.Items.LEATHER.getId())
          .addTag(Tags.Items.MUSHROOMS.getId())
          .addTag(Tags.Items.MUSIC_DISCS.getId())
          .addTag(Tags.Items.NETHER_STARS.getId())
          .addTag(Tags.Items.NETHERRACK.getId())
          .addTag(Tags.Items.NUGGETS_GOLD.getId())
          .addTag(Tags.Items.NUGGETS_IRON.getId())
          .addTag(Tags.Items.OBSIDIAN.getId())
          .addTag(Tags.Items.ORES_COAL.getId())
          .addTag(Tags.Items.ORES_DIAMOND.getId())
          .addTag(Tags.Items.ORES_EMERALD.getId())
          .addTag(Tags.Items.ORES_GOLD.getId())
          .addTag(Tags.Items.ORES_IRON.getId())
          .addTag(Tags.Items.ORES_LAPIS.getId())
          .addTag(Tags.Items.ORES_QUARTZ.getId())
          .addTag(Tags.Items.ORES_REDSTONE.getId())
          .addTag(Tags.Items.RODS_BLAZE.getId())
          .addTag(Tags.Items.RODS_WOODEN.getId())

          .addTag(Tags.Items.SAND.getId())
          .addTag(Tags.Items.SAND_COLORLESS.getId())
          .addTag(Tags.Items.SAND_RED.getId())

          .addTag(Tags.Items.SANDSTONE.getId())
          .addTag(Tags.Items.SEEDS.getId())
          .addTag(Tags.Items.SEEDS_BEETROOT.getId())
          .addTag(Tags.Items.SEEDS_MELON.getId())
          .addTag(Tags.Items.SEEDS_PUMPKIN.getId())
          .addTag(Tags.Items.SEEDS_WHEAT.getId())
          .addTag(Tags.Items.SLIMEBALLS.getId())
          .addTag(Tags.Items.STAINED_GLASS.getId())
          .addTag(Tags.Items.STAINED_GLASS_PANES.getId())
          .addTag(Tags.Items.STONE.getId())
          .addTag(Tags.Items.STORAGE_BLOCKS_COAL.getId())
          .addTag(Tags.Items.STORAGE_BLOCKS_DIAMOND.getId())
          .addTag(Tags.Items.STORAGE_BLOCKS_EMERALD.getId())
          .addTag(Tags.Items.STORAGE_BLOCKS_GOLD.getId())
          .addTag(Tags.Items.STORAGE_BLOCKS_IRON.getId())
          .addTag(Tags.Items.STORAGE_BLOCKS_LAPIS.getId())
          .addTag(Tags.Items.STORAGE_BLOCKS_QUARTZ.getId())
          .addTag(Tags.Items.STORAGE_BLOCKS_REDSTONE.getId())
          .addTag(Tags.Items.STRING.getId());
    }

    private static void BootstrapEquivalencyHandler()
    {
        //Handle itemstack equivalency:
        GameObjectEquivalencyHandlerRegistry.getInstance()
          .registerNewHandler(
            ItemStack.class,
            (left, right) -> Optional.of(ItemStackUtils.compareItemStacksIgnoreStackSize(left.getContents(), right.getContents()))
          );

        //Handle block equivalency:
        GameObjectEquivalencyHandlerRegistry.getInstance()
          .registerNewHandler(
            Block.class,
            (left, right) -> Optional.of(left.getContents().getRegistryName().toString().equals(right.getContents().getRegistryName().toString()))
          );
    }



}
