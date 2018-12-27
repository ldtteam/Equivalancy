package com.ldtteam.equivalency.analyzer;

import com.google.common.collect.Sets;
import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import com.ldtteam.equivalency.api.util.EquivalencyLogger;
import com.ldtteam.equivalency.api.util.ItemStackUtils;
import com.ldtteam.equivalency.bootstrap.CommonBootstrapper;
import com.ldtteam.equivalency.compound.SimpleCompoundInstance;
import com.ldtteam.equivalency.compound.SimpleCompoundType;
import com.ldtteam.equivalency.compound.container.heat.HeatWrapper;
import com.ldtteam.equivalency.compound.container.itemstack.ItemStackWrapper;
import com.ldtteam.equivalency.compound.test.TestWrapper;
import com.ldtteam.equivalency.heat.Heat;
import com.ldtteam.equivalency.recipe.SimpleEquivalancyRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.RegistryEvent;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class ItemStackAndVanillaCompoundAnalysisTest
{

    public static void main(String[] args) throws Exception
    {
        final ItemStackAndVanillaCompoundAnalysisTest test = new ItemStackAndVanillaCompoundAnalysisTest();
        test.setUp();
        test.calculate();
    }

    @Before
    public void setUp() throws Exception
    {
        Bootstrap.register();

        //Setup API
        EquivalencyApi.onRegistryNewRegistry(new RegistryEvent.NewRegistry());

        //Setup base information
        CommonBootstrapper.Bootstrap();
    }

    @Test
    public void calculate()
    {
        JGraphTBasedCompoundAnalyzer analyzer = new JGraphTBasedCompoundAnalyzer();
        analyzer.calculate(EquivalencyApi.getInstance().getEquivalencyRecipeRegistry());
    }
}
