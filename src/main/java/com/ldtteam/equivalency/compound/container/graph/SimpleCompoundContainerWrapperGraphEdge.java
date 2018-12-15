package com.ldtteam.equivalency.compound.container.graph;

import com.ldtteam.equivalency.api.compound.container.wrapper.graph.ICompoundContainerWrapperGraphEdge;
import com.ldtteam.equivalency.api.compound.container.wrapper.graph.ICompoundContainerWrapperGraphEdgeInformation;
import com.ldtteam.equivalency.api.compound.container.wrapper.graph.ICompoundContainerWrapperGraphNode;

public class SimpleCompoundContainerWrapperGraphEdge implements ICompoundContainerWrapperGraphEdge
{
    private final ICompoundContainerWrapperGraphNode source;
    private final ICompoundContainerWrapperGraphNode target;
    private final ICompoundContainerWrapperGraphEdgeInformation information;

    public SimpleCompoundContainerWrapperGraphEdge(
      final ICompoundContainerWrapperGraphNode source,
      final ICompoundContainerWrapperGraphNode target, final ICompoundContainerWrapperGraphEdgeInformation information) {
        this.source = source;
        this.target = target;
        this.information = information;
    }

    @Override
    public ICompoundContainerWrapperGraphNode getSource()
    {
        return source;
    }

    @Override
    public ICompoundContainerWrapperGraphNode getTarget()
    {
        return target;
    }

    @Override
    public ICompoundContainerWrapperGraphEdgeInformation getInformation()
    {
        return information;
    }
}
