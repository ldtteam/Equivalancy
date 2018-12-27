package com.ldtteam.equivalency.analyzer;

import com.ldtteam.equivalency.api.recipe.IEquivalencyRecipe;
import com.ldtteam.equivalency.api.recipe.IEquivalencyRecipeRegistry;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class EquivalencyRecipeRegistry implements IEquivalencyRecipeRegistry
{
    private final Set<IEquivalencyRecipe> recipes = new HashSet<>();

    /**
     * Adds a new recipe to the registry.
     *
     * @param recipe The recipe to add.
     * @return The registry.
     */
    @NotNull
    @Override
    public IEquivalencyRecipeRegistry registerNewRecipe(@NotNull final IEquivalencyRecipe recipe)
    {
        recipes.add(Validate.notNull(recipe));
        return this;
    }

    /**
     * The recipes.
     *
     * @return The recipes.
     */
    @NotNull
    @Override
    public Set<IEquivalencyRecipe> getRecipes()
    {
        return recipes;
    }
}
