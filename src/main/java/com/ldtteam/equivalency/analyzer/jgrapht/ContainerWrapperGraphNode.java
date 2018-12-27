package com.ldtteam.equivalency.analyzer.jgrapht;

import com.google.common.collect.Sets;
import com.ldtteam.equivalency.api.compound.ICompoundInstance;
import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ContainerWrapperGraphNode implements IAnalysisGraphNode
{
    @NotNull
    private final ICompoundContainerWrapper<?> wrapper;

    @NotNull
    private final Set<ICompoundInstance> compoundInstances = Sets.newConcurrentHashSet();

    public ContainerWrapperGraphNode(@NotNull final ICompoundContainerWrapper<?> wrapper) {this.wrapper = wrapper;}

    @NotNull
    public ICompoundContainerWrapper<?> getWrapper()
    {
        return wrapper;
    }

    @NotNull
    public Set<ICompoundInstance> getCompoundInstances()
    {
        return compoundInstances;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof ContainerWrapperGraphNode))
        {
            return false;
        }

        final ContainerWrapperGraphNode that = (ContainerWrapperGraphNode) o;

        return getWrapper().equals(that.getWrapper());
    }

    @Override
    public int hashCode()
    {
        return getWrapper().hashCode();
    }

    @Override
    public String toString()
    {
        return "ContainerWrapperGraphNode{" +
                 "wrapper=" + wrapper +
                 '}';
    }



}
