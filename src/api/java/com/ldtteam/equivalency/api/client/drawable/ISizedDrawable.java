package com.ldtteam.equivalency.api.client.drawable;

import net.minecraft.util.math.vector.Vector2f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Represents drawable with a given size.
 */
public interface ISizedDrawable extends IDrawable
{
    /**
     * The intended size of the drawable.
     *
     * @return The requested size on neutral scale.
     */
    @OnlyIn(Dist.CLIENT)
    Vector2f getSize();
}
