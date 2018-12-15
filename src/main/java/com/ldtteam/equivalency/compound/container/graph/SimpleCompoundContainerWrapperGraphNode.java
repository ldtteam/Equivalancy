package com.ldtteam.equivalency.compound.container.graph;

import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import com.ldtteam.equivalency.api.compound.container.wrapper.graph.ICompoundContainerWrapperGraphEdge;
import com.ldtteam.equivalency.api.compound.container.wrapper.graph.ICompoundContainerWrapperGraphNode;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class SimpleCompoundContainerWrapperGraphNode implements ICompoundContainerWrapperGraphNode
{
    @NotNull
    private final ICompoundContainerWrapper<?> nodeContents;

    private final Set<ICompoundContainerWrapperGraphEdge> incoming;
    private final Set<ICompoundContainerWrapperGraphEdge> outgoing;

    public SimpleCompoundContainerWrapperGraphNode(@NotNull final ICompoundContainerWrapper<?> nodeContents)
    {
        this.nodeContents = nodeContents;
        this.incoming = new HashSet<>();
        this.outgoing = new HashSet<>();
    }

    public SimpleCompoundContainerWrapperGraphNode(
      @NotNull final ICompoundContainerWrapper<?> nodeContents,
      final Set<ICompoundContainerWrapperGraphEdge> incoming,
      final Set<ICompoundContainerWrapperGraphEdge> outgoing) {
        this.nodeContents = nodeContents;
        this.incoming = incoming;
        this.outgoing = outgoing;
    }

    @Override
    public ICompoundContainerWrapper<?> getNodeContents()
    {
        return nodeContents;
    }

    @Override
    public Set<ICompoundContainerWrapperGraphEdge> getIncomingEdges()
    {
        return incoming;
    }

    @Override
    public Set<ICompoundContainerWrapperGraphEdge> getOutgoingEdges()
    {
        return outgoing;
    }
}
