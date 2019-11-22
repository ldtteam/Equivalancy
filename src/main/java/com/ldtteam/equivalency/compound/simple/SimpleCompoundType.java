package com.ldtteam.equivalency.compound.simple;

import com.ldtteam.equivalency.api.client.drawable.IDrawable;
import com.ldtteam.equivalency.api.compound.ICompoundType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Supplier;

public class SimpleCompoundType extends ForgeRegistryEntry<ICompoundType> implements ICompoundType
{

    @NotNull
    private final Supplier<Supplier<IDrawable>> drawableSupplier;
    @NotNull
    private final ITextComponent translationText;

    private final int color;

    public SimpleCompoundType(@NotNull final Supplier<Supplier<IDrawable>> drawableSupplier, @NotNull final ITextComponent translationText, final int color)
    {
        this.drawableSupplier = Objects.requireNonNull(drawableSupplier);
        this.translationText = Objects.requireNonNull(translationText);
        this.color = color;
    }

    @NotNull
    @Override
    public Supplier<Supplier<IDrawable>> getDrawableSupplier()
    {
        return drawableSupplier;
    }

    @NotNull
    @Override
    public ITextComponent getTranslation()
    {
        return translationText;
    }

    @Override
    public int getColor()
    {
        return color;
    }

    @Override
    public int compareTo(@NotNull final ICompoundType o)
    {
        return Objects.requireNonNull(getRegistryName()).toString().compareTo(Objects.requireNonNull(o.getRegistryName()).toString());
    }
}
