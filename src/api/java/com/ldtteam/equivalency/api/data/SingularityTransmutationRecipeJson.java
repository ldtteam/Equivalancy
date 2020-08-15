package com.ldtteam.equivalency.api.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.recipes.RecipeIngredientKeyJson;
import com.ldtteam.datagenerators.recipes.RecipeResultJson;
import com.ldtteam.datagenerators.recipes.shapeless.ShapelessRecipeJson;
import net.minecraft.item.crafting.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SingularityTransmutationRecipeJson extends ShapelessRecipeJson
{

    public float massIncrease = 1f;

    public SingularityTransmutationRecipeJson()
    {
    }

    public SingularityTransmutationRecipeJson(
      @Nullable final String group,
      @NotNull final List<RecipeIngredientKeyJson> ingredients,
      @NotNull final RecipeResultJson result)
    {
        super(group, ingredients, result);
    }

    public SingularityTransmutationRecipeJson(
      @Nullable final String group,
      @NotNull final List<RecipeIngredientKeyJson> ingredients,
      @NotNull final RecipeResultJson result, @NotNull final String recipeType)
    {
        super(group, ingredients, result, recipeType);
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonElement superElement = super.serialize();
        final JsonObject superObject = (JsonObject) superElement;

        superObject.addProperty("massIncrease", massIncrease);

        return superObject;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        super.deserialize(jsonElement);

        final JsonObject jsonObject = (JsonObject) jsonElement;

        if (jsonObject.has("massIncrease"))
            this.massIncrease = jsonObject.get("massIncrease").getAsFloat();
    }

    public float getMassIncrease()
    {
        return massIncrease;
    }

    public void setMassIncrease(final float massIncrease)
    {
        this.massIncrease = massIncrease;
    }
}
