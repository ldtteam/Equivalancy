package com.ldtteam.equivalency.api.util;

import net.minecraft.util.ResourceLocation;

public final class ModBiomeFeatureNames
{

    private ModBiomeFeatureNames()
    {
        throw new IllegalStateException("Tried to initialize: ModBiomeFeatureNames but this is a Utility class.");
    }

    public static final class Path {
        public static final String BEDROCK_SINGULARITY = "bedrock_singularity";
    }

    public static final class Name {
        public static final ResourceLocation BEDROCK_SINGULARITY = new ResourceLocation(Constants.MOD_ID, Path.BEDROCK_SINGULARITY);
    }
}
