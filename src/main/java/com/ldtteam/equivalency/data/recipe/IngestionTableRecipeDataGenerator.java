package com.ldtteam.equivalency.data.recipe;

import com.ldtteam.datagenerators.recipes.RecipeIngredientJson;
import com.ldtteam.datagenerators.recipes.RecipeIngredientKeyJson;
import com.ldtteam.datagenerators.recipes.RecipeResultJson;
import com.ldtteam.datagenerators.recipes.shaped.ShapedPatternJson;
import com.ldtteam.datagenerators.recipes.shaped.ShapedRecipeJson;
import com.ldtteam.equivalency.api.data.SingularityTransmutationRecipeJson;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModBlocks;
import com.ldtteam.equivalency.api.util.ModRecipeTypeNames;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IngestionTableRecipeDataGenerator implements IDataProvider
{

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        event.getGenerator().addProvider(new IngestionTableRecipeDataGenerator(event.getGenerator()));
    }
    
    private final DataGenerator generator;

    public IngestionTableRecipeDataGenerator(final DataGenerator generator) {this.generator = generator;}

    @Override
    public void act(final DirectoryCache cache) throws IOException
    {
        if (ModBlocks.INGESTION_TABLE == null)
            return;

        if (ModBlocks.INGESTION_TABLE.getRegistryName() == null)
            return;

        final ShapedRecipeJson recipeJson = new ShapedRecipeJson();
        recipeJson.setResult(new RecipeResultJson(1, ModBlocks.INGESTION_TABLE.getRegistryName().toString()));
        recipeJson.setPattern(new ShapedPatternJson("LLL", " L ", "SLS"));

        final Map<String, RecipeIngredientKeyJson> ingredients = new HashMap<>();
        ingredients.put("L", new RecipeIngredientKeyJson(new RecipeIngredientJson("logs", true)));
        ingredients.put("S", new RecipeIngredientKeyJson(new RecipeIngredientJson("wooden_slabs", true)));
        recipeJson.setKey(ingredients);

        final Path
          recipePath = this.generator.getOutputFolder().resolve(Constants.DataGenerator.RECIPES_DIR).resolve(ModBlocks.INGESTION_TABLE.getRegistryName().getPath() + ".json");

        IDataProvider.save(Constants.DataGenerator.GSON, cache, recipeJson.serialize(), recipePath);
    }

    @Override
    public String getName()
    {
        return "Ingestion table recipe data generator";
    }
}
