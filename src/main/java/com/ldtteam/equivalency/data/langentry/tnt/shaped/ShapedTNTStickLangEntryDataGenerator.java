package com.ldtteam.equivalency.data.langentry.tnt.shaped;

import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModBlocks;
import com.ldtteam.equivalency.data.blockstate.tnt.AbstractTNTStickBlockStateDataGenerator;
import com.ldtteam.equivalency.data.langentry.tnt.AbstractTNTStickLangEntryDataGenerator;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShapedTNTStickLangEntryDataGenerator extends AbstractTNTStickLangEntryDataGenerator
{
    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        event.getGenerator().addProvider(new ShapedTNTStickLangEntryDataGenerator(event.getGenerator()));
    }

    private ShapedTNTStickLangEntryDataGenerator(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected Block getBlock()
    {
        return ModBlocks.SHAPED_TNT_STICK;
    }

    @Override
    protected String getModelPath()
    {
        return "block/tnt/shaped";
    }

    @Override
    protected String getDefaultEnUsTranslation()
    {
        return "Shaped TNT Stick";
    }

    @Override
    public String getName()
    {
        return "Shaped TNT Stick LangEntry DataGenerator.";
    }
}
