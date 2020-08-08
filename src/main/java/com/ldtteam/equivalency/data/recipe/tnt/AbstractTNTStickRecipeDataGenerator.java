package com.ldtteam.equivalency.data.recipe.tnt;

import com.google.gson.JsonParser;
import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.datagenerators.recipes.RecipeIngredientJson;
import com.ldtteam.datagenerators.recipes.RecipeIngredientKeyJson;
import com.ldtteam.datagenerators.recipes.RecipeResultJson;
import com.ldtteam.datagenerators.recipes.shaped.ShapedPatternJson;
import com.ldtteam.datagenerators.recipes.shaped.ShapedRecipeJson;
import com.ldtteam.equivalency.api.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTNTStickRecipeDataGenerator implements IDataProvider
{
    private final DataGenerator generator;

    protected AbstractTNTStickRecipeDataGenerator(DataGenerator generator) {this.generator = generator;}


    @Override
    public void act(final DirectoryCache cache) throws IOException
    {
        createRecipe(cache);
    }

    private void createRecipe(final DirectoryCache cache) throws IOException
    {
        if (getBlock() == null)
            return;

        if (getBlock().getRegistryName() == null)
            return;

        final ShapedRecipeJson recipeJson = new ShapedRecipeJson();
        recipeJson.setResult(new RecipeResultJson(1, getBlock().getRegistryName().toString()));
        recipeJson.setPattern(getPattern());
        recipeJson.setKey(getIngredients());

        final Path recipePath = this.generator.getOutputFolder().resolve(Constants.DataGenerator.RECIPES_DIR).resolve(getBlock().getRegistryName().getPath() + ".json");

        IDataProvider.save(Constants.DataGenerator.GSON, cache, recipeJson.serialize(), recipePath);
    }

    protected abstract Block getBlock();

    protected abstract String getModelPath();

    protected abstract ShapedPatternJson getPattern();

    protected abstract Map<String, RecipeIngredientKeyJson> getIngredients();
}
