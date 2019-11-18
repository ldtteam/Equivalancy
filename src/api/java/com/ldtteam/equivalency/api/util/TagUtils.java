package com.ldtteam.equivalency.api.util;

import com.google.common.collect.Lists;
import net.minecraft.tags.*;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class TagUtils
{

    private TagUtils()
    {
        throw new IllegalStateException("Tried to initialize: TagUtils but this is a Utility class.");
    }

    public static List<Tag<?>> get(@NotNull final ResourceLocation name)
    {
        final List<Tag<?>> result = Lists.newArrayList();

        if (BlockTags.getCollection().getTagMap().containsKey(name))
            result.add(BlockTags.getCollection().getTagMap().get(name));
        if (ItemTags.getCollection().getTagMap().containsKey(name))
            result.add(ItemTags.getCollection().getTagMap().get(name));
        if (EntityTypeTags.getCollection().getTagMap().containsKey(name))
            result.add(EntityTypeTags.getCollection().getTagMap().get(name));
        if (FluidTags.getCollection().getTagMap().containsKey(name))
            result.add(FluidTags.getCollection().getTagMap().get(name));

        return result;
    }
}
