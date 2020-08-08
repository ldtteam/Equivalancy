package com.ldtteam.equivalency.api.client.drawable.implementations;

import com.ldtteam.equivalency.api.client.drawable.ISizedDrawable;
import com.ldtteam.equivalency.api.client.util.DrawingUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TextDrawable implements ISizedDrawable
{

    @OnlyIn(Dist.CLIENT)
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
    public Vector2f getSize()
    {
        return new Vector2f(
          fontRenderer.getStringWidth(string.getString()),
          fontRenderer.FONT_HEIGHT
        );
    }

    /**
     * Draws this object.
     */
    @Override
    public void draw(final MatrixStack stack)
    {
        DrawingUtil.drawWithAlphaAndBlending(stack, () -> {
            this.fontRenderer.drawStringWithShadow(stack, string.getString(), 0,0, 0xffffffff);
        });
    }
}
