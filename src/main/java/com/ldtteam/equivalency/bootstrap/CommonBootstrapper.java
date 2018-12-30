package com.ldtteam.equivalency.bootstrap;

import com.google.common.collect.Sets;
import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import com.ldtteam.equivalency.api.util.ItemStackUtils;
import com.ldtteam.equivalency.api.util.ModCompoundTypes;
import com.ldtteam.equivalency.api.util.TranslationKeys;
import com.ldtteam.equivalency.compound.SimpleCompoundInstance;
import com.ldtteam.equivalency.compound.SimpleCompoundType;
import com.ldtteam.equivalency.compound.container.heat.HeatWrapper;
import com.ldtteam.equivalency.compound.container.itemstack.ItemStackWrapper;
import com.ldtteam.equivalency.recipe.SimpleEquivalancyRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CommonBootstrapper
{

    public static void Bootstrap()
    {
        BootstrapCompoundTypes();
        BootstrapWrapperFactories();
        BootstrapDefaultCraftingRecipes();
        BootstrapOreDictionaryInformation();
        BootstrapDefaultInformation();
        BootstrapEquivalencyHandler();
    }

    private static void BootstrapOreDictionaryInformation()
    {
        Arrays.stream(OreDictionary.getOreNames())
          .forEach(oreDicName -> {
              OreDictionary.getOres(oreDicName)
                .forEach(inputStack -> {
                    OreDictionary.getOres(oreDicName)
                      .stream()
                      .filter(outputStack -> !EquivalencyApi.getInstance().getItemStackEquivalentHelperRegistry().areItemStacksEquivalentExceptForStack(inputStack, outputStack))
                      .forEach(outputStack -> {
                          EquivalencyApi.getInstance().getEquivalencyRecipeRegistry()
                            .registerNewRecipe(new SimpleEquivalancyRecipe(
                              Sets.newHashSet(EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().wrapInContainer(inputStack, (double) inputStack.getCount())),
                              Sets.newHashSet(EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().wrapInContainer(outputStack, (double) outputStack.getCount()))
                            ));
                      });
                });
          });
    }

    private static void BootstrapCompoundTypes()
    {
        ModCompoundTypes.TREE = registerCompoundType(new SimpleCompoundType(new TextComponentString(TranslationKeys.COMPOUND_TREE)).setRegistryName("equivalency:tree"));
        ModCompoundTypes.BURNABLE = registerCompoundType(new SimpleCompoundType(new TextComponentString(TranslationKeys.COMPOUND_BURNABLE)).setRegistryName("equivalency:burnable"));
        ModCompoundTypes.STONE = registerCompoundType(new SimpleCompoundType(new TextComponentString(TranslationKeys.COMPOUND_STONE)).setRegistryName("equivalency:stone"));
        ModCompoundTypes.AIR = registerCompoundType(new SimpleCompoundType(new TextComponentString(TranslationKeys.COMPOUND_AIR)).setRegistryName("equivalency:air"));
    }

    private static void BootstrapWrapperFactories()
    {
        EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().registerFactory(new ItemStackWrapper.Factory());
        EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().registerFactory(new HeatWrapper.Factory());
    }

    private static void BootstrapDefaultCraftingRecipes()
    {
        CraftingManager.REGISTRY
          .forEach(iRecipe -> {
              if (iRecipe.getRecipeOutput().isEmpty())
                  return;

              final NonNullList<Ingredient> ingredients = iRecipe.getIngredients();
              final List<Ingredient> withOutEmptyIngredients = ingredients.stream()
                                                                 .filter(ingredient -> !ingredient.apply(ItemStack.EMPTY) && ingredient.getMatchingStacks().length > 0 && !ItemStackUtils.isEmpty(ingredient.getMatchingStacks()[0]))
                                                                 .collect(Collectors.toList());

              final List<ItemStack> inputStacks = withOutEmptyIngredients.stream()
                                                    .map(ingredient -> ingredient.getMatchingStacks()[0])
                                                    .filter(itemStack -> !itemStack.isEmpty())
                                                    .collect(Collectors.toList());

              final Set<ICompoundContainerWrapper<?>> wrappedInput = inputStacks
                                                                       .stream()
                                                                       .map(stack -> EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().wrapInContainer(stack, (double) stack.getCount()))
                                                                       .collect(Collectors.toMap(wrapper->wrapper, wrapper->wrapper.getContentsCount(), (d1, d2) -> d1 + d2))
                                                                       .entrySet()
                                                                       .stream()
                                                                       .map(iCompoundContainerWrapperDoubleEntry -> EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().wrapInContainer(iCompoundContainerWrapperDoubleEntry.getKey().getContents(), iCompoundContainerWrapperDoubleEntry.getValue()))
                                                                       .collect(Collectors.toSet());

              final ICompoundContainerWrapper<?> outputWrapped = EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().wrapInContainer(iRecipe.getRecipeOutput(),
                (double) iRecipe.getRecipeOutput().getCount());


              EquivalencyApi.getInstance().getEquivalencyRecipeRegistry().registerNewRecipe(new SimpleEquivalancyRecipe(wrappedInput, Sets.newHashSet(outputWrapped)));
          });
        FurnaceRecipes.instance()
          .getSmeltingList()
          .forEach((input, output) -> {
              final ICompoundContainerWrapper<?> inputWrapped = EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().wrapInContainer(input,
                (double) input.getCount());

              final ICompoundContainerWrapper<?> outputWrapped = EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().wrapInContainer(output,
                (double) output.getCount());

              EquivalencyApi.getInstance().getEquivalencyRecipeRegistry().registerNewRecipe(new SimpleEquivalancyRecipe(
                Sets.newHashSet(inputWrapped),
                Sets.newHashSet(outputWrapped)
              ));
          });
    }

    private static void BootstrapDefaultInformation()
    {
        EquivalencyApi.getInstance().getValidCompoundTypeInformationProviderRegistry()
          .registerNewProvider(ItemStack.class, (wrappedStack, compoundType) -> {
              if (!compoundType.getRegistryName().toString().equals("equivalency:burnable"))
                  return Optional.empty();

              return Optional.of(TileEntityFurnace.isItemFuel(wrappedStack.getContents()));
          });

        EquivalencyApi.getInstance().getLockedCompoundWrapperToTypeRegistry().registerLocking(
          new ItemStack(Blocks.LOG),
          Sets.newHashSet(
            new SimpleCompoundInstance(ModCompoundTypes.TREE, 8),
            new SimpleCompoundInstance(ModCompoundTypes.BURNABLE, 300)
          )
        );
    }

    private static void BootstrapEquivalencyHandler()
    {

    }

    private static ICompoundType registerCompoundType(ICompoundType type)
    {
        EquivalencyApi.getInstance().getCompoundTypeRegistry().register(type);
        return type;
    }
}
