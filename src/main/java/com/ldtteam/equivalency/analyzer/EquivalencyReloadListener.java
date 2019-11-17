package com.ldtteam.equivalency.analyzer;

import com.google.common.collect.Lists;
import com.ldtteam.equivalency.bootstrap.WorldBootstrapper;
import com.ldtteam.equivalency.equivalency.EquivalencyInformationCache;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

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
        return performReloadPerWorldAsync();
    }

    public static void onServerStarting(final FMLServerStartingEvent serverStartingEvent)
    {
        LOGGER.info("Registering reload listener for graph rebuilding.");
        serverStartingEvent.getServer().getResourceManager().addReloadListener(new EquivalencyReloadListener());
    }


    public static void onServerStarted(final FMLServerStartedEvent serverStartedEvent)
    {
        LOGGER.info("Building initial equivalency graph.");
        performReloadPerWorldAsync().join();
    }

    private static CompletableFuture<Void> performReloadPerWorldAsync()
    {
        final List<World> world = Lists.newArrayList(ServerLifecycleHooks.getCurrentServer().getWorlds());

        return CompletableFuture.allOf(
            world.stream().map(w -> {
                return CompletableFuture.runAsync(() -> {
                    WorldBootstrapper.onWorldReload(w);
                }).thenRun(() -> {
                    JGraphTBasedCompoundAnalyzer analyzer = new JGraphTBasedCompoundAnalyzer(w);
                    EquivalencyInformationCache.getInstance(w.getDimension().getType()).set(analyzer.calculate());
                });
            }).toArray(CompletableFuture[]::new)
        );
    }
}
