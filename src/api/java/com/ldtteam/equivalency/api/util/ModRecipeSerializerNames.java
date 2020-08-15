package com.ldtteam.equivalency.api.util;

import net.minecraft.util.ResourceLocation;

public final class ModRecipeSerializerNames
{

    private ModRecipeSerializerNames()
    {
        throw new IllegalStateException("Tried to initialize: ModRecipeSerializerNames but this is a Utility class.");
    }

    public static final class Path {
        public static final String SINGULARITY_TRANSMUTATION = "singularity_transmutation";
    }

    public static final class Name {
        public static final ResourceLocation SINGULARITY_TRANSMUTATION = new ResourceLocation(Constants.MOD_ID, Path.SINGULARITY_TRANSMUTATION);
    }
}
