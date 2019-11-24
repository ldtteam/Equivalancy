package com.ldtteam.equivalency.api.util;

import net.minecraft.block.Block;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Constants.MOD_ID)
public final class ModBlocks
{

    private ModBlocks()
    {
        throw new IllegalStateException("Tried to initialize: ModBlocks but this is a Utility class.");
    }

    @ObjectHolder("tnt_stick_unshaped")
    public static Block UNSHAPED_TNT_STICK;

    @ObjectHolder("tnt_stick_shaped")
    public static Block SHAPED_TNT_STICK;

    @ObjectHolder("tnt_stack_unstable")
    public static Block TNT_STACK_UNSTABLE;
}
