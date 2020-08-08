package com.ldtteam.equivalency.data.tag.paper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.ldtteam.datagenerators.tags.TagJson;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModBlockNames;
import com.ldtteam.equivalency.api.util.ModTags;
import com.ldtteam.equivalency.data.tag.tnt.shaped.ShapedTNTStickTagDataGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Items;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PaperTagDataGenerator implements IDataProvider
{
    private final DataGenerator generator;

    private PaperTagDataGenerator(DataGenerator generator) {this.generator = generator;}

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        event.getGenerator().addProvider(new PaperTagDataGenerator(event.getGenerator()));
    }

    @Override
    public void act(final DirectoryCache cache) throws IOException
    {
        createTag(cache);
    }

    @Override
    public String getName()
    {
        return "Paper Tag Generator";
    }

    private void createTag(final DirectoryCache cache) throws IOException
    {
        if (getTags().isEmpty())
            return;

        for (Map.Entry<String, List<String>> entry : getTags().entrySet())
        {
            List<String> value = entry.getValue();
            final TagJson tagJson = new TagJson(false, value);
            final Path tagPath = this.generator.getOutputFolder().resolve(Constants.DataGenerator.FORGE_TAGS_DIR).resolve(entry.getKey() + ".json");

            IDataProvider.save(Constants.DataGenerator.GSON, cache, tagJson.serialize(), tagPath);
        }
    }

    private Map<String, List<String>> getTags() {
        return ImmutableMap.of(
          "items/" + ModTags.Items.PAPER.getName().getPath(),
          Lists.newArrayList(Items.PAPER.getRegistryName().toString())
        );
    }
}
