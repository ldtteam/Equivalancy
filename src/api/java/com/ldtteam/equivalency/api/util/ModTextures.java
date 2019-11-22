package com.ldtteam.equivalency.api.util;

import net.minecraft.util.ResourceLocation;

public final class ModTextures
{

    private ModTextures()
    {
        throw new IllegalStateException("Tried to initialize: ModTextures but this is a Utility class.");
    }


    /*

                TIER 0
                Basic compounds that make up everything.

     */
    public static ResourceLocation LOGO_COMPOUND_AIR = new ResourceLocation(Constants.MOD_ID, "compounds/logos/air");

    public static ResourceLocation LOGO_COMPOUND_EARTH = new ResourceLocation(Constants.MOD_ID, "compounds/logos/earth");

    public static ResourceLocation LOGO_COMPOUND_WATER = new ResourceLocation(Constants.MOD_ID, "compounds/logos/water");

    public static ResourceLocation LOGO_COMPOUND_FIRE = new ResourceLocation(Constants.MOD_ID, "compounds/logos/fire");

    public static ResourceLocation LOGO_COMPOUND_CHAOS = new ResourceLocation(Constants.MOD_ID, "compounds/logos/chaos");

    public static ResourceLocation LOGO_COMPOUND_ORDER = new ResourceLocation(Constants.MOD_ID, "compounds/logos/order");


    /*

                TIER 1
                Initial combinations of tier 0 compounds
                Represents initial stages of life, and everything that comes with it.

     */
    public static ResourceLocation LOGO_COMPOUND_TIME = new ResourceLocation(Constants.MOD_ID, "compounds/logos/time");

    public static ResourceLocation LOGO_COMPOUND_LIFE = new ResourceLocation(Constants.MOD_ID, "compounds/logos/life");

    public static ResourceLocation LOGO_COMPOUND_ENERGY = new ResourceLocation(Constants.MOD_ID, "compounds/logos/energy");


    /*

                TIER 2
                Medium tiered combinations, further combines initial compounds, possibly with tier 1 compounds.
                Represents life in its first forms, seeds and movement of bacteria etc.
                Introduces spiritual and elemental concepts.

     */
    public static ResourceLocation LOGO_COMPOUND_SEED = new ResourceLocation(Constants.MOD_ID, "compounds/logos/seed");

    public static ResourceLocation LOGO_COMPOUND_BURNABLE = new ResourceLocation(Constants.MOD_ID, "compounds/logos/burnable");

    public static ResourceLocation LOGO_COMPOUND_MOVEMENT = new ResourceLocation(Constants.MOD_ID, "compounds/logos/movement");

    public static ResourceLocation LOGO_COMPOUND_ELEMENTAL = new ResourceLocation(Constants.MOD_ID, "compounds/logos/elemental");

    public static ResourceLocation LOGO_COMPOUND_SPIRITUAL = new ResourceLocation(Constants.MOD_ID, "compounds/logos/spiritual");


    /*

                TIER 3
                Advanced tier combinations, combines all previous tiers together.


     */
    public static ResourceLocation LOGO_COMPOUND_PLANTLIKE = new ResourceLocation(Constants.MOD_ID, "compounds/logos/plantlike");

    public static ResourceLocation LOGO_COMPOUND_METALIC = new ResourceLocation(Constants.MOD_ID, "compounds/logos/metalic");

    public static ResourceLocation LOGO_COMPOUND_LIGHT = new ResourceLocation(Constants.MOD_ID, "compounds/logos/light");

    public static ResourceLocation LOGO_COMPOUND_KNOWLEDGE = new ResourceLocation(Constants.MOD_ID, "compounds/logos/knowledge");



    public static ResourceLocation LOGO_COMPOUND_EXCHANGE = new ResourceLocation(Constants.MOD_ID, "compounds/logos/exchange");

    public static ResourceLocation LOGO_COMPOUND_TREE = new ResourceLocation(Constants.MOD_ID, "compounds/logos/tree");

    public static ResourceLocation LOGO_COMPOUND_TOOL = new ResourceLocation(Constants.MOD_ID, "compounds/logos/tool");

    public static ResourceLocation LOGO_COMPOUND_AUTOMATON = new ResourceLocation(Constants.MOD_ID, "compounds/logos/automaton");


    public static ResourceLocation LOGO_COMPOUND_MACHINE = new ResourceLocation(Constants.MOD_ID, "compounds/logos/machine");

    public static ResourceLocation LOGO_COMPOUND_DEFENSE = new ResourceLocation(Constants.MOD_ID, "compounds/logos/defense");

    public static ResourceLocation LOGO_COMPOUND_OFFENSE = new ResourceLocation(Constants.MOD_ID, "compounds/logos/offense");

    public static ResourceLocation LOGO_COMPOUND_HARVEST = new ResourceLocation(Constants.MOD_ID, "compounds/logos/harvest");



    public static ResourceLocation LOGO_COMPOUND_PRODUCTION = new ResourceLocation(Constants.MOD_ID, "compounds/logos/production");
}
