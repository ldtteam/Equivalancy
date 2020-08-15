package com.ldtteam.equivalency.data.recipe.singularity.transmutation;

import com.ldtteam.datagenerators.recipes.RecipeIngredientKeyJson;
import com.ldtteam.datagenerators.recipes.RecipeResultJson;
import com.ldtteam.datagenerators.recipes.shaped.ShapedPatternJson;
import com.ldtteam.datagenerators.recipes.shaped.ShapedRecipeJson;
import com.ldtteam.datagenerators.recipes.shapeless.ShapelessRecipeJson;
import com.ldtteam.equivalency.api.data.SingularityTransmutationRecipeJson;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModRecipeTypeNames;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Item;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public abstract class AbstractSingularityTransmutationRecipeDataGenerator implements IDataProvider
{
    private final DataGenerator generator;

    protected AbstractSingularityTransmutationRecipeDataGenerator(DataGenerator generator) {this.generator = generator;}


    @Override
    public void act(final DirectoryCache cache) throws IOException
    {
        createRecipe(cache);
    }

    private void createRecipe(final DirectoryCache cache) throws IOException
    {
        if (getItem() == null)
            return;

        if (getItem().getRegistryName() == null)
            return;

        final SingularityTransmutationRecipeJson recipeJson = new SingularityTransmutationRecipeJson();
        recipeJson.setResult(new RecipeResultJson(1, getItem().getRegistryName().toString()));
        recipeJson.setRecipeType(ModRecipeTypeNames.Name.SINGULARITY_TRANSMUTATION.toString());
        recipeJson.setIngredients(getIngredients());
        recipeJson.setMassIncrease(getMassIncrease());

        final Path recipePath = this.generator.getOutputFolder().resolve(Constants.DataGenerator.RECIPES_DIR).resolve(getItem().getRegistryName().getPath() + "_from_bedrock_singularity" + ".json");

        IDataProvider.save(Constants.DataGenerator.GSON, cache, recipeJson.serialize(), recipePath);
    }

    protected abstract Item getItem();

    protected abstract List<RecipeIngredientKeyJson> getIngredients();

    protected abstract float getMassIncrease();
}
