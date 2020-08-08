package com.ldtteam.equivalency.bootstrap;

import com.ldtteam.equivalency.api.util.ModBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientBootstrapper
{
    private static final Logger LOGGER = LogManager.getLogger();

    public static void doBootstrap() {
        LOGGER.info("Bootstrapping Equivalency client side information.");
        doBootstrapRenderTypes();
    }

    private static void doBootstrapRenderTypes() {
        LOGGER.debug("Registering block render types.");
        RenderTypeLookup.setRenderLayer(ModBlocks.UNSHAPED_TNT_STICK, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.SHAPED_TNT_STICK, RenderType.getCutout());
    }
}
