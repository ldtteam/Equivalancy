package com.ldtteam.equivalency.api.util;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public final class ModItemGroups
{

    private ModItemGroups()
    {
        throw new IllegalStateException("Tried to initialize: ModItemGroups but this is a Utility class.");
    }

    public static final ItemGroup TNT  = new ItemGroup(Constants.MOD_ID + "_tnt") {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModBlocks.UNSHAPED_TNT_STICK);
        }
    };
}
