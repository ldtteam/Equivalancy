package com.ldtteam.equivalency.bootstrap;

import com.ldtteam.equivalency.api.client.drawable.implementations.TextureDrawable;
import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModCompoundNames;
import com.ldtteam.equivalency.api.util.ModTextures;
import com.ldtteam.equivalency.compound.simple.builder.SimpleCompoundTypeBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CompoundTypesRegistrar
{

    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onRegisterNewRegistry(final RegistryEvent.NewRegistry event)
    {
        LOGGER.info("Registering the compound type registry with forge.");
        new RegistryBuilder<ICompoundType>()
          .setType(ICompoundType.class)
          .setName(new ResourceLocation(Constants.MOD_ID, "compound_type"))
          .allowModification()
          .create();
    }

    @SubscribeEvent
    public static void BootstrapCompoundTypes(final RegistryEvent.Register<ICompoundType> registerEvent)
    {
        LOGGER.info("Registering compound types.");
        final IForgeRegistry<ICompoundType> registry = registerEvent.getRegistry();
        
        /*

                TIER 0
                Basic compounds that make up everything.

        */
        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.AIR)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_AIR))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.EARTH)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_EARTH))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.WATER)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_WATER))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.FIRE)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_FIRE))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.CHAOS)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_CHAOS))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.ORDER)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_ORDER))
            .create()
        );


        /*
    
                    TIER 1
                    Initial combinations of tier 0 compounds
                    Represents initial stages of life, and everything that comes with it.
    
         */
        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.TIME)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_TIME))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.LIFE)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_LIFE))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.ENERGY)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_ENERGY))
            .create()
        );


        /*
    
                    TIER 2
                    Medium tiered combinations, further combines initial compounds, possibly with tier 1 compounds.
                    Represents life in its first forms, seeds and movement of bacteria etc.
                    Introduces spiritual and elemental concepts.
    
         */
        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.SEED)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_SEED))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.BURNABLE)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_BURNABLE))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.MOVEMENT)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_MOVEMENT))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.ELEMENTAL)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_ELEMENTAL))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.SPIRITUAL)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_SPIRITUAL))
            .create()
        );


        /*
    
                    TIER 3
                    Advanced tier combinations, combines all previous tiers together.
    
    
         */
        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.PLANTLIKE)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_PLANTLIKE))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.METALIC)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_METALIC))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.LIGHT)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_LIGHT))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.KNOWLEDGE)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_KNOWLEDGE))
            .create()
        );


        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.EXCHANGE)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_EXCHANGE))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.TREE)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_TREE))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.TOOL)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_TOOL))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.AUTOMATON)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_AUTOMATON))
            .create()
        );


        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.MACHINE)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_MACHINE))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.DEFENSE)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_DEFENSE))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.OFFENSE)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_OFFENSE))
            .create()
        );

        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.HARVEST)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_HARVEST))
            .create()
        );


        registry.register(
          new SimpleCompoundTypeBuilder()
            .withName(ModCompoundNames.PRODUCTION)
            .setColor(0xffb340)
            .setDrawableSupplier(() -> () -> new TextureDrawable(ModTextures.LOGO_COMPOUND_PRODUCTION))
            .create()
        );
    }
}
