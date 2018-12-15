package com.ldtteam.equivalency.api.recipe;

import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;

import java.util.Set;

/**
 * Represents a "recipe".
 * With this not just a crafting recipe is meant.
 *
 * But also for example the conversion of furnace fuel into heat.
 *
 * EG: 2 Wooden planks for 4 Sticks as output, as well a 1 coal for 16 heat.
 */
public interface IEquivalencyRecipe extends Comparable<IEquivalencyRecipe>
{

    /**
     * The compound containers that are the input for this recipe.
     *
     * @return The inputs.
     */
    Set<ICompoundContainerWrapper<?>> getInputs();

    /**
     * The compound containers that are the output for this recipe.
     *
     * @return The output.
     */
    Set<ICompoundContainerWrapper<?>> getOutputs();
}
