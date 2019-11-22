package com.ldtteam.equivalency.api.client.drawable.provider;

import com.ldtteam.equivalency.api.client.drawable.IDrawable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents object in the game who can be drawn.
 * Each object provides a handler to its drawable.
 */
public interface IDrawableProvider
{

    /**
     * Gives access to a supplier which produces a handler that creates a new drawable instance.
     *
     * @return The supplier which creates a new handler to get the {@link IDrawable}.
     */
    @NotNull
    Supplier<Supplier<IDrawable>> getDrawableSupplier();
}
