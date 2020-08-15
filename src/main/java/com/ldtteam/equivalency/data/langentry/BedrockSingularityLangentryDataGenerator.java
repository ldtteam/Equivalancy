package com.ldtteam.equivalency.data.langentry;

import com.google.gson.JsonParser;
import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BedrockSingularityLangentryDataGenerator implements IDataProvider
{
    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        event.getGenerator().addProvider(new BedrockSingularityLangentryDataGenerator(event.getGenerator()));
    }

    private final DataGenerator generator;

    private BedrockSingularityLangentryDataGenerator(final DataGenerator generator) {this.generator = generator;}

    @Override
    public void act(final DirectoryCache cache) throws IOException
    {
        if (ModBlocks.BEDROCK_SINGULARITY == null)
            return;

        final Path inputPath = generator.getInputFolders().stream().findFirst().orElse(null);

        if (inputPath == null)
            return;

        if (ModBlocks.BEDROCK_SINGULARITY.getRegistryName() == null)
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

        final String reference = "block.equivalency." + ModBlocks.BEDROCK_SINGULARITY.getRegistryName().getPath();
        final String value = "Bedrock singularity";

        langJson.getLang().put(reference, value);

        IDataProvider.save(Constants.DataGenerator.GSON, cache, langJson.serialize(), langFile.toPath());
    }

    @Override
    public String getName()
    {
        return "Bedrock singularity lang entry data generator.";
    }
}