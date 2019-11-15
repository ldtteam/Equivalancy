package com.ldtteam.equivalency.api.client.drawable;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Represents drawables.
 * Each instance of this interface can be drawn on screen, in either 2D or 3D;
 */
public interface IDrawable
{

    /**
     * Draws this object.
     */
    @OnlyIn(Dist.CLIENT)
    void draw();
}
