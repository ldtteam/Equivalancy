package com.ldtteam.equivalency.api.client.drawable.implementations;

import com.ldtteam.equivalency.api.client.drawable.I2DDrawable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.vecmath.Vector2d;

/**
 * A Simple 2D texture drawable resource.
 */
@OnlyIn(Dist.CLIENT)
public class TextureDrawable implements I2DDrawable
{
    private final ResourceLocation icon;
    private final Vector2d size;

    public TextureDrawable(final ResourceLocation icon) {
        this(icon, new Vector2d(16, 16));
    }

    public TextureDrawable(final ResourceLocation icon, final Vector2d size) {
        this.icon = icon;
        this.size = size;
    }

    /**
     * The resource to draw.
     *
     * @return The icon to draw.
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public ResourceLocation getIcon()
    {
        return icon;
    }

    /**
     * The intended size of the drawable.
     *
     * @return The requested size on neutral scale.
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public Vector2d getSize()
    {
        return size;
    }
}
