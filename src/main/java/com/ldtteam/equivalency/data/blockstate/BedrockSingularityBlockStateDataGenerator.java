package com.ldtteam.equivalency.data.blockstate;

import com.google.common.collect.Maps;
import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.DataGeneratorUtils;
import com.ldtteam.equivalency.api.util.ModBlocks;
import com.ldtteam.equivalency.data.loottable.SimpleBlockLootTableDataGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BedrockSingularityBlockStateDataGenerator implements IDataProvider
{
    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        event.getGenerator().addProvider(new BedrockSingularityBlockStateDataGenerator(event.getGenerator()));
    }

    private final DataGenerator generator;

    private BedrockSingularityBlockStateDataGenerator(final DataGenerator generator) {this.generator = generator;}

    @Override
    public void act(final DirectoryCache cache) throws IOException
    {
        if (ModBlocks.BEDROCK_SINGULARITY == null)
            return;

        if (ModBlocks.BEDROCK_SINGULARITY.getRegistryName() == null)
            return;

        final Map<String, BlockstateVariantJson> variants = Maps.newHashMap();
        final String variantKey = "";

        String modelFile = new ResourceLocation(Constants.MOD_ID, "block/bedrock_singularity").toString();
        final BlockstateModelJson model = new BlockstateModelJson(modelFile, 0, 0);
        variants.put(variantKey, new BlockstateVariantJson(model));

        final BlockstateJson blockstateJson = new BlockstateJson(variants);
        final Path blockstateFolder = this.generator.getOutputFolder().resolve(Constants.DataGenerator.BLOCKSTATE_DIR);
        final Path blockstatePath = blockstateFolder.resolve(ModBlocks.BEDROCK_SINGULARITY.getRegistryName().getPath() + ".json");

        IDataProvider.save(Constants.DataGenerator.GSON, cache, blockstateJson.serialize(), blockstatePath);
    }

    @Override
    public String getName()
    {
        return "Bedrock singularity blockstate generator";
    }
}
