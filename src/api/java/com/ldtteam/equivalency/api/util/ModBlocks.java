package com.ldtteam.equivalency.api.util;

import net.minecraft.block.Block;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Constants.MOD_ID)
public final class ModBlocks
{

    private ModBlocks()
    {
        throw new IllegalStateException("Tried to initialize: ModBlocks but this is a Utility class.");
    }

    @ObjectHolder(ModBlockNames.Path.BEDROCK_SINGULARITY)
    public static Block BEDROCK_SINGULARITY;

    @ObjectHolder(ModBlockNames.Path.INGESTION_TABLE)
    public static Block INGESTION_TABLE;
}
