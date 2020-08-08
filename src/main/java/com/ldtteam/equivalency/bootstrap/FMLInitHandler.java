package com.ldtteam.equivalency.bootstrap;

import com.ldtteam.equivalency.api.util.Constants;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FMLInitHandler
{
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Setting up Equivalencies static data.");
        CommonBootstrapper.doBootstrap();
    }
}
