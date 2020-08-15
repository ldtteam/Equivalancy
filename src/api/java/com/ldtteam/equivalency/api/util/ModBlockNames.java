package com.ldtteam.equivalency.api.util;

import net.minecraft.util.ResourceLocation;

public final class ModBlockNames
{

    private ModBlockNames()
    {
        throw new IllegalStateException("Tried to initialize: ModBlockNames but this is a Utility class.");
    }

    public static final class Path {
        public static final String BEDROCK_SINGULARITY = "bedrock_singularity";
        public static final String INGESTION_TABLE = "ingestion_table";
    }

    public static final class Name {
        public static final ResourceLocation BEDROCK_SINGULARITY = new ResourceLocation(Constants.MOD_ID, Path.BEDROCK_SINGULARITY);
        public static final ResourceLocation INGESTION_TABLE = new ResourceLocation(Constants.MOD_ID, Path.INGESTION_TABLE);
    }
}
