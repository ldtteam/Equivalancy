package com.ldtteam.equivalency.bootstrap;

import com.google.common.collect.Sets;
import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.api.IEquivalencyAPI;
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
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class CommonBootstrapper
{



    public static void Bootstrap()
    {
        BootstrapCompoundTypes();
        BootstrapWrapperFactories();
        BootstrapEquivalencyHandler();
    }

    private static void BootstrapCompoundTypes()
    {
        ModCompoundTypes.AIR = registerCompoundType(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_AIR)).setRegistryName(new ResourceLocation("equivalency:air")));
        ModCompoundTypes.WATER = registerCompoundType(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_WATER)).setRegistryName(new ResourceLocation("equivalency:stone")));
        ModCompoundTypes.EARTH = registerCompoundType(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_EARTH)).setRegistryName(new ResourceLocation("equivalency:earth")));
        ModCompoundTypes.FIRE = registerCompoundType(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_FIRE)).setRegistryName(new ResourceLocation("equivalency:fire")));
        ModCompoundTypes.CHAOS = registerCompoundType(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_CHAOS)).setRegistryName(new ResourceLocation("equivalency:chaos")));
        ModCompoundTypes.ORDER = registerCompoundType(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_ORDER)).setRegistryName(new ResourceLocation("equivalency:order")));

        ModCompoundTypes.TREE = registerCompoundType(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_TREE)).setRegistryName(new ResourceLocation("equivalency:tree")));
        ModCompoundTypes.BURNABLE = registerCompoundType(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_BURNABLE)).setRegistryName(new ResourceLocation("equivalency:burnable")));
        ModCompoundTypes.METALIC = registerCompoundType(new SimpleCompoundType(new StringTextComponent(TranslationKeys.COMPOUND_METALIC)).setRegistryName(new ResourceLocation("equivalency:metalic")));
    }

    private static ICompoundType registerCompoundType(ICompoundType type)
    {
        EquivalencyApi.getInstance().getCompoundTypeRegistry().register(type);
        return type;
    }

    private static void BootstrapWrapperFactories()
    {
        EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().registerFactory(new ItemStackWrapper.Factory());
        EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().registerFactory(new HeatWrapper.Factory());
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
        world.getTags().getItems().getTagMap().values().forEach(tag -> {
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
