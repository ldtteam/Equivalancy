package com.ldtteam.equivalency.data.itemmodel.tnt;

import com.ldtteam.datagenerators.models.item.ItemModelJson;
import com.ldtteam.equivalency.api.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;

public abstract class AbstractTNTStickItemModelGenerator implements IDataProvider
{

    private final DataGenerator generator;

    protected AbstractTNTStickItemModelGenerator(final DataGenerator generator) {this.generator = generator;}

    @Override
    public void act(final DirectoryCache cache) throws IOException
    {
        createItemModel(cache);
    }

    private void createItemModel(final DirectoryCache cache) throws IOException
    {
        if (getBlock() == null)
            return;

        if (getBlock().getRegistryName() == null)
        {
            return;
        }

        final ItemModelJson modelJson = new ItemModelJson();

        final String parent = new ResourceLocation(Constants.MOD_ID, getModelPath()).toString();
        modelJson.setParent(parent);

        final String name = getBlock().getRegistryName().getPath();
        IDataProvider.save(Constants.DataGenerator.GSON,
          cache,
          modelJson.serialize(),
          generator.getOutputFolder().resolve(Constants.DataGenerator.ITEM_MODEL_DIR).resolve(name + ".json"));
    }

    protected abstract Block getBlock();

    protected abstract String getModelPath();
}
