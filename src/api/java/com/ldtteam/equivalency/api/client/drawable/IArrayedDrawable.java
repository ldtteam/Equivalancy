package com.ldtteam.equivalency.api.client.drawable;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * An {@link IDrawable} that is made up out of several different {@link ISizedDrawable} instances.
 * The instances are drawn in some relevant order depending on the implementation.
 */
public interface IArrayedDrawable extends ICombiningDrawable
{

    /**
     * The intended size of the drawable.
     *
     * @return The requested size on neutral scale.
     */
    @Override
    default Vector2f getSize()
    {
        if (getDrawables().isEmpty())
            return new Vector2f(0,0);

        if (getDrawables().size() == 1)
            return getDrawables().iterator().next().getSize();

        float width;
        float height;

        if (getOrientation() == Orientation.HORIZONTAL)
        {
            width = (float) (getDrawables().stream().mapToDouble(d -> d.getSize().x + getOffset().x).sum() - getOffset().x);
            height = (float) getDrawables().stream().mapToDouble(d -> d.getSize().y).max().orElse(0);
        }
        else
        {
            width = (float) getDrawables().stream().mapToDouble(d -> d.getSize().x).max().orElse(0);
            height = (float) (getDrawables().stream().mapToDouble(d -> d.getSize().y + getOffset().y).sum() - getOffset().y);
        }

        return new Vector2f(width, height);
    }

    /**
     * The orientation of the arrayed drawable.
     * 
     * @return The orientation.
     */
    @OnlyIn(Dist.CLIENT)
    Orientation getOrientation();

    /**
     * The vertical drawing strategy.
     * 
     * Relevant when drawing in vertical mode.
     * Can be either triggered via a call to {@link #drawVertical(MatrixStack)} or
     * if a call to {@link #draw(MatrixStack)} when {@link #getOrientation()}
     * returns {@link Orientation#VERTICAL} is made.
     * 
     * @return The strategy used during vertical drawing.
     */
    @OnlyIn(Dist.CLIENT)
    VerticalArrayedDrawingStrategy getVerticalStrategy();

    /**
     * The horizontal drawing strategy.
     *
     * Relevant when drawing in horizontal mode.
     * Can be either triggered via a call to {@link #drawHorizontal(MatrixStack)} or
     * if a call to {@link #draw(MatrixStack)} when {@link #getOrientation()}
     * returns {@link Orientation#HORIZONTAL} is made.
     *
     * @return The strategy used during horizontal drawing.
     */
    @OnlyIn(Dist.CLIENT)
    HorizontalArrayedDrawingStrategy getHorizontalStrategy();

    /**
     * The offset between each of the drawables.
     *
     * @return The offset between drawables.
     */
    @OnlyIn(Dist.CLIENT)
    Vector2f getOffset();

    /**
     * Draws this object.
     */
    @Override
    default void draw(final MatrixStack stack)
    {
        switch (getOrientation())
        {
            case HORIZONTAL:
                drawHorizontal(stack);
                break;
            case VERTICAL:
                drawVertical(stack);
                break;
        }
    }

    @OnlyIn(Dist.CLIENT)
    default void drawVertical(final MatrixStack stack)
    {
        stack.push();

        getDrawables().forEach(iSizedDrawable -> {
            stack.push();

            switch (getVerticalStrategy())
            {
                case LEFT:
                    break;
                case CENTER:
                    stack.translate((getSize().x - iSizedDrawable.getSize().x) / 2, 0f, 0f);
                    break;
                case RIGHT:
                    stack.translate(getSize().x - iSizedDrawable.getSize().x, 0f, 0f);
                    break;
            }

            iSizedDrawable.draw(stack);

            stack.pop();

            stack.translate(0f, iSizedDrawable.getSize().y + getOffset().y, 0f);
        });

        stack.pop();
    }

    @OnlyIn(Dist.CLIENT)
    default void drawHorizontal(final MatrixStack stack)
    {
        stack.push();

        getDrawables().forEach(iSizedDrawable -> {
            stack.pop();

            switch (getHorizontalStrategy())
            {
                case TOP:
                    break;
                case CENTER:
                    stack.translate(0f, (getSize().y - iSizedDrawable.getSize().y) / 2,0f);
                case BOTTOM:
                    stack.translate(0f, getSize().y - iSizedDrawable.getSize().y, 0f);
            }

            iSizedDrawable.draw(stack);

            stack.pop();

            stack.translate(iSizedDrawable.getSize().x + getOffset().x, 0f, 0f);
        });

        stack.pop();
    }

    /**
     * Defines the possible orientations of the arrayed drawable
     */
    @OnlyIn(Dist.CLIENT)
    enum Orientation
    {
        HORIZONTAL,
        VERTICAL
    }

    /**
     * Defines the possible strategies used to draw the {@link IDrawable} of the {@link IArrayedDrawable}
     * when drawn in vertical mode.
     */
    @OnlyIn(Dist.CLIENT)
    enum VerticalArrayedDrawingStrategy
    {
        LEFT,
        CENTER,
        RIGHT
    }

    /**
     * Defines the possible strategies used to draw the {@link IDrawable} of the {@link IArrayedDrawable}
     * when drawn in horizontal mode.
     */
    @OnlyIn(Dist.CLIENT)
    enum HorizontalArrayedDrawingStrategy
    {
        TOP,
        CENTER,
        BOTTOM
    }
}
