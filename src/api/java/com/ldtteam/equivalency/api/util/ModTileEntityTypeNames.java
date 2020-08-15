package com.ldtteam.equivalency.api.util;

import net.minecraft.util.ResourceLocation;

public final class ModTileEntityTypeNames
{

    private ModTileEntityTypeNames()
    {
        throw new IllegalStateException("Tried to initialize: ModTileEntityTypeNames but this is a Utility class.");
    }

    public static final class Path {
        public static final String SINGULARITY_TRANSMUTATION = "singularity_transmutation";
    }

    public static final class Name {
        public static final ResourceLocation SINGULARITY_TRANSMUTATION = new ResourceLocation(Constants.MOD_ID, Path.SINGULARITY_TRANSMUTATION);
    }
}
