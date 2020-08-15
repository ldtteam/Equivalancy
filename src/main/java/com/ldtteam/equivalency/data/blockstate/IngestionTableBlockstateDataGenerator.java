package com.ldtteam.equivalency.data.blockstate;

import com.google.common.collect.Maps;
import com.ldtteam.datagenerators.blockstate.BlockstateJson;
import com.ldtteam.datagenerators.blockstate.BlockstateModelJson;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.DataGeneratorUtils;
import com.ldtteam.equivalency.api.util.ModBlocks;
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
public class IngestionTableBlockstateDataGenerator implements IDataProvider
{
    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        event.getGenerator().addProvider(new IngestionTableBlockstateDataGenerator(event.getGenerator()));
    }

    private final DataGenerator generator;

    private IngestionTableBlockstateDataGenerator(final DataGenerator generator) {this.generator = generator;}

    @Override
    public void act(final DirectoryCache cache) throws IOException
    {
        if (ModBlocks.INGESTION_TABLE == null)
            return;

        if (ModBlocks.INGESTION_TABLE.getRegistryName() == null)
            return;

        final Map<String, BlockstateVariantJson> variants = Maps.newHashMap();

        String modelFile = new ResourceLocation(Constants.MOD_ID, "block/ingestion_table").toString();
        BlockStateProperties.HORIZONTAL_FACING.getAllowedValues().forEach(direction -> {
            final String variantKey = "facing=" + direction;

            int rotateX = DataGeneratorUtils.getXRotationFromFacing(direction);
            int rotateY = DataGeneratorUtils.getYRotationFromFacing(direction);

            final BlockstateModelJson model = new BlockstateModelJson(modelFile, rotateX, rotateY);
            variants.put(variantKey, new BlockstateVariantJson(model));
        });

        final BlockstateJson blockstateJson = new BlockstateJson(variants);
        final Path blockstateFolder = this.generator.getOutputFolder().resolve(Constants.DataGenerator.BLOCKSTATE_DIR);
        final Path blockstatePath = blockstateFolder.resolve(ModBlocks.INGESTION_TABLE.getRegistryName().getPath() + ".json");

        IDataProvider.save(Constants.DataGenerator.GSON, cache, blockstateJson.serialize(), blockstatePath);
    }

    @Override
    public String getName()
    {
        return "Ingestion table blockstate data generator";
    }
}
