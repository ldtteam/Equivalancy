package com.ldtteam.equivalency.bootstrap;

import com.google.common.collect.Sets;
import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import com.ldtteam.equivalency.api.recipe.IEquivalencyRecipe;
import com.ldtteam.equivalency.api.util.ItemStackUtils;
import com.ldtteam.equivalency.api.util.ModCompoundTypes;
import com.ldtteam.equivalency.api.util.TranslationKeys;
import com.ldtteam.equivalency.compound.SimpleCompoundInstance;
import com.ldtteam.equivalency.compound.SimpleCompoundType;
import com.ldtteam.equivalency.compound.container.heat.HeatWrapper;
import com.ldtteam.equivalency.compound.container.itemstack.ItemStackWrapper;
import com.ldtteam.equivalency.recipe.TagEquivalencyRecipe;
import com.ldtteam.equivalency.recipe.SimpleEquivalancyRecipe;
import com.ldtteam.equivalency.recipe.SmeltingEquivalancyRecipe;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class CommonBootstrapper
{
    public static void BootstrapCompoundTypes(final RegistryEvent.Register<ICompoundType> register)
    {
        ModCompoundTypes.AIR = registerCompoundType(register.getRegistry(), new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_AIR)).setRegistryName(new ResourceLocation("equivalency:air")));
        ModCompoundTypes.WATER = registerCompoundType(register.getRegistry(), new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_WATER)).setRegistryName(new ResourceLocation("equivalency:stone")));
        ModCompoundTypes.EARTH = registerCompoundType(register.getRegistry(), new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_EARTH)).setRegistryName(new ResourceLocation("equivalency:earth")));
        ModCompoundTypes.FIRE = registerCompoundType(register.getRegistry(), new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_FIRE)).setRegistryName(new ResourceLocation("equivalency:fire")));
        ModCompoundTypes.CHAOS = registerCompoundType(register.getRegistry(), new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_CHAOS)).setRegistryName(new ResourceLocation("equivalency:chaos")));
        ModCompoundTypes.ORDER = registerCompoundType(register.getRegistry(), new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_ORDER)).setRegistryName(new ResourceLocation("equivalency:order")));

        ModCompoundTypes.TREE = registerCompoundType(register.getRegistry(), new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_TREE)).setRegistryName(new ResourceLocation("equivalency:tree")));
        ModCompoundTypes.BURNABLE = registerCompoundType(register.getRegistry(), new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_BURNABLE)).setRegistryName(new ResourceLocation("equivalency:burnable")));
        ModCompoundTypes.METALIC = registerCompoundType(register.getRegistry(), new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_METALIC)).setRegistryName(new ResourceLocation("equivalency:metalic")));
    }

    private static ICompoundType registerCompoundType(final IForgeRegistry<ICompoundType> registry, final ICompoundType type)
    {
        registry.register(type);
        return type;
    }

    public static void Bootstrap()
    {
        BootstrapWrapperFactories();
        BootstrapEquivalencyHandler();
        BootstrapTagNames();
    }

    private static void BootstrapWrapperFactories()
    {
        EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().registerFactory(new ItemStackWrapper.Factory());
        EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().registerFactory(new HeatWrapper.Factory());
    }
    
    private static void BootstrapTagNames()
    {
        EquivalencyApi.getInstance().getItemStackEquivalentHelperRegistry()
          .registerTagNameToInclude(ItemTags.WOOL.getId())
          .registerTagNameToInclude(ItemTags.PLANKS.getId())
          .registerTagNameToInclude(ItemTags.STONE_BRICKS.getId())
          .registerTagNameToInclude(ItemTags.WOODEN_BUTTONS.getId())
          .registerTagNameToInclude(ItemTags.BUTTONS.getId())
          .registerTagNameToInclude(ItemTags.CARPETS.getId())
          .registerTagNameToInclude(ItemTags.WOODEN_DOORS.getId())
          .registerTagNameToInclude(ItemTags.WOODEN_STAIRS.getId())
          .registerTagNameToInclude(ItemTags.WOODEN_SLABS.getId())
          .registerTagNameToInclude(ItemTags.WOODEN_FENCES.getId())
          .registerTagNameToInclude(ItemTags.WOODEN_PRESSURE_PLATES.getId())
          .registerTagNameToInclude(ItemTags.WOODEN_TRAPDOORS.getId())
          .registerTagNameToInclude(ItemTags.DOORS.getId())
          .registerTagNameToInclude(ItemTags.SAPLINGS.getId())
          .registerTagNameToInclude(ItemTags.LOGS.getId())
          .registerTagNameToInclude(ItemTags.DARK_OAK_LOGS.getId())
          .registerTagNameToInclude(ItemTags.OAK_LOGS.getId())
          .registerTagNameToInclude(ItemTags.BIRCH_LOGS.getId())
          .registerTagNameToInclude(ItemTags.ACACIA_LOGS.getId())
          .registerTagNameToInclude(ItemTags.JUNGLE_LOGS.getId())
          .registerTagNameToInclude(ItemTags.SPRUCE_LOGS.getId())
          .registerTagNameToInclude(ItemTags.BANNERS.getId())
          .registerTagNameToInclude(ItemTags.SAND.getId())
          .registerTagNameToInclude(ItemTags.STAIRS.getId())
          .registerTagNameToInclude(ItemTags.SLABS.getId())
          .registerTagNameToInclude(ItemTags.WALLS.getId())
          .registerTagNameToInclude(ItemTags.ANVIL.getId())
          .registerTagNameToInclude(ItemTags.RAILS.getId())
          .registerTagNameToInclude(ItemTags.LEAVES.getId())
          .registerTagNameToInclude(ItemTags.TRAPDOORS.getId())
          .registerTagNameToInclude(ItemTags.SMALL_FLOWERS.getId())
          .registerTagNameToInclude(ItemTags.BEDS.getId())
          .registerTagNameToInclude(ItemTags.FENCES.getId())
          .registerTagNameToInclude(ItemTags.BOATS.getId())
          .registerTagNameToInclude(ItemTags.FISHES.getId())
          .registerTagNameToInclude(ItemTags.SIGNS.getId())
          .registerTagNameToInclude(ItemTags.MUSIC_DISCS.getId())
          .registerTagNameToInclude(ItemTags.COALS.getId())
          .registerTagNameToInclude(ItemTags.ARROWS.getId())

          .registerTagNameToInclude(Tags.Items.ARROWS.getId())
          .registerTagNameToInclude(Tags.Items.BONES.getId())
          .registerTagNameToInclude(Tags.Items.BOOKSHELVES.getId())
          .registerTagNameToInclude(Tags.Items.CHESTS_ENDER.getId())
          .registerTagNameToInclude(Tags.Items.CHESTS_TRAPPED.getId())
          .registerTagNameToInclude(Tags.Items.CHESTS_WOODEN.getId())
          .registerTagNameToInclude(Tags.Items.COBBLESTONE.getId())
          .registerTagNameToInclude(Tags.Items.CROPS.getId())
          .registerTagNameToInclude(Tags.Items.CROPS_BEETROOT.getId())
          .registerTagNameToInclude(Tags.Items.CROPS_CARROT.getId())
          .registerTagNameToInclude(Tags.Items.CROPS_NETHER_WART.getId())
          .registerTagNameToInclude(Tags.Items.CROPS_POTATO.getId())
          .registerTagNameToInclude(Tags.Items.CROPS_WHEAT.getId())
          .registerTagNameToInclude(Tags.Items.DUSTS_PRISMARINE.getId())
          .registerTagNameToInclude(Tags.Items.DUSTS_REDSTONE.getId())
          .registerTagNameToInclude(Tags.Items.DUSTS_GLOWSTONE.getId())

          .registerTagNameToInclude(Tags.Items.DYES.getId())
          .registerTagNameToInclude(Tags.Items.DYES_BLACK.getId())
          .registerTagNameToInclude(Tags.Items.DYES_RED.getId())
          .registerTagNameToInclude(Tags.Items.DYES_GREEN.getId())
          .registerTagNameToInclude(Tags.Items.DYES_BROWN.getId())
          .registerTagNameToInclude(Tags.Items.DYES_BLUE.getId())
          .registerTagNameToInclude(Tags.Items.DYES_PURPLE.getId())
          .registerTagNameToInclude(Tags.Items.DYES_CYAN.getId())
          .registerTagNameToInclude(Tags.Items.DYES_LIGHT_GRAY.getId())
          .registerTagNameToInclude(Tags.Items.DYES_GRAY.getId())
          .registerTagNameToInclude(Tags.Items.DYES_PINK.getId())
          .registerTagNameToInclude(Tags.Items.DYES_LIME.getId())
          .registerTagNameToInclude(Tags.Items.DYES_YELLOW.getId())
          .registerTagNameToInclude(Tags.Items.DYES_LIGHT_BLUE.getId())
          .registerTagNameToInclude(Tags.Items.DYES_MAGENTA.getId())
          .registerTagNameToInclude(Tags.Items.DYES_ORANGE.getId())
          .registerTagNameToInclude(Tags.Items.DYES_WHITE.getId())

          .registerTagNameToInclude(Tags.Items.EGGS.getId())
          .registerTagNameToInclude(Tags.Items.END_STONES.getId())
          .registerTagNameToInclude(Tags.Items.ENDER_PEARLS.getId())
          .registerTagNameToInclude(Tags.Items.FEATHERS.getId())
          .registerTagNameToInclude(Tags.Items.FENCE_GATES.getId())
          .registerTagNameToInclude(Tags.Items.FENCE_GATES_WOODEN.getId())
          .registerTagNameToInclude(Tags.Items.FENCES.getId())
          .registerTagNameToInclude(Tags.Items.FENCES_NETHER_BRICK.getId())
          .registerTagNameToInclude(Tags.Items.FENCES_WOODEN.getId())
          .registerTagNameToInclude(Tags.Items.GEMS_DIAMOND.getId())
          .registerTagNameToInclude(Tags.Items.GEMS_EMERALD.getId())
          .registerTagNameToInclude(Tags.Items.GEMS_LAPIS.getId())
          .registerTagNameToInclude(Tags.Items.GEMS_PRISMARINE.getId())
          .registerTagNameToInclude(Tags.Items.GEMS_QUARTZ.getId())

          .registerTagNameToInclude(Tags.Items.GLASS.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_BLACK.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_BLUE.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_BROWN.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_COLORLESS.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_CYAN.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_GRAY.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_GREEN.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_LIGHT_BLUE.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_LIGHT_GRAY.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_LIME.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_MAGENTA.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_ORANGE.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PINK.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PURPLE.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_RED.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_WHITE.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_YELLOW.getId())

          .registerTagNameToInclude(Tags.Items.GLASS_PANES.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_BLACK.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_BLUE.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_BROWN.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_COLORLESS.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_CYAN.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_GRAY.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_GREEN.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_LIGHT_BLUE.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_LIGHT_GRAY.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_LIME.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_MAGENTA.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_ORANGE.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_PINK.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_PURPLE.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_RED.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_WHITE.getId())
          .registerTagNameToInclude(Tags.Items.GLASS_PANES_YELLOW.getId())

          .registerTagNameToInclude(Tags.Items.GRAVEL.getId())
          .registerTagNameToInclude(Tags.Items.GUNPOWDER.getId())
          .registerTagNameToInclude(Tags.Items.HEADS.getId())
          .registerTagNameToInclude(Tags.Items.INGOTS_BRICK.getId())
          .registerTagNameToInclude(Tags.Items.INGOTS_GOLD.getId())
          .registerTagNameToInclude(Tags.Items.INGOTS_IRON.getId())
          .registerTagNameToInclude(Tags.Items.INGOTS_NETHER_BRICK.getId())
          .registerTagNameToInclude(Tags.Items.LEATHER.getId())
          .registerTagNameToInclude(Tags.Items.MUSHROOMS.getId())
          .registerTagNameToInclude(Tags.Items.MUSIC_DISCS.getId())
          .registerTagNameToInclude(Tags.Items.NETHER_STARS.getId())
          .registerTagNameToInclude(Tags.Items.NETHERRACK.getId())
          .registerTagNameToInclude(Tags.Items.NUGGETS_GOLD.getId())
          .registerTagNameToInclude(Tags.Items.NUGGETS_IRON.getId())
          .registerTagNameToInclude(Tags.Items.OBSIDIAN.getId())
          .registerTagNameToInclude(Tags.Items.ORES_COAL.getId())
          .registerTagNameToInclude(Tags.Items.ORES_DIAMOND.getId())
          .registerTagNameToInclude(Tags.Items.ORES_EMERALD.getId())
          .registerTagNameToInclude(Tags.Items.ORES_GOLD.getId())
          .registerTagNameToInclude(Tags.Items.ORES_IRON.getId())
          .registerTagNameToInclude(Tags.Items.ORES_LAPIS.getId())
          .registerTagNameToInclude(Tags.Items.ORES_QUARTZ.getId())
          .registerTagNameToInclude(Tags.Items.ORES_REDSTONE.getId())
          .registerTagNameToInclude(Tags.Items.RODS_BLAZE.getId())
          .registerTagNameToInclude(Tags.Items.RODS_WOODEN.getId())

          .registerTagNameToInclude(Tags.Items.SAND.getId())
          .registerTagNameToInclude(Tags.Items.SAND_COLORLESS.getId())
          .registerTagNameToInclude(Tags.Items.SAND_RED.getId())

          .registerTagNameToInclude(Tags.Items.SANDSTONE.getId())
          .registerTagNameToInclude(Tags.Items.SEEDS.getId())
          .registerTagNameToInclude(Tags.Items.SEEDS_BEETROOT.getId())
          .registerTagNameToInclude(Tags.Items.SEEDS_MELON.getId())
          .registerTagNameToInclude(Tags.Items.SEEDS_PUMPKIN.getId())
          .registerTagNameToInclude(Tags.Items.SEEDS_WHEAT.getId())
          .registerTagNameToInclude(Tags.Items.SLIMEBALLS.getId())
          .registerTagNameToInclude(Tags.Items.STAINED_GLASS.getId())
          .registerTagNameToInclude(Tags.Items.STAINED_GLASS_PANES.getId())
          .registerTagNameToInclude(Tags.Items.STONE.getId())
          .registerTagNameToInclude(Tags.Items.STORAGE_BLOCKS_COAL.getId())
          .registerTagNameToInclude(Tags.Items.STORAGE_BLOCKS_DIAMOND.getId())
          .registerTagNameToInclude(Tags.Items.STORAGE_BLOCKS_EMERALD.getId())
          .registerTagNameToInclude(Tags.Items.STORAGE_BLOCKS_GOLD.getId())
          .registerTagNameToInclude(Tags.Items.STORAGE_BLOCKS_IRON.getId())
          .registerTagNameToInclude(Tags.Items.STORAGE_BLOCKS_LAPIS.getId())
          .registerTagNameToInclude(Tags.Items.STORAGE_BLOCKS_QUARTZ.getId())
          .registerTagNameToInclude(Tags.Items.STORAGE_BLOCKS_REDSTONE.getId())
          .registerTagNameToInclude(Tags.Items.STRING.getId());
    }

    private static void BootstrapEquivalencyHandler()
    {
    }

    public static void onWorldReload(final World world)
    {
        ResetDataForWorld(world);

        BootstrapTagInformation(world);
        BootstrapDefaultInformation(world);
        BootstrapDefaultCraftingRecipes(world);
    }

    private static void ResetDataForWorld(final World world)
    {
        EquivalencyApi.getInstance().getValidCompoundTypeInformationProviderRegistry().resetForWorld(world);
        EquivalencyApi.getInstance().getEquivalencyRecipeRegistry().resetForWorld(world);
    }

    private static void BootstrapTagInformation(final World world)
    {
        EquivalencyApi.getInstance().getItemStackEquivalentHelperRegistry().getTagNamesToInclude().forEach(tagName -> {
            final Tag<Item> tag = world.getTags().getItems().get(tagName);
            if (tag == null)
                return;

            final Collection<ItemStack> stacksInTag = tag.getAllElements()
                                                        .stream()
                                                        .map(ItemStack::new)
                                                        .collect(Collectors.toList());

            stacksInTag.forEach(inputStack -> {
                stacksInTag
                  .stream()
                  .filter(outputStack -> !EquivalencyApi.getInstance().getItemStackEquivalentHelperRegistry().areItemStacksEquivalentExceptForStack(inputStack, outputStack))
                  .forEach(outputStack -> {
                      EquivalencyApi.getInstance().getEquivalencyRecipeRegistry()
                        .registerNewRecipe(
                          world,
                          new TagEquivalencyRecipe<>(
                            tag,
                            Sets.newHashSet(EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().wrapInContainer(inputStack, (double) inputStack.getCount())),
                            Sets.newHashSet(EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().wrapInContainer(outputStack, (double) outputStack.getCount()))
                          ));
                  });
            });
        });
    }

    private static void BootstrapDefaultInformation(@NotNull final World world)
    {
        EquivalencyApi.getInstance().getValidCompoundTypeInformationProviderRegistry()
            .registerNewProvider(
              world,
              ItemStack.class,
              (wrappedStack, compoundType) -> {
                if (compoundType != ModCompoundTypes.BURNABLE)
                    return Optional.empty();

                return Optional.of(ForgeHooks.getBurnTime(wrappedStack.getContents()) == 0);
            })
            .registerNewProvider(
              world,
              ItemStack.class,
              (wrappedStack, compoundType) -> {
                if (ItemTags.getCollection().getOwningTags(wrappedStack.getContents().getItem()).stream().anyMatch(r -> r.getPath().contains("ingot")))
                    return Optional.of(compoundType != ModCompoundTypes.EARTH);

                return Optional.empty();
            });

        EquivalencyApi.getInstance().getLockedCompoundWrapperToTypeRegistry().registerLocking(
          world,
          new ItemStack(Blocks.OAK_LOG),
          Sets.newHashSet(
            new SimpleCompoundInstance(ModCompoundTypes.TREE, 8),
            new SimpleCompoundInstance(ModCompoundTypes.BURNABLE, ForgeHooks.getBurnTime(new ItemStack(Blocks.OAK_LOG)))
          )
        );

        EquivalencyApi.getInstance().getLockedCompoundWrapperToTypeRegistry().registerLocking(
          world,
          new ItemStack(Blocks.COBBLESTONE),
          Sets.newHashSet(
            new SimpleCompoundInstance(ModCompoundTypes.EARTH, 8)
          )
        ).registerLocking(
          world,
          new ItemStack(Blocks.CLAY),
          Sets.newHashSet(
            new SimpleCompoundInstance(ModCompoundTypes.EARTH, 4),
            new SimpleCompoundInstance(ModCompoundTypes.WATER, 4)
          )
        ).registerLocking(
          world,
          new ItemStack(Blocks.IRON_ORE, 1),
          Sets.newHashSet(
            new SimpleCompoundInstance(ModCompoundTypes.EARTH, 2),
            new SimpleCompoundInstance(ModCompoundTypes.METALIC, 8)
          )
        ).registerLocking(
          world,
          new ItemStack(Blocks.GOLD_ORE, 1),
          Sets.newHashSet(
            new SimpleCompoundInstance(ModCompoundTypes.EARTH, 2),
            new SimpleCompoundInstance(ModCompoundTypes.METALIC, 8)
          )
        ).registerLocking(
          world,
          new ItemStack(Blocks.COAL_ORE, 1),
          Sets.newHashSet(
            new SimpleCompoundInstance(ModCompoundTypes.EARTH, 2),
            new SimpleCompoundInstance(ModCompoundTypes.CHAOS, 4)
          )
        );
    }

    private static void BootstrapDefaultCraftingRecipes(@NotNull final World world)
    {
        world.getRecipeManager().getRecipes(IRecipeType.CRAFTING)
          .values()
          .forEach(recipe -> {
              processCraftingRecipe(world, recipe);
          });

        world.getRecipeManager().getRecipes(IRecipeType.SMELTING)
          .values()
          .forEach(recipe -> {
              processSmeltingRecipe(world, recipe);
          });
    }

    private static void processSmeltingRecipe(@NotNull final World world, IRecipe iRecipe)
    {
        processIRecipe(world, iRecipe, SmeltingEquivalancyRecipe::new);
    }

    private static void processCraftingRecipe(@NotNull final World world, IRecipe iRecipe)
    {
        processIRecipe(world, iRecipe, SimpleEquivalancyRecipe::new);
    }

    private static void processIRecipe(
      @NotNull final World world,
      IRecipe iRecipe,
      BiFunction<Set<ICompoundContainerWrapper<?>>, Set<ICompoundContainerWrapper<?>>, IEquivalencyRecipe> recipeProducer
    )
    {
        if (iRecipe.getRecipeOutput().isEmpty())
        {
            return;
        }

        final NonNullList<Ingredient> ingredients = iRecipe.getIngredients();
        final List<Ingredient> withOutEmptyIngredients = ingredients.stream()
                                                           .filter(ingredient -> !ingredient.test(ItemStack.EMPTY) && ingredient.getMatchingStacks().length > 0
                                                                                   && !ItemStackUtils.isEmpty(ingredient.getMatchingStacks()[0]))
                                                           .collect(Collectors.toList());

        final List<ItemStack> inputStacks = withOutEmptyIngredients.stream()
                                              .map(ingredient -> ingredient.getMatchingStacks()[0])
                                              .filter(itemStack -> !itemStack.isEmpty())
                                              .collect(Collectors.toList());

        final Set<ICompoundContainerWrapper<?>> wrappedInput = inputStacks
                                                                 .stream()
                                                                 .map(stack -> EquivalencyApi.getInstance()
                                                                                 .getCompoundContainerWrapperFactoryRegistry()
                                                                                 .wrapInContainer(stack, (double) stack.getCount()))
                                                                 .collect(Collectors.toMap(wrapper -> wrapper, wrapper -> wrapper.getContentsCount(), (d1, d2) -> d1 + d2))
                                                                 .entrySet()
                                                                 .stream()
                                                                 .map(iCompoundContainerWrapperDoubleEntry -> EquivalencyApi.getInstance()
                                                                                                                .getCompoundContainerWrapperFactoryRegistry()
                                                                                                                .wrapInContainer(iCompoundContainerWrapperDoubleEntry.getKey()
                                                                                                                                   .getContents(),
                                                                                                                  iCompoundContainerWrapperDoubleEntry.getValue()))
                                                                 .collect(Collectors.toSet());

        final ICompoundContainerWrapper<?> outputWrapped = EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().wrapInContainer(iRecipe.getRecipeOutput(),
          (double) iRecipe.getRecipeOutput().getCount());


        EquivalencyApi.getInstance().getEquivalencyRecipeRegistry().registerNewRecipe(world, recipeProducer.apply(wrappedInput, Sets.newHashSet(outputWrapped)));
    }

}
