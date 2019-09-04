package com.ldtteam.equivalency.api.util;

import com.ldtteam.equivalency.api.compound.ICompoundType;

public final class ModCompoundTypes
{

    private ModCompoundTypes()
    {
        throw new IllegalStateException("Tried to initialize: ModCompoundTypes but this is a Utility class.");
    }

    public static ICompoundType AIR;
    public static ICompoundType EARTH;
    public static ICompoundType WATER;
    public static ICompoundType FIRE;
    public static ICompoundType CHAOS;
    public static ICompoundType ORDER;

    public static ICompoundType TIME; //CHAOS + ORDER
    public static ICompoundType LIFE; //WATER + EARTH + AIR + CHAOS
    public static ICompoundType ENERGY; //ORDER + FIRE

    public static ICompoundType SEED; //EARTH + LIFE
    public static ICompoundType BURNABLE; //FIRE + ENERGY
    public static ICompoundType MOVEMENT; //TIME + LIFE + ENERGY
    public static ICompoundType ELEMTAL; //EARTH + TIME + ORDER
    public static ICompoundType SPIRITUAL; //LIFE + ORDER

    public static ICompoundType PLANTLIKE; //SEED + TIME
    public static ICompoundType METALIC; //ELEMENTAL + EARTH + TIME
    public static ICompoundType LIGHT; //BURNABLE + AIR
    public static ICompoundType KNOWLEDGE; //LIFE + SPIRITUAL + TIME

    public static ICompoundType EXCHANGE; //KNOWLEDGE + ORDER + CHAOS + TIME
    public static ICompoundType TREE; //PLANT + MOVEMENT + TIME
    public static ICompoundType TOOL; //ELEMENTAL + KNOWLEDGE + MOVEMENT
    public static ICompoundType AUTOMATON; //METALIC + KNOWLEDGE + MOVEMENT

    public static ICompoundType MACHINE; //TOOL + AUTOMATON + KNOWLEDGE
    public static ICompoundType DEFENSE; //TOOL + LIFE + KNOWLEDGE
    public static ICompoundType HARVEST; //TOOL + PLANT + KNOWLEDGE
    public static ICompoundType PRODUCTION; //TOOL + AUTOMATON + KNOWLEDGE

}
