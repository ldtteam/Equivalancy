package com.ldtteam.equivalency.data.tag.tnt.shaped;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.ldtteam.datagenerators.recipes.RecipeIngredientJson;
import com.ldtteam.datagenerators.recipes.RecipeIngredientKeyJson;
import com.ldtteam.datagenerators.recipes.shaped.ShapedPatternJson;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModBlockNames;
import com.ldtteam.equivalency.api.util.ModBlocks;
import com.ldtteam.equivalency.api.util.ModTags;
import com.ldtteam.equivalency.data.tag.tnt.AbstractTNTStickTagDataGenerator;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShapedTNTStickTagDataGenerator extends AbstractTNTStickTagDataGenerator
{
    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        event.getGenerator().addProvider(new ShapedTNTStickTagDataGenerator(event.getGenerator()));
    }

    private ShapedTNTStickTagDataGenerator(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected Map<String, List<String>> getTags()
    {
        return ImmutableMap.of(
          "items/" + ModTags.Items.SHAPED_TNT_STICKS.getName().getPath(),
          Lists.newArrayList(ModBlockNames.Name.Block.SHAPED_TNT_STICK.toString()),
          "blocks/" + ModTags.Blocks.SHAPED_TNT_STICKS.getName().getPath(),
          Lists.newArrayList(ModBlockNames.Name.Block.SHAPED_TNT_STICK.toString())
        );
    }

    @Override
    public String getName()
    {
        return "Shaped TNT Stick Tag DataGenerator.";
    }
}
