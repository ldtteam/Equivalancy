package com.ldtteam.equivalency.analyzer;

import com.google.common.collect.Lists;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.bootstrap.WorldBootstrapper;
import com.ldtteam.equivalency.equivalency.EquivalencyInformationCache;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class EquivalencyReloadListener implements ISelectiveResourceReloadListener
{

    private static final Logger LOGGER = LogManager.getLogger(EquivalencyReloadListener.class);

    @SubscribeEvent
    public static void onAddReloadListener(final AddReloadListenerEvent reloadListenerEvent) {
        LOGGER.info("Registering reload listener for graph rebuilding.");
        reloadListenerEvent.addListener(new EquivalencyReloadListener());
    }

    @SubscribeEvent
    public static void onServerStarted(final FMLServerStartedEvent serverStartedEvent)
    {
        LOGGER.info("Building initial equivalency graph.");
        reloadResources();
    }

    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager, final Predicate<IResourceType> resourcePredicate)
    {
        LOGGER.info("Reloading resources has been triggered, recalculating graph.");
        reloadResources();
    }

    private static void reloadResources() {
        if (ServerLifecycleHooks.getCurrentServer() == null)
            return;

        final List<ServerWorld> worlds = Lists.newArrayList(ServerLifecycleHooks.getCurrentServer().getWorlds());
        worlds.forEach(EquivalencyReloadListener::reloadEquivalencyDataFor);
    }

    private static void reloadEquivalencyDataFor(final World world) {
        if (!(world instanceof ServerWorld))
            return;

        final ServerWorld serverWorld = (ServerWorld) world;

        LOGGER.info("Starting equivalency data reload for world: " + world.func_234923_W_().toString());

        WorldBootstrapper.onWorldReload(serverWorld);
        JGraphTBasedCompoundAnalyzer analyzer = new JGraphTBasedCompoundAnalyzer(serverWorld);
        EquivalencyInformationCache.getInstance(serverWorld.func_230315_m_()).set(analyzer.calculate());
    }
}
