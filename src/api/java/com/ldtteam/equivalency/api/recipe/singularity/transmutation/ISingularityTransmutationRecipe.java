package com.ldtteam.equivalency.api.recipe.singularity.transmutation;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;

public interface ISingularityTransmutationRecipe extends IRecipe<IInventory>
{

    float getMassIncrease();
}
