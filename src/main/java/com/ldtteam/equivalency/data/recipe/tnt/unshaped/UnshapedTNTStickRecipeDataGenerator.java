package com.ldtteam.equivalency.data.recipe.tnt.unshaped;

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
public class UnshapedTNTStickRecipeDataGenerator extends AbstractTNTStickRecipeDataGenerator
{
    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        event.getGenerator().addProvider(new UnshapedTNTStickRecipeDataGenerator(event.getGenerator()));
    }

    private UnshapedTNTStickRecipeDataGenerator(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected Block getBlock()
    {
        return ModBlocks.UNSHAPED_TNT_STICK;
    }

    @Override
    protected String getModelPath()
    {
        return "block/tnt/unshaped";
    }

    @Override
    protected ShapedPatternJson getPattern()
    {
        return new ShapedPatternJson("SP ", "PGP", " PG");
    }

    @Override
    protected Map<String, RecipeIngredientKeyJson> getIngredients()
    {
        return new ImmutableMap.Builder<String, RecipeIngredientKeyJson>()
                 .put("S", new RecipeIngredientKeyJson(new RecipeIngredientJson(Tags.Items.STRING.getName().toString() , true)))
                 .put("P", new RecipeIngredientKeyJson(new RecipeIngredientJson(ModTags.Items.PAPER.getName().toString(), true)))
                 .put("G", new RecipeIngredientKeyJson(new RecipeIngredientJson(Tags.Items.GUNPOWDER.getName().toString(), true)))
                 .build();
    }

    @Override
    public String getName()
    {
        return "Unshaped TNT Stick Recipe DataGenerator";
    }
}
