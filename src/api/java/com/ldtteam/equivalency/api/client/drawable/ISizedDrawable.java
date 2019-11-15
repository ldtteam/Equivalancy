package com.ldtteam.equivalency.api.client.drawable;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.vecmath.Vector2d;

public interface ISizedDrawable extends IDrawable
{
    /**
     * The intended size of the drawable.
     *
     * @return The requested size on neutral scale.
     */
    @OnlyIn(Dist.CLIENT)
    Vector2d getSize();
}
