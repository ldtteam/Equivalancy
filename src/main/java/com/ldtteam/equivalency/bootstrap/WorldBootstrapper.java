package com.ldtteam.equivalency.bootstrap;

import com.google.common.collect.Sets;
import com.ldtteam.equivalency.analyzer.EquivalencyRecipeRegistry;
import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.api.compound.container.ICompoundContainer;
import com.ldtteam.equivalency.api.recipe.IEquivalencyRecipe;
import com.ldtteam.equivalency.api.util.ItemStackUtils;
import com.ldtteam.equivalency.api.util.ModCompoundTypes;
import com.ldtteam.equivalency.api.util.TagUtils;
import com.ldtteam.equivalency.compound.information.LockedCompoundInformationRegistry;
import com.ldtteam.equivalency.compound.simple.SimpleCompoundInstance;
import com.ldtteam.equivalency.compound.container.registry.CompoundContainerFactoryRegistry;
import com.ldtteam.equivalency.compound.information.ValidCompoundTypeInformationProviderRegistry;
import com.ldtteam.equivalency.gameobject.equivalent.GameObjectEquivalencyHandlerRegistry;
import com.ldtteam.equivalency.recipe.SimpleEquivalancyRecipe;
import com.ldtteam.equivalency.recipe.SmeltingEquivalancyRecipe;
import com.ldtteam.equivalency.recipe.TagEquivalencyRecipe;
import com.ldtteam.equivalency.tags.TagEquivalencyRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public final class WorldBootstrapper
{

    private WorldBootstrapper()
    {
        throw new IllegalStateException("Tried to initialize: WorldBootstrapper but this is a Utility class.");
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
        LockedCompoundInformationRegistry.getInstance(world.getDimension().getType()).reset();
        ValidCompoundTypeInformationProviderRegistry.getInstance(world.getDimension().getType()).reset();
        EquivalencyRecipeRegistry.getInstance(world.getDimension().getType()).reset();
    }

    private static void BootstrapTagInformation(final World world)
    {
        TagEquivalencyRegistry.getInstance().get().forEach(tagName -> {
            final List<Tag<?>> tags = TagUtils.get(tagName);
            if (tags.isEmpty())
                return;

            tags.forEach(tag -> {
                final Collection<ICompoundContainer<?>> elementsOfTag = tag.getAllElements()
                                                                          .stream()
                                                                          .map(stack -> CompoundContainerFactoryRegistry.getInstance().wrapInContainer(stack, 1d))
                                                                          .collect(Collectors.toList());

                elementsOfTag.forEach(inputStack -> {
                    elementsOfTag
                      .stream()
                      .filter(outputStack -> !GameObjectEquivalencyHandlerRegistry.getInstance().areGameObjectsEquivalent(inputStack, outputStack))
                      .forEach(outputStack -> {
                          EquivalencyApi.getInstance().getEquivalencyRecipeRegistry(world.dimension.getType())
                            .register(
                              new TagEquivalencyRecipe<>(
                                tag,
                                Sets.newHashSet(inputStack),
                                Sets.newHashSet(outputStack)
                              ));
                      });
                });
            });
        });
    }

    private static void BootstrapDefaultInformation(@NotNull final World world)
    {
        ValidCompoundTypeInformationProviderRegistry.getInstance(world.getDimension().getType())
          .registerNewProvider(
            ItemStack.class,
            (wrappedStack, compoundType) -> {
                if (compoundType != ModCompoundTypes.BURNABLE)
                    return Optional.empty();

                return Optional.of(ForgeHooks.getBurnTime(wrappedStack.getContents()) == 0);
            })
          .registerNewProvider(
            ItemStack.class,
            (wrappedStack, compoundType) -> {
                if (ItemTags.getCollection().getOwningTags(wrappedStack.getContents().getItem()).stream().anyMatch(r -> r.getPath().contains("ingot")))
                    return Optional.of(compoundType != ModCompoundTypes.EARTH);

                return Optional.empty();
            });

        LockedCompoundInformationRegistry.getInstance(world.getDimension().getType()).registerLocking(
          new ItemStack(Blocks.OAK_LOG),
          Sets.newHashSet(
            new SimpleCompoundInstance(ModCompoundTypes.TREE, 8),
            new SimpleCompoundInstance(ModCompoundTypes.BURNABLE, ForgeHooks.getBurnTime(new ItemStack(Blocks.OAK_LOG)))
          )
        );

        LockedCompoundInformationRegistry.getInstance(world.getDimension().getType()).registerLocking(
          new ItemStack(Blocks.COBBLESTONE),
          Sets.newHashSet(
            new SimpleCompoundInstance(ModCompoundTypes.EARTH, 8)
          )
        ).registerLocking(
          new ItemStack(Blocks.CLAY),
          Sets.newHashSet(
            new SimpleCompoundInstance(ModCompoundTypes.EARTH, 4),
            new SimpleCompoundInstance(ModCompoundTypes.WATER, 4)
          )
        ).registerLocking(
          new ItemStack(Blocks.IRON_ORE, 1),
          Sets.newHashSet(
            new SimpleCompoundInstance(ModCompoundTypes.EARTH, 2),
            new SimpleCompoundInstance(ModCompoundTypes.METALIC, 8)
          )
        ).registerLocking(
          new ItemStack(Blocks.GOLD_ORE, 1),
          Sets.newHashSet(
            new SimpleCompoundInstance(ModCompoundTypes.EARTH, 2),
            new SimpleCompoundInstance(ModCompoundTypes.METALIC, 8)
          )
        ).registerLocking(
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
      BiFunction<Set<ICompoundContainer<?>>, Set<ICompoundContainer<?>>, IEquivalencyRecipe> recipeProducer
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

        final Set<ICompoundContainer<?>> wrappedInput = inputStacks
                                                                 .stream()
                                                                 .map(stack -> CompoundContainerFactoryRegistry.getInstance()
                                                                                 .wrapInContainer(stack, (double) stack.getCount()))
                                                                 .collect(Collectors.toMap(wrapper -> wrapper, wrapper -> wrapper.getContentsCount(), (d1, d2) -> d1 + d2))
                                                                 .entrySet()
                                                                 .stream()
                                                                 .map(iCompoundContainerWrapperDoubleEntry -> CompoundContainerFactoryRegistry.getInstance()
                                                                                                                .wrapInContainer(iCompoundContainerWrapperDoubleEntry.getKey()
                                                                                                                                   .getContents(),
                                                                                                                  iCompoundContainerWrapperDoubleEntry.getValue()))
                                                                 .collect(Collectors.toSet());

        final ICompoundContainer<?> outputWrapped = CompoundContainerFactoryRegistry.getInstance().wrapInContainer(iRecipe.getRecipeOutput(),
          (double) iRecipe.getRecipeOutput().getCount());


        EquivalencyRecipeRegistry.getInstance(world.getDimension().getType()).register(recipeProducer.apply(wrappedInput, Sets.newHashSet(outputWrapped)));
    }
}
