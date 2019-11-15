package com.ldtteam.equivalency.api.client.drawable;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.vecmath.Vector2d;

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
    default Vector2d getSize()
    {
        if (getDrawables().isEmpty())
            return new Vector2d();

        if (getDrawables().size() == 1)
            //noinspection OptionalGetWithoutIsPresent (Size == 1 ensures that stream entry is present)
            return getDrawables().stream().findFirst().get().getSize();

        double width;
        double height;

        if (getOrientation() == Orientation.HORIZONTAL)
        {
            width = getDrawables().stream().mapToDouble(d -> d.getSize().getX() + getOffset().getX()).sum() - getOffset().getX();
            height = getDrawables().stream().mapToDouble(d -> d.getSize().getY()).max().orElse(0);
        }
        else
        {
            width = getDrawables().stream().mapToDouble(d -> d.getSize().getX()).max().orElse(0);
            height = getDrawables().stream().mapToDouble(d -> d.getSize().getY() + getOffset().getY()).sum() - getOffset().getY();
        }

        return new Vector2d(width, height);
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
     * Can be either triggered via a call to {@link #drawVertical()} or
     * if a call to {@link #draw()} when {@link #getOrientation()}
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
     * Can be either triggered via a call to {@link #drawHorizontal()} or
     * if a call to {@link #draw()} when {@link #getOrientation()}
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
    Vector2d getOffset();

    /**
     * Draws this object.
     */
    @Override
    default void draw()
    {
        switch (getOrientation())
        {
            case HORIZONTAL:
                drawHorizontal();
                break;
            case VERTICAL:
                drawVertical();
                break;
        }
    }

    @OnlyIn(Dist.CLIENT)
    default void drawVertical()
    {
        GlStateManager.pushMatrix();

        getDrawables().forEach(iSizedDrawable -> {
            GlStateManager.pushMatrix();

            switch (getVerticalStrategy())
            {
                case LEFT:
                    break;
                case CENTER:
                    GlStateManager.translated((getSize().getX() - iSizedDrawable.getSize().getX()) / 2, 0f, 0f);
                    break;
                case RIGHT:
                    GlStateManager.translated(getSize().getX() - iSizedDrawable.getSize().getX(), 0f, 0f);
                    break;
            }

            iSizedDrawable.draw();

            GlStateManager.popMatrix();

            GlStateManager.translated(0f, iSizedDrawable.getSize().getY() + getOffset().getY(), 0f);
        });

        GlStateManager.popMatrix();
    }

    @OnlyIn(Dist.CLIENT)
    default void drawHorizontal()
    {
        GlStateManager.pushMatrix();

        getDrawables().forEach(iSizedDrawable -> {
            GlStateManager.pushMatrix();

            switch (getHorizontalStrategy())
            {
                case TOP:
                    break;
                case CENTER:
                    GlStateManager.translated(0f, (getSize().getY() - iSizedDrawable.getSize().getY()) / 2,0f);
                case BOTTOM:
                    GlStateManager.translated(0f, getSize().getY() - iSizedDrawable.getSize().getY(), 0f);
            }

            iSizedDrawable.draw();

            GlStateManager.popMatrix();

            GlStateManager.translated(iSizedDrawable.getSize().getX() + getOffset().getX(), 0f, 0f);
        });

        GlStateManager.popMatrix();
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
