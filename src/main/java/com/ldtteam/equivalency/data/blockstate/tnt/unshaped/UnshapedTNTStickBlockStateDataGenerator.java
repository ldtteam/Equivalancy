package com.ldtteam.equivalency.data.blockstate.tnt.unshaped;

import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModBlocks;
import com.ldtteam.equivalency.data.blockstate.tnt.AbstractTNTStickBlockStateDataGenerator;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UnshapedTNTStickBlockStateDataGenerator extends AbstractTNTStickBlockStateDataGenerator
{
    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        event.getGenerator().addProvider(new UnshapedTNTStickBlockStateDataGenerator(event.getGenerator()));
    }

    private UnshapedTNTStickBlockStateDataGenerator(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected Block getBlock()
    {
        return ModBlocks.UNSHAPED_TNT_STICK;
    }

    @Override
    protected String getModelPath()
    {
        return "block/tnt/unshaped";
    }

    @Override
    public String getName()
    {
        return "Unshaped TNT Stick BlockState DataGenerator";
    }
}
