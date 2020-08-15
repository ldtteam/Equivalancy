package com.ldtteam.equivalency.bootstrap;

import com.ldtteam.equivalency.api.tileentity.AbstractBedrockSingularityTileEntity;
import com.ldtteam.equivalency.api.util.ModBlocks;
import com.ldtteam.equivalency.api.util.ModTileEntityTypes;
import com.ldtteam.equivalency.client.tileentity.renderer.BedrockSingularityTileEntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;

public class ClientBootstrapper
{
    private static final Logger LOGGER = LogManager.getLogger();

    static void doBootstrap() {
        LOGGER.info("Bootstrapping Equivalency client side information.");
        doBootstrapRenderTypes();
        doBootstrapTileEntityRenderers();
    }

    private static void doBootstrapRenderTypes() {
        LOGGER.debug("Registering block render types.");
    }

    private static void doBootstrapTileEntityRenderers() {
        LOGGER.debug("Registering block tile entity renderers.");
        ClientRegistry.bindTileEntityRenderer(
          ModTileEntityTypes.SINGULARITY_TRANSLATION,
          BedrockSingularityTileEntityRenderer::new
        );
    }
}
