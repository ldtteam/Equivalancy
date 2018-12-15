package com.ldtteam.equivalency.api.client.drawable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Vector2d;

public interface ISizedDrawable extends IDrawable
{
    /**
     * The intended size of the drawable.
     *
     * @return The requested size on neutral scale.
     */
    @SideOnly(Side.CLIENT)
    Vector2d getSize();
}
