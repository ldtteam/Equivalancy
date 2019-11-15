package com.ldtteam.equivalency;

import com.ldtteam.equivalency.analyzer.EquivalencyReloadListener;
import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.util.ItemStackUtils;
import com.ldtteam.equivalency.bootstrap.CommonBootstrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathFinder;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("equivalency")
public class Equivalency
{

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Equivalency()
    {
        LOGGER.info("Equivalency is starting.");
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(EquivalencyApi::onRegisterNewRegistry);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ICompoundType.class, CommonBootstrapper::BootstrapCompoundTypes);

        MinecraftForge.EVENT_BUS.addListener(EquivalencyReloadListener::onServerStarting);
        MinecraftForge.EVENT_BUS.addListener(EquivalencyReloadListener::onServerStarted);

    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Setting up Equivalencies static data.");
        CommonBootstrapper.Bootstrap();
    }



}
