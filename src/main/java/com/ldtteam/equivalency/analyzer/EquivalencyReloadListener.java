package com.ldtteam.equivalency.analyzer;

import com.ldtteam.equivalency.api.compound.ICompoundInstance;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.bootstrap.CommonBootstrapper;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class EquivalencyReloadListener implements IFutureReloadListener
{

    private static final Logger LOGGER = LogManager.getLogger(EquivalencyReloadListener.class);

    @Override
    public CompletableFuture<Void> reload(
      final IStage reloadStage,
      final IResourceManager resourceManager,
      final IProfiler profiler,
      final IProfiler iProfiler1,
      final Executor executor,
      final Executor executor1)
    {
        LOGGER.info("Reloading resources has been triggered, recalculating graph.");
        return CompletableFuture.runAsync(() -> {
            ServerLifecycleHooks.getCurrentServer().getWorlds().forEach(CommonBootstrapper::onWorldReload);
            ServerLifecycleHooks.getCurrentServer().getWorlds().forEach(serverWorld -> {
                JGraphTBasedCompoundAnalyzer analyzer = new JGraphTBasedCompoundAnalyzer(serverWorld);
                analyzer.calculate();
            });
        });
    }

    public static void onServerStarting(final FMLServerStartingEvent serverStartingEvent)
    {
        LOGGER.info("Registering reload listener for graph rebuilding.");
        serverStartingEvent.getServer().getResourceManager().addReloadListener(new EquivalencyReloadListener());
    }


    public static void onServerStarted(final FMLServerStartedEvent serverStartedEvent)
    {
        LOGGER.info("Building initial equivalency graph.");
        ServerLifecycleHooks.getCurrentServer().getWorlds().forEach(CommonBootstrapper::onWorldReload);
        ServerLifecycleHooks.getCurrentServer().getWorlds().forEach(serverWorld -> {
            JGraphTBasedCompoundAnalyzer analyzer = new JGraphTBasedCompoundAnalyzer(serverWorld);
            analyzer.calculate();
        });
    }
}
