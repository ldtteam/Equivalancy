package com.ldtteam.equivalency.data.loottable;

import com.ldtteam.datagenerators.loot_table.LootTableJson;
import com.ldtteam.datagenerators.loot_table.LootTableTypeEnum;
import com.ldtteam.datagenerators.loot_table.pool.PoolJson;
import com.ldtteam.datagenerators.loot_table.pool.conditions.survives_explosion.SurvivesExplosionConditionJson;
import com.ldtteam.datagenerators.loot_table.pool.entry.EntryJson;
import com.ldtteam.datagenerators.loot_table.pool.entry.EntryTypeEnum;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModBlockNames;
import com.ldtteam.equivalency.api.util.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SimpleBlockLootTableDataGenerator implements IDataProvider
{
    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        event.getGenerator().addProvider(new SimpleBlockLootTableDataGenerator(event.getGenerator()));
    }

    private final DataGenerator generator;

    protected SimpleBlockLootTableDataGenerator(DataGenerator generator) {this.generator = generator;}

    @Override
    public void act(final DirectoryCache directoryCache) throws IOException
    {
        saveBlock(ModBlocks.INGESTION_TABLE, directoryCache);
    }

    private void saveBlock(final Block block, final DirectoryCache cache) throws IOException
    {
        if (block == null)
            return;

        if (block.getRegistryName() != null)
        {

            final EntryJson entryJson = new EntryJson();
            entryJson.setType(EntryTypeEnum.ITEM);
            entryJson.setName(block.getRegistryName().toString());

            final PoolJson poolJson = new PoolJson();
            poolJson.setEntries(Collections.singletonList(entryJson));
            poolJson.setRolls(1);
            poolJson.setConditions(Collections.singletonList(new SurvivesExplosionConditionJson()));

            final LootTableJson lootTableJson = new LootTableJson();
            lootTableJson.setType(LootTableTypeEnum.BLOCK);
            lootTableJson.setPools(Collections.singletonList(poolJson));

            final Path savePath = generator.getOutputFolder().resolve(Constants.DataGenerator.LOOT_TABLES_DIR).resolve(block.getRegistryName().getPath() + ".json");
            IDataProvider.save(Constants.DataGenerator.GSON, cache, lootTableJson.serialize(), savePath);
        }
    }


    @Override
    public String getName()
    {
        return "Simple block loottable generator";
    }
}
