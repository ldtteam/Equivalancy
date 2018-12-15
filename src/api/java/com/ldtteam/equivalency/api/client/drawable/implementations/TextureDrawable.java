package com.ldtteam.equivalency.api.client.drawable.implementations;

import com.ldtteam.equivalency.api.client.drawable.I2DDrawable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Vector2d;

/**
 * A Simple 2D texture drawable resource.
 */
@SideOnly(Side.CLIENT)
public class TextureDrawable implements I2DDrawable
{
    private final ResourceLocation icon;
    private final Vector2d size;

    public TextureDrawable(final ResourceLocation icon, final Vector2d size) {
        this.icon = icon;
        this.size = size;
    }

    /**
     * The resource to draw.
     *
     * @return The icon to draw.
     */
    @SideOnly(Side.CLIENT)
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
    @SideOnly(Side.CLIENT)
    @Override
    public Vector2d getSize()
    {
        return size;
    }
}
