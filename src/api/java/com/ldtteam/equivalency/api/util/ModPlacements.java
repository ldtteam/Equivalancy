package com.ldtteam.equivalency.api.util;

import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Constants.MOD_ID)
public final class ModPlacements
{

    private ModPlacements()
    {
        throw new IllegalStateException("Tried to initialize: ModPlacements but this is a Utility class.");
    }

    @ObjectHolder(ModBiomeFeatureNames.Path.BEDROCK_SINGULARITY)
    public static Placement<NoPlacementConfig> BEDROCK_SINGULARITY;
}
