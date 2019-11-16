package com.ldtteam.equivalency.api.recipe;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * A registry containing recipes which the analysis engine uses to determine how compounds are passed from inputs to outputs.
 */
public interface IEquivalencyRecipeRegistry
{

    /**
     * Adds a new recipe to the registry.
     *
     * @param recipe The recipe to add.
     * @return The registry with the recipe added.
     */
    @NotNull
    IEquivalencyRecipeRegistry register(@NotNull final IEquivalencyRecipe recipe);
}
