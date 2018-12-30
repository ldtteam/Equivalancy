package com.ldtteam.equivalency.analyzer;

import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.bootstrap.CommonBootstrapper;
import net.minecraft.init.Bootstrap;
import net.minecraftforge.event.RegistryEvent;
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
