package com.ldtteam.equivalency.api.recipe;

import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface IEquivalencyRecipeRegistry
{

    void resetForWorld(@NotNull final World world);

    /**
     * Adds a new recipe to the registry.
     *
     * @param recipe The recipe to add.
     *
     * @return The registry.
     */
    @NotNull
    IEquivalencyRecipeRegistry registerNewRecipe(@NotNull final World world, @NotNull final IEquivalencyRecipe recipe);

    /**
     * The recipes.
     * @return The recipes.
     */
    @NotNull
    Set<IEquivalencyRecipe> getRecipes(@NotNull final World world);
}
