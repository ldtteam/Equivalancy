package com.ldtteam.equivalency.api.util;

import com.ldtteam.equivalency.api.tileentity.AbstractBedrockSingularityTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Constants.MOD_ID)
public final class ModTileEntityTypes
{

    private ModTileEntityTypes()
    {
        throw new IllegalStateException("Tried to initialize: ModTileEntityTypes but this is a Utility class.");
    }

    @ObjectHolder(ModTileEntityTypeNames.Path.SINGULARITY_TRANSMUTATION)
    public static TileEntityType<AbstractBedrockSingularityTileEntity> SINGULARITY_TRANSLATION;
}
