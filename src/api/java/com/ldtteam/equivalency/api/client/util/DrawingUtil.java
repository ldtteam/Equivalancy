package com.ldtteam.equivalency.api.client.util;

import com.mojang.blaze3d.platform.GlStateManager;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

public final class DrawingUtil
{

    private DrawingUtil()
    {
        throw new IllegalStateException("Tried to initialize: DrawingUtil but this is a Utility class.");
    }

    public static void drawWithAlphaAndBlending(@NotNull final Runnable drawingCode)
    {
        Validate.notNull(drawingCode);

        GlStateManager.pushMatrix();
        GlStateManager.enableAlphaTest();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
        GlStateManager.pushMatrix();

        drawingCode.run();

        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
        GlStateManager.disableAlphaTest();
        GlStateManager.popMatrix();
    }
}
