package com.ldtteam.equivalency.api.util;

import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Constants.MOD_ID)
public final class ModBiomeFeatures
{

    private ModBiomeFeatures()
    {
        throw new IllegalStateException("Tried to initialize: ModBiomeFeatures but this is a Utility class.");
    }

    @ObjectHolder(ModBiomeFeatureNames.Path.BEDROCK_SINGULARITY)
    public static Feature<ReplaceBlockConfig> BEDROCK_SINGULARITY;
}
