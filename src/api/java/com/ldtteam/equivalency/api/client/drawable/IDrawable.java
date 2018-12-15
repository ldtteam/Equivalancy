package com.ldtteam.equivalency.api.client.drawable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Represents drawables.
 * Each instance of this interface can be drawn on screen, in either 2D or 3D;
 */
public interface IDrawable
{

    /**
     * Draws this object.
     */
    @SideOnly(Side.CLIENT)
    void draw();
}
