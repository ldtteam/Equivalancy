package com.ldtteam.equivalency.data.tag.tnt;

import com.ldtteam.datagenerators.tags.TagJson;
import com.ldtteam.equivalency.api.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.tags.Tag;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public abstract class AbstractTNTStickTagDataGenerator implements IDataProvider
{
    private final DataGenerator generator;

    protected AbstractTNTStickTagDataGenerator(DataGenerator generator) {this.generator = generator;}


    @Override
    public void act(final DirectoryCache cache) throws IOException
    {
        createTag(cache);
    }

    private void createTag(final DirectoryCache cache) throws IOException
    {
        if (getTags().isEmpty())
            return;

        for (Map.Entry<String, List<String>> entry : getTags().entrySet())
        {
            List<String> value = entry.getValue();
            final TagJson tagJson = new TagJson(false, value);
            final Path tagPath = this.generator.getOutputFolder().resolve(Constants.DataGenerator.TAGS_DIR).resolve(entry.getKey() + ".json");

            IDataProvider.save(Constants.DataGenerator.GSON, cache, tagJson.serialize(), tagPath);
        }
    }

    protected abstract Map<String, List<String>> getTags();
}
