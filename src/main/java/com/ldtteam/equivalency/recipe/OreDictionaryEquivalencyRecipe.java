package com.ldtteam.equivalency.recipe;

import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import com.ldtteam.equivalency.api.recipe.IEquivalencyRecipe;

import java.util.Set;

public class OreDictionaryEquivalencyRecipe implements IEquivalencyRecipe
{
    private final String oreDictionaryName;
    private final Set<ICompoundContainerWrapper<?>> inputs;
    private final Set<ICompoundContainerWrapper<?>> outputs;

    public OreDictionaryEquivalencyRecipe(
      final String oreDictionaryName,
      final Set<ICompoundContainerWrapper<?>> inputs, final Set<ICompoundContainerWrapper<?>> outputs) {
        this.oreDictionaryName = oreDictionaryName;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public String getOreDictionaryName()
    {
        return oreDictionaryName;
    }

    @Override
    public Set<ICompoundContainerWrapper<?>> getInputs()
    {
        return inputs;
    }

    @Override
    public Set<ICompoundContainerWrapper<?>> getOutputs()
    {
        return outputs;
    }

    @Override
    public Double getOffsetFactor()
    {
        return 1D;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof OreDictionaryEquivalencyRecipe))
        {
            return false;
        }

        final OreDictionaryEquivalencyRecipe that = (OreDictionaryEquivalencyRecipe) o;

        if (getOreDictionaryName() != null ? !getOreDictionaryName().equals(that.getOreDictionaryName()) : that.getOreDictionaryName() != null)
        {
            return false;
        }
        if (getInputs() != null ? !getInputs().equals(that.getInputs()) : that.getInputs() != null)
        {
            return false;
        }
        return getOutputs() != null ? getOutputs().equals(that.getOutputs()) : that.getOutputs() == null;
    }

    @Override
    public int hashCode()
    {
        int result = getOreDictionaryName() != null ? getOreDictionaryName().hashCode() : 0;
        result = 31 * result + (getInputs() != null ? getInputs().hashCode() : 0);
        result = 31 * result + (getOutputs() != null ? getOutputs().hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "OreDictionaryEquivalencyRecipe{" +
                 "oreDictionaryName='" + oreDictionaryName + '\'' +
                 ", inputs=" + inputs +
                 ", outputs=" + outputs +
                 '}';
    }
}
