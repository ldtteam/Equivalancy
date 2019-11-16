package com.ldtteam.equivalency.api.equivalency;

import com.ldtteam.equivalency.api.compound.ICompoundInstance;
import com.ldtteam.equivalency.api.compound.container.ICompoundContainer;

import java.util.Map;
import java.util.Set;

public interface IEquivalencyInformationCache
{

    /**
     * Returns the calculated and cached equivalency information.
     * @return The calculated and cached data, if no information is available for that dimension, then an empty map is returned.
     */
    Map<ICompoundContainer<?>, Set<ICompoundInstance>> get();
}
