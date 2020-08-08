package com.ldtteam.equivalency.data.langentry.tnt;

import com.google.gson.JsonParser;
import com.ldtteam.datagenerators.lang.LangJson;
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

public abstract class AbstractTNTStickLangEntryDataGenerator implements IDataProvider
{
    private final DataGenerator generator;

    protected AbstractTNTStickLangEntryDataGenerator(DataGenerator generator) {this.generator = generator;}


    @Override
    public void act(final DirectoryCache cache) throws IOException
    {
        createLangEntry(cache);
    }

    private void createLangEntry(final DirectoryCache cache) throws IOException
    {
        if (getBlock() == null)
            return;

        final Path inputPath = generator.getInputFolders().stream().findFirst().orElse(null);

        if (inputPath == null)
            return;

        if (getBlock().getRegistryName() == null)
            return;

        final File langFile = inputPath.resolve(Constants.DataGenerator.EN_US_LANG).toFile();
        if (!langFile.exists())
        {
            langFile.getParentFile().mkdirs();
            langFile.createNewFile();
        }

        final Reader reader = new FileReader(langFile);

        final LangJson langJson = new LangJson();
        langJson.deserialize(new JsonParser().parse(reader));

        final String reference = "block.equivalency." + getBlock().getRegistryName().getPath();
        final String value = getDefaultEnUsTranslation();

        langJson.getLang().put(reference, value);

        IDataProvider.save(Constants.DataGenerator.GSON, cache, langJson.serialize(), langFile.toPath());
    }

    protected abstract Block getBlock();

    protected abstract String getModelPath();

    protected abstract String getDefaultEnUsTranslation();
}
