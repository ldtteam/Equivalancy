package com.ldtteam.equivalency.api.util;

import com.ldtteam.equivalency.api.recipe.singularity.transmutation.ISingularityTransmutationRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Constants.MOD_ID)
public class ModRecipeTypes
{

    public static IRecipeType<? extends ISingularityTransmutationRecipe> SINGULARITY_TRANSMUTATION;
}
