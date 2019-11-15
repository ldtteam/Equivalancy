package com.ldtteam.equivalency.analyzer;

import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.bootstrap.CommonBootstrapper;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Bootstrap;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ItemStackAndVanillaCompoundAnalysisTest
{

    public static void main(String[] args) throws Exception
    {
        final ItemStackAndVanillaCompoundAnalysisTest test = new ItemStackAndVanillaCompoundAnalysisTest();
        test.setUp();
        test.calculate();
    }

    private final Logger logger = LogManager.getLogger(BaseGraphTBasedCompoundAnalyzerTest.class);
    private       World  world;

    public void setUp() throws Exception
    {
        logger.info("Loading vanilla base information.");
        logger.info("  -> Air blockstate: " + Blocks.AIR.getDefaultState().toString());
        logger.info("  -> Coal registryname: " + Items.COAL.getRegistryName().toString());

        world = Mockito.mock(World.class);;

        //Setup API
        EquivalencyApi.onRegisterNewRegistry(new RegistryEvent.NewRegistry());

        //Setup base information
        CommonBootstrapper.Bootstrap();
    }

    public void calculate()
    {
        JGraphTBasedCompoundAnalyzer analyzer = new JGraphTBasedCompoundAnalyzer(world);
        analyzer.calculate();
    }
}
