package com.ldtteam.equivalency.api.client.drawable;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
    @SideOnly(Side.CLIENT)
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
    @SideOnly(Side.CLIENT)
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
    @SideOnly(Side.CLIENT)
    HorizontalArrayedDrawingStrategy getHorizontalStrategy();

    /**
     * The offset between each of the drawables.
     *
     * @return The offset between drawables.
     */
    @SideOnly(Side.CLIENT)
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

    @SideOnly(Side.CLIENT)
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
                    GlStateManager.translate((getSize().getX() - iSizedDrawable.getSize().getX()) / 2, 0f, 0f);
                    break;
                case RIGHT:
                    GlStateManager.translate(getSize().getX() - iSizedDrawable.getSize().getX(), 0f, 0f);
                    break;
            }

            iSizedDrawable.draw();

            GlStateManager.popMatrix();

            GlStateManager.translate(0f, iSizedDrawable.getSize().getY() + getOffset().getY(), 0f);
        });

        GlStateManager.popMatrix();
    }

    @SideOnly(Side.CLIENT)
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
                    GlStateManager.translate(0f, (getSize().getY() - iSizedDrawable.getSize().getY()) / 2,0f);
                case BOTTOM:
                    GlStateManager.translate(0f, getSize().getY() - iSizedDrawable.getSize().getY(), 0f);
            }

            iSizedDrawable.draw();

            GlStateManager.popMatrix();

            GlStateManager.translate(iSizedDrawable.getSize().getX() + getOffset().getX(), 0f, 0f);
        });

        GlStateManager.popMatrix();
    }

    /**
     * Defines the possible orientations of the arrayed drawable
     */
    @SideOnly(Side.CLIENT)
    enum Orientation
    {
        HORIZONTAL,
        VERTICAL
    }

    /**
     * Defines the possible strategies used to draw the {@link IDrawable} of the {@link IArrayedDrawable}
     * when drawn in vertical mode.
     */
    @SideOnly(Side.CLIENT)
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
    @SideOnly(Side.CLIENT)
    enum HorizontalArrayedDrawingStrategy
    {
        TOP,
        CENTER,
        BOTTOM
    }
}
