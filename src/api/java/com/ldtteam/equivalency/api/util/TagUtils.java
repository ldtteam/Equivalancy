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

    public static List<ITag.INamedTag<?>> get(@NotNull final ResourceLocation name)
    {
        final List<ITag.INamedTag<?>> result = Lists.newArrayList();

        if (BlockTags.getCollection().getTagMap().containsKey(name))
            result.add(BlockTags.makeWrapperTag(name.toString()));
        if (ItemTags.getCollection().getTagMap().containsKey(name))
            result.add(ItemTags.makeWrapperTag(name.toString()));
        if (EntityTypeTags.getCollection().getTagMap().containsKey(name))
            result.add(EntityTypeTags.func_232896_a_(name.toString()));
        if (FluidTags.getCollection().getTagMap().containsKey(name))
            result.add(FluidTags.makeWrapperTag(name.toString()));

        return result;
    }
}
