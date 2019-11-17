package com.ldtteam.equivalency.api.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class TypeUtils
{

    private TypeUtils()
    {
        throw new IllegalStateException("Tried to initialize: TypeUtils but this is a Utility class.");
    }

    public static Set<Class<?>> getAllSuperTypesExcludingObject(@NotNull final Class<?> clz)
    {
        final Set<Class<?>> result = Sets.newHashSet();
        result.add(clz);

        final List<Class<?>> directSuperTypes = Lists.newArrayList();
        if (clz.getSuperclass() != null)
            directSuperTypes.addAll(Collections.singleton(clz.getSuperclass()));

        if (clz.getInterfaces() != null && clz.getInterfaces().length > 0)
            directSuperTypes.addAll(Arrays.asList(clz.getInterfaces()));

        directSuperTypes.remove(Object.class);
        result.addAll(directSuperTypes.stream().map(TypeUtils::getAllSuperTypesExcludingObject).flatMap(Set::stream).collect(Collectors.toSet()));

        return result;
    }
}
