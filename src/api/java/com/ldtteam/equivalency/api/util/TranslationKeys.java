package com.ldtteam.equivalency.api.util;

import com.ldtteam.equivalency.api.compound.ICompoundType;

public final class TranslationKeys
{

    private TranslationKeys()
    {
        throw new IllegalStateException("Tried to initialize: TranslationKeys but this is a Utility class.");
    }

    public static final String COMPOUND_AIR      = "compound.type.air";
    public static final String COMPOUND_WATER    = "compound.type.water";
    public static final String COMPOUND_EARTH    = "compound.type.earth";
    public static final String COMPOUND_FIRE    = "compound.type.fire";
    public static final String COMPOUND_CHAOS    = "compound.type.chaos";
    public static final String COMPOUND_ORDER    = "compound.type.order";

    public static final String COMPOUND_TREE     = "compound.type.tree";
    public static final String COMPOUND_BURNABLE = "compound.type.burnable";
    public static final String COMPOUND_METALIC = "compound.type.metalic";
}
