package com.ldtteam.equivalency.api.client.drawable.implementations;

import com.ldtteam.equivalency.api.client.drawable.ISizedDrawable;
import com.ldtteam.equivalency.api.client.util.DrawingUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Vector2d;

@SideOnly(Side.CLIENT)
public class TextDrawable implements ISizedDrawable
{

    @SideOnly(Side.CLIENT)
    private final FontRenderer   fontRenderer;
    private final ITextComponent string;

    public TextDrawable(final FontRenderer fontRenderer, final ITextComponent string) {
        this.fontRenderer = fontRenderer;
        this.string = string;
    }

    /**
     * The intended size of the drawable.
     *
     * @return The requested size on neutral scale.
     */
    @Override
    public Vector2d getSize()
    {
        return new Vector2d(
          fontRenderer.getStringWidth(string.getFormattedText()),
          fontRenderer.FONT_HEIGHT
        );
    }

    /**
     * Draws this object.
     */
    @Override
    public void draw()
    {
        DrawingUtil.drawWithAlphaAndBlending(() -> {
            this.fontRenderer.drawStringWithShadow(string.getFormattedText(), 0,0, 0xffffffff);
        });
    }
}
