package com.ldtteam.equivalency.api.util;

import net.minecraft.tags.*;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class TagUtils
{

    private TagUtils()
    {
        throw new IllegalStateException("Tried to initialize: TagUtils but this is a Utility class.");
    }

    public static Tag<?> get(@NotNull final ResourceLocation name)
    {
        if (BlockTags.getCollection().getTagMap().containsKey(name))
            return BlockTags.getCollection().getTagMap().get(name);
        if (ItemTags.getCollection().getTagMap().containsKey(name))
            return ItemTags.getCollection().getTagMap().get(name);
        if (EntityTypeTags.getCollection().getTagMap().containsKey(name))
            return EntityTypeTags.getCollection().getTagMap().get(name);
        if (FluidTags.getCollection().getTagMap().containsKey(name))
            return FluidTags.getCollection().getTagMap().get(name);
        return null;
    }
}
