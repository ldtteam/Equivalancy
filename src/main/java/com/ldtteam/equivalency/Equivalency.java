package com.ldtteam.equivalency;

import com.ldtteam.equivalency.analyzer.EquivalencyReloadListener;
import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.util.ItemStackUtils;
import com.ldtteam.equivalency.bootstrap.CommonBootstrapper;
import com.ldtteam.equivalency.bootstrap.CompoundTypesRegistrar;
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

    private static final Logger LOGGER = LogManager.getLogger();

    public Equivalency()
    {
        LOGGER.info("Equivalency is being instantiated.");
    }
}
