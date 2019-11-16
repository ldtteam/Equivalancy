package com.ldtteam.equivalency.tags;

import com.google.common.collect.Sets;
import com.ldtteam.equivalency.api.tags.ITagEquivalencyRegistry;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class TagEquivalencyRegistry implements ITagEquivalencyRegistry
{
    private static final TagEquivalencyRegistry INSTANCE = new TagEquivalencyRegistry();

    public static TagEquivalencyRegistry getInstance()
    {
        return INSTANCE;
    }

    private final Set<ResourceLocation> tags = Sets.newConcurrentHashSet();

    private TagEquivalencyRegistry()
    {
    }

    @Override
    public ITagEquivalencyRegistry addTag(@NotNull final ResourceLocation tagName)
    {
        tags.add(tagName);
        return this;
    }

    public Set<ResourceLocation> get()
    {
        return tags;
    }
}
