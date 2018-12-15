package com.ldtteam.equivalency.analyzer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.api.compound.ICompoundInstance;
import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import com.ldtteam.equivalency.api.recipe.IEquivalencyRecipeRegistry;
import org.apache.commons.lang3.Validate;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LPBasedCompoundCalculator
{

    public Map<ICompoundContainerWrapper<?>, Set<ICompoundInstance>> calculateCompounds(@NotNull final IEquivalencyRecipeRegistry registry)
    {
        Validate.notNull(registry);
        final Map<ICompoundContainerWrapper<?>, Set<ICompoundInstance>> resultingCompounds = new TreeMap<>();

        registry
          .getRecipes()
          .forEach(recipe -> {
              //Process inputs
              recipe.getInputs().forEach(input -> resultingCompounds.putIfAbsent(input, new TreeSet<>()));

              //Process outputs
              recipe.getOutputs().forEach(output -> resultingCompounds.putIfAbsent(output, new TreeSet<>()));
          });

        final Map<ICompoundContainerWrapper<?>, Integer> containerWrapperIndexMap = createIndexMap(resultingCompounds.keySet());
        
        final Set<ICompoundType> compoundTypes = new TreeSet<>(EquivalencyApi.getInstance().getCompoundTypeRegistry().getValues());
        final Map<ICompoundType, Integer> typeIndexes = createIndexMap(compoundTypes);

        final int variableCount = resultingCompounds.size() * compoundTypes.size();

        final double[] targetOffsets = new double[variableCount];
        Arrays.fill(targetOffsets, 1d);

        final Collection<LinearConstraint> constraints = Lists.newArrayList();

    }

    private static <T> Map<T, Integer> createIndexMap(@NotNull final Collection<T> collection)
    {
        Validate.notNull(collection);
        final Map<T, Integer> indexMap = Maps.newHashMap();
        int index = 0;
        for (final T t :
          collection)
        {
            indexMap.put(t, index++);
        }

        return indexMap;
    }
}
