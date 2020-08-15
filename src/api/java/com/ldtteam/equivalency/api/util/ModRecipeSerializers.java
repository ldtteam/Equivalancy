package com.ldtteam.equivalency.api.util;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Constants.MOD_ID)
public final class ModRecipeSerializers
{

    private ModRecipeSerializers()
    {
        throw new IllegalStateException("Tried to initialize: ModRecipeSerializers but this is a Utility class.");
    }

    @ObjectHolder(ModRecipeSerializerNames.Path.SINGULARITY_TRANSMUTATION)
    public static IRecipeSerializer<?> SINGULARITY_TRANSMUTATION;
}
