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

    @ObjectHolder(ModBlockNames.Path.Block.UNSHAPED_TNT_STICK)
    public static Block UNSHAPED_TNT_STICK;

    @ObjectHolder(ModBlockNames.Path.Block.SHAPED_TNT_STICK)
    public static Block SHAPED_TNT_STICK;

    @ObjectHolder(ModBlockNames.Path.Block.TNT_STACK_STABLE)
    public static Block TNT_STACK_STABLE;
}
