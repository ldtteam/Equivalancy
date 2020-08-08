package com.ldtteam.equivalency.data.tag.tnt.unshaped;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModBlockNames;
import com.ldtteam.equivalency.api.util.ModBlocks;
import com.ldtteam.equivalency.api.util.ModTags;
import com.ldtteam.equivalency.data.tag.tnt.AbstractTNTStickTagDataGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UnshapedTNTStickRecipeDataGenerator extends AbstractTNTStickTagDataGenerator
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
    protected Map<String, List<String>> getTags()
    {
        return ImmutableMap.of(
          "items/" + ModTags.Items.UNSHAPED_TNT_STICKS.getName().getPath(),
          Lists.newArrayList(ModBlockNames.Name.Block.UNSHAPED_TNT_STICK.toString()),
          "blocks/" + ModTags.Blocks.UNSHAPED_TNT_STICKS.getName().getPath(),
          Lists.newArrayList(ModBlockNames.Name.Block.UNSHAPED_TNT_STICK.toString())
        );
    }

    @Override
    public String getName()
    {
        return "Unshaped TNT Stick Recipe DataGenerator";
    }
}
