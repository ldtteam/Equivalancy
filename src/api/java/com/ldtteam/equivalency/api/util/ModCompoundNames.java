package com.ldtteam.equivalency.api.util;

import com.ldtteam.equivalency.api.compound.ICompoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

public final class ModCompoundNames
{

    private ModCompoundNames()
    {
        throw new IllegalStateException("Tried to initialize: TranslationKeys but this is a Utility class.");
    }

    /*

                TIER 0
                Basic compounds that make up everything.

     */
    public static ResourceLocation AIR = new ResourceLocation(Constants.MOD_ID, "air");

    public static ResourceLocation EARTH = new ResourceLocation(Constants.MOD_ID, "earth");

    public static ResourceLocation WATER = new ResourceLocation(Constants.MOD_ID, "water");

    public static ResourceLocation FIRE = new ResourceLocation(Constants.MOD_ID, "fire");

    public static ResourceLocation CHAOS = new ResourceLocation(Constants.MOD_ID, "chaos");

    public static ResourceLocation ORDER = new ResourceLocation(Constants.MOD_ID, "order");


    /*

                TIER 1
                Initial combinations of tier 0 compounds
                Represents initial stages of life, and everything that comes with it.

     */
    public static ResourceLocation TIME = new ResourceLocation(Constants.MOD_ID, "time");

    public static ResourceLocation LIFE = new ResourceLocation(Constants.MOD_ID, "life");

    public static ResourceLocation ENERGY = new ResourceLocation(Constants.MOD_ID, "energy");


    /*

                TIER 2
                Medium tiered combinations, further combines initial compounds, possibly with tier 1 compounds.
                Represents life in its first forms, seeds and movement of bacteria etc.
                Introduces spiritual and elemental concepts.

     */
    public static ResourceLocation SEED = new ResourceLocation(Constants.MOD_ID, "seed");

    public static ResourceLocation BURNABLE = new ResourceLocation(Constants.MOD_ID, "burnable");

    public static ResourceLocation MOVEMENT = new ResourceLocation(Constants.MOD_ID, "movement");

    public static ResourceLocation ELEMENTAL = new ResourceLocation(Constants.MOD_ID, "elemental");

    public static ResourceLocation SPIRITUAL = new ResourceLocation(Constants.MOD_ID, "spiritual");


    /*

                TIER 3
                Advanced tier combinations, combines all previous tiers together.


     */
    public static ResourceLocation PLANTLIKE = new ResourceLocation(Constants.MOD_ID, "plantlike");

    public static ResourceLocation METALIC = new ResourceLocation(Constants.MOD_ID, "metalic");

    public static ResourceLocation LIGHT = new ResourceLocation(Constants.MOD_ID, "light");

    public static ResourceLocation KNOWLEDGE = new ResourceLocation(Constants.MOD_ID, "knowledge");



    public static ResourceLocation EXCHANGE = new ResourceLocation(Constants.MOD_ID, "exchange");

    public static ResourceLocation TREE = new ResourceLocation(Constants.MOD_ID, "tree");

    public static ResourceLocation TOOL = new ResourceLocation(Constants.MOD_ID, "tool");

    public static ResourceLocation AUTOMATON = new ResourceLocation(Constants.MOD_ID, "automaton");


    public static ResourceLocation MACHINE = new ResourceLocation(Constants.MOD_ID, "machine");

    public static ResourceLocation DEFENSE = new ResourceLocation(Constants.MOD_ID, "defense");

    public static ResourceLocation OFFENSE = new ResourceLocation(Constants.MOD_ID, "offense");

    public static ResourceLocation HARVEST = new ResourceLocation(Constants.MOD_ID, "harvest");



    public static ResourceLocation PRODUCTION = new ResourceLocation(Constants.MOD_ID, "production");
}
