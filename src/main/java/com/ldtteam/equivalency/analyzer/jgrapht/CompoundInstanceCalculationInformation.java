package com.ldtteam.equivalency.analyzer.jgrapht;

import com.ldtteam.equivalency.api.compound.ICompoundInstance;

public class CompoundInstanceCalculationInformation
{

    private final IAnalysisGraphNode source;
    private final ICompoundInstance instance;

    public CompoundInstanceCalculationInformation(final IAnalysisGraphNode source, final ICompoundInstance instance) {
        this.source = source;
        this.instance = instance;
    }
}
