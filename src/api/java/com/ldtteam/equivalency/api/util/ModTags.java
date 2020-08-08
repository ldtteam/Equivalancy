package com.ldtteam.equivalency.api.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public final class ModTags
{

    private ModTags()
    {
        throw new IllegalStateException("Tried to initialize: ModTags but this is a Utility class.");
    }

    public static class Items {
        public static final ITag.INamedTag<Item> UNSHAPED_TNT_STICKS = ItemTags.makeWrapperTag(new ResourceLocation(Constants.MOD_ID, "unshaped_tnt_stick").toString());
        public static final ITag.INamedTag<Item> SHAPED_TNT_STICKS   = ItemTags.makeWrapperTag(new ResourceLocation(Constants.MOD_ID, "shaped_tnt_stick").toString());
        public static final ITag.INamedTag<Item> PAPER = ItemTags.makeWrapperTag(new ResourceLocation("forge", "paper").toString());
    }

    public static class Blocks {
        public static final ITag.INamedTag<Block> UNSHAPED_TNT_STICKS = BlockTags.makeWrapperTag(new ResourceLocation(Constants.MOD_ID, "unshaped_tnt_stick").toString());
        public static final ITag.INamedTag<Block> SHAPED_TNT_STICKS = BlockTags.makeWrapperTag(new ResourceLocation(Constants.MOD_ID, "shaped_tnt_stick").toString());
    }
}
