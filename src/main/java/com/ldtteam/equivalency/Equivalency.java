package com.ldtteam.equivalency;

import com.ldtteam.equivalency.analyzer.EquivalencyReloadListener;
import com.ldtteam.equivalency.api.EquivalencyApi;
import net.minecraft.pathfinding.PathFinder;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
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
        MinecraftForge.EVENT_BUS.addListener(EquivalencyReloadListener::onServerStarting);
        MinecraftForge.EVENT_BUS.addListener(EquivalencyReloadListener::onServerStarted);
        MinecraftForge.EVENT_BUS.addListener(EquivalencyApi::onRegisterNewRegistry);
    }


}
