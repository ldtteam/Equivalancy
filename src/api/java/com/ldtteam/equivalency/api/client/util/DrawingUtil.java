package com.ldtteam.equivalency.api.client.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

public final class DrawingUtil
{

    private DrawingUtil()
    {
        throw new IllegalStateException("Tried to initialize: DrawingUtil but this is a Utility class.");
    }

    public static void drawWithAlphaAndBlending(@NotNull final MatrixStack stack, @NotNull final Runnable drawingCode)
    {
        Validate.notNull(drawingCode);

        stack.push();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
        stack.push();

        drawingCode.run();

        stack.pop();
        RenderSystem.disableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.popMatrix();
    }
}
