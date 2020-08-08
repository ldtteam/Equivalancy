package com.ldtteam.equivalency.data.recipe.tnt.shaped;

import com.google.common.collect.ImmutableMap;
import com.ldtteam.datagenerators.recipes.RecipeIngredientJson;
import com.ldtteam.datagenerators.recipes.RecipeIngredientKeyJson;
import com.ldtteam.datagenerators.recipes.shaped.ShapedPatternJson;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModBlocks;
import com.ldtteam.equivalency.api.util.ModTags;
import com.ldtteam.equivalency.data.recipe.tnt.AbstractTNTStickRecipeDataGenerator;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShapedTNTStickRecipeDataGenerator extends AbstractTNTStickRecipeDataGenerator
{
    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        event.getGenerator().addProvider(new ShapedTNTStickRecipeDataGenerator(event.getGenerator()));
    }

    private ShapedTNTStickRecipeDataGenerator(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected Block getBlock()
    {
        return ModBlocks.SHAPED_TNT_STICK;
    }

    @Override
    protected String getModelPath()
    {
        return "block/tnt/shaped";
    }

    @Override
    protected ShapedPatternJson getPattern()
    {
        return new ShapedPatternJson(" N ", "NUN", " N ");
    }

    @Override
    protected Map<String, RecipeIngredientKeyJson> getIngredients()
    {
        return new ImmutableMap.Builder<String, RecipeIngredientKeyJson>()
          .put("N", new RecipeIngredientKeyJson(new RecipeIngredientJson(Tags.Items.NUGGETS_IRON.getName().toString() , true)))
          .put("U", new RecipeIngredientKeyJson(new RecipeIngredientJson(ModTags.Items.UNSHAPED_TNT_STICKS.getName().toString(), true)))
          .build();
    }

    @Override
    public String getName()
    {
        return "Shaped TNT Stick Recipe DataGenerator.";
    }
}
