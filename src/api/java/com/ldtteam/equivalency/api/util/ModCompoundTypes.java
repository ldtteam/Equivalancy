package com.ldtteam.equivalency.api.util;

import com.ldtteam.equivalency.api.compound.ICompoundType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Constants.MOD_ID)
public final class ModCompoundTypes
{

    private ModCompoundTypes()
    {
        throw new IllegalStateException("Tried to initialize: ModCompoundTypes but this is a Utility class.");
    }

    /*

                TIER 0
                Basic compounds that make up everything.

     */
    @ObjectHolder("air")
    public static ICompoundType AIR;

    @ObjectHolder("earth")
    public static ICompoundType EARTH;

    @ObjectHolder("water")
    public static ICompoundType WATER;

    @ObjectHolder("fire")
    public static ICompoundType FIRE;

    @ObjectHolder("chaos")
    public static ICompoundType CHAOS;

    @ObjectHolder("order")
    public static ICompoundType ORDER;


    /*

                TIER 1
                Initial combinations of tier 0 compounds
                Represents initial stages of life, and everything that comes with it.

     */
    @ObjectHolder("time")
    public static ICompoundType TIME; //CHAOS + ORDER

    @ObjectHolder("life")
    public static ICompoundType LIFE; //WATER + EARTH + AIR + CHAOS

    @ObjectHolder("energy")
    public static ICompoundType ENERGY; //ORDER + FIRE


    /*

                TIER 2
                Medium tiered combinations, further combines initial compounds, possibly with tier 1 compounds.
                Represents life in its first forms, seeds and movement of bacteria etc.
                Introduces spiritual and elemental concepts.

     */
    @ObjectHolder("seed")
    public static ICompoundType SEED; //EARTH + LIFE

    @ObjectHolder("burnable")
    public static ICompoundType BURNABLE; //FIRE + ENERGY

    @ObjectHolder("movement")
    public static ICompoundType MOVEMENT; //TIME + LIFE + ENERGY

    @ObjectHolder("elemental")
    public static ICompoundType ELEMENTAL; //EARTH + TIME + ORDER

    @ObjectHolder("spiritual")
    public static ICompoundType SPIRITUAL; //LIFE + ORDER


    /*

                TIER 3
                Advanced tier combinations, combines all previous tiers together.


     */
    @ObjectHolder("plantlike")
    public static ICompoundType PLANTLIKE; //SEED + TIME

    @ObjectHolder("metalic")
    public static ICompoundType METALIC; //ELEMENTAL + EARTH + TIME

    @ObjectHolder("light")
    public static ICompoundType LIGHT; //BURNABLE + AIR

    @ObjectHolder("knowledge")
    public static ICompoundType KNOWLEDGE; //LIFE + SPIRITUAL + TIME



    @ObjectHolder("exchange")
    public static ICompoundType EXCHANGE; //KNOWLEDGE + ORDER + CHAOS + TIME

    @ObjectHolder("tree")
    public static ICompoundType TREE; //PLANT + MOVEMENT + TIME

    @ObjectHolder("tool")
    public static ICompoundType TOOL; //ELEMENTAL + KNOWLEDGE + MOVEMENT

    @ObjectHolder("automaton")
    public static ICompoundType AUTOMATON; //METALIC + KNOWLEDGE + MOVEMENT


    @ObjectHolder("machine")
    public static ICompoundType MACHINE; //TOOL + AUTOMATON + ORDER + KNOWLEDGE

    @ObjectHolder("defense")
    public static ICompoundType DEFENSE; //TOOL + LIFE + ORDER + KNOWLEDGE

    @ObjectHolder("offense")
    public static ICompoundType OFFENSE; //TOOL + LIFE + CHAOS + KNOWLEDGE

    @ObjectHolder("harvest")
    public static ICompoundType HARVEST; //TOOL + ORDER + PLANT + KNOWLEDGE



    @ObjectHolder("production")
    public static ICompoundType PRODUCTION; //TOOL + AUTOMATON + MACHINE + KNOWLEDGE
}
