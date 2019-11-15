package com.ldtteam.equivalency.analyzer;

import com.google.common.collect.Maps;
import com.ldtteam.equivalency.api.recipe.IEquivalencyRecipe;
import com.ldtteam.equivalency.api.recipe.IEquivalencyRecipeRegistry;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EquivalencyRecipeRegistry implements IEquivalencyRecipeRegistry
{
    private final Map<World, Set<IEquivalencyRecipe>> recipes = Maps.newConcurrentMap();

    @Override
    public void resetForWorld(@NotNull final World world)
    {
        recipes.remove(world);
    }

    /**
     * Adds a new recipe to the registry.
     *
     * @param recipe The recipe to add.
     * @return The registry.
     */
    @NotNull
    @Override
    public IEquivalencyRecipeRegistry registerNewRecipe(@NotNull final World world, @NotNull final IEquivalencyRecipe recipe)
    {
        recipes.putIfAbsent(world, new LinkedHashSet<>());
        recipes.get(world).add(Validate.notNull(recipe));
        return this;
    }

    /**
     * The recipes.
     *
     * @return The recipes.
     */
    @NotNull
    @Override
    public Set<IEquivalencyRecipe> getRecipes(@NotNull final World world)
    {
        return recipes.get(world);
    }
}
