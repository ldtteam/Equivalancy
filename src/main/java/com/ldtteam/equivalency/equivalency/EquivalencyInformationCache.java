package com.ldtteam.equivalency.equivalency;

import com.google.common.collect.Maps;
import com.ldtteam.equivalency.api.compound.ICompoundInstance;
import com.ldtteam.equivalency.api.compound.container.ICompoundContainer;
import com.ldtteam.equivalency.api.equivalency.IEquivalencyInformationCache;
import net.minecraft.world.DimensionType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class EquivalencyInformationCache implements IEquivalencyInformationCache
{

    private final        Map<ICompoundContainer<?>, Set<ICompoundInstance>> cacheData       = Maps.newConcurrentMap();
    private static final Map<DimensionType, EquivalencyInformationCache>    WORLD_INSTANCES = Maps.newConcurrentMap();

    private EquivalencyInformationCache()
    {

    }

    public static EquivalencyInformationCache getInstance(@NotNull final DimensionType dimensionType)
    {
        return WORLD_INSTANCES.computeIfAbsent(dimensionType, (dimType) -> new EquivalencyInformationCache());
    }

    @Override
    public Map<ICompoundContainer<?>, Set<ICompoundInstance>> get()
    {
        return cacheData;
    }

    public void set(@NotNull final Map<ICompoundContainer<?>, Set<ICompoundInstance>> data)
    {
        cacheData.putAll(data);
    }
}
