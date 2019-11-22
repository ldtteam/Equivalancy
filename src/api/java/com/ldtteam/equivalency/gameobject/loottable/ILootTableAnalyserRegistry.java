package com.ldtteam.equivalency.gameobject.loottable;

import com.ldtteam.equivalency.api.compound.container.ICompoundContainer;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The registry which is used to register handlers which handle loottable based equivalencies.
 */
public interface ILootTableAnalyserRegistry
{


    @NotNull
    <T> ILootTableAnalyserRegistry register(
      @NotNull Class<T> lootTableType,
      @NotNull BiFunction<ICompoundContainer<? extends T>, ServerWorld, Set<ICompoundContainer<?>>> handlerCallback
    );
}
