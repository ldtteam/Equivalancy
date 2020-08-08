package com.ldtteam.equivalency.compound.simple.builder;

import com.ldtteam.equivalency.api.client.drawable.IDrawable;
import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.compound.simple.SimpleCompoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Represents a builder for {@link ICompoundType}.
 */
public class SimpleCompoundTypeBuilder {
    private static final String TRANSLATION_PREFIX = "compound.type";


    private ResourceLocation location;
    private Supplier<Supplier<IDrawable>> drawableSupplier;
    private ITextComponent                translationText;
    private int                           color;

    /**
     * Sets the drawable supplier for the compound type.
     *
     * @param drawableSupplier The supplier for the producer of the drawable.
     * @return The builder.
     */
    public SimpleCompoundTypeBuilder setDrawableSupplier(@NotNull final Supplier<Supplier<IDrawable>> drawableSupplier)
    {
        this.drawableSupplier = Objects.requireNonNull(drawableSupplier);
        return this;
    }

    /**
     * Sets the translation text component used to display the name of the compound.
     *
     * @param translationText The text component used display the name of the compound.
     * @return The builder.
     */
    public SimpleCompoundTypeBuilder setTranslationText(@NotNull final ITextComponent translationText)
    {
        this.translationText = Objects.requireNonNull(translationText);
        return this;
    }

    /**
     * Utility method to set the display name and registry name in one go.
     * Prefixes the display names translation key with: "compound.type." and then appends the path from the given
     * {@link ResourceLocation}
     *
     * @param name The name for the compound type.
     * @return The builder.
     */
    public SimpleCompoundTypeBuilder withName(@NotNull final ResourceLocation name)
    {
        return this
          .setTranslationText(new TranslationTextComponent(String.format("%s.%s", TRANSLATION_PREFIX, name)))
          .setRegistryName(name);
    }

    /**
     * Sets the registry name of the compound.
     * Has to be unique.
     *
     * @param location The registry name of the compound.
     * @return The builder.
     */
    public SimpleCompoundTypeBuilder setRegistryName(@NotNull final ResourceLocation location)
    {
        this.location = Objects.requireNonNull(location);
        return this;
    }

    /**
     * Sets the color of the compound.
     *
     * @param color The color of the compound.
     * @return THe builder.
     */
    public SimpleCompoundTypeBuilder setColor(final int color)
    {
        this.color = color;
        return this;
    }

    /**
     * Creates a new instance of a {@link ICompoundType}.
     *
     * @return The compound.
     */
    public SimpleCompoundType create()
    {
        final SimpleCompoundType compoundType = new SimpleCompoundType(drawableSupplier, translationText, color);
        compoundType.setRegistryName(location);

        return compoundType;
    }
}