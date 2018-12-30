package com.ldtteam.equivalency.api.util;

import com.ldtteam.equivalency.api.compound.ICompoundType;

public final class ModCompoundTypes
{

    private ModCompoundTypes()
    {
        throw new IllegalStateException("Tried to initialize: ModCompoundTypes but this is a Utility class.");
    }

    public static ICompoundType AIR;
    public static ICompoundType STONE;
    public static ICompoundType EARTH;
    public static ICompoundType FIRE;
    public static ICompoundType DARK;
    public static ICompoundType LIGHT;


    public static ICompoundType TIME; //LIGHT + DARK
    public static ICompoundType LIFE; //WATER + EARTH + AIR
    public static ICompoundType BURNABLE; //FIRE + AIR

    public static ICompoundType SEED; //EARTH + LIFE

    public static ICompoundType PLANT; //SEED + TIME

    public static ICompoundType TREE; //PLANT + TIME





    public static ICompoundType RED_STONE;

}
