package com.ldtteam.equivalency.api.client.drawable;

import com.ldtteam.equivalency.api.client.util.DrawingUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Represents a 2D Drawable and its information.
 * Provides a default draw method that draws using the information.
 */
public interface I2DDrawable extends ISizedDrawable
{
    /**
     * The resource to draw.
     *
     * @return The icon to draw.
     */
    @OnlyIn(Dist.CLIENT)
    ResourceLocation getIcon();

    /**
     * Draws this object.
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    default void draw()
    {
        DrawingUtil.drawWithAlphaAndBlending(() -> {
            Minecraft.getInstance().getTextureManager().bindTexture(getIcon());

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos((double)(0), (0 + getSize().getY()), (double)0).tex(0, 1).endVertex();
            bufferbuilder.pos((0 + getSize().getX()), (0 + getSize().getY()), (double)0).tex(1, (double)1).endVertex();
            bufferbuilder.pos((0 + getSize().getX()), (double)(0), (double)0).tex((double)1, (double)0).endVertex();
            bufferbuilder.pos((double)(0), (double)(0), (double)0).tex((double)0, (double)0).endVertex();
            tessellator.draw();
        });
    }
}
