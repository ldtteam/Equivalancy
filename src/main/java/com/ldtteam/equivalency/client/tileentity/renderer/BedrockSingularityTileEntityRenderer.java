package com.ldtteam.equivalency.client.tileentity.renderer;

import com.google.common.collect.ImmutableList;
import com.ldtteam.equivalency.api.tileentity.AbstractBedrockSingularityTileEntity;
import com.ldtteam.equivalency.tileentity.BedrockSingularityTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix4f;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class BedrockSingularityTileEntityRenderer extends TileEntityRenderer<AbstractBedrockSingularityTileEntity>
{
    private static final Random           RANDOM       = new Random(31100L);
    private static final List<RenderType> RENDER_TYPES = IntStream.range(0, 16).mapToObj((p_228882_0_) -> RenderType.getEndPortal(p_228882_0_ + 1)).collect(ImmutableList.toImmutableList());

    public BedrockSingularityTileEntityRenderer(final TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(
      final AbstractBedrockSingularityTileEntity tileEntityIn,
      final float partialTicks,
      final MatrixStack matrixStackIn,
      final IRenderTypeBuffer bufferIn,
      final int combinedLightIn,
      final int combinedOverlayIn)
    {
        RANDOM.setSeed(31100L);
        double renderDistance = tileEntityIn.getPos().distanceSq(this.renderDispatcher.renderInfo.getProjectedView(), true);
        int renderPasses = this.getPasses(renderDistance);
        float offset = this.getOffset();
        Matrix4f renderMatrix = matrixStackIn.getLast().getMatrix();
        this.renderCube(offset, 0.15F, renderMatrix, bufferIn.getBuffer(RENDER_TYPES.get(0)));

        for(int j = 1; j < renderPasses; ++j) {
            this.renderCube(offset, 2.0F / (float)(18 - j), renderMatrix, bufferIn.getBuffer(RENDER_TYPES.get(j)));
        }
    }

    private void renderCube(float offset, float colorIntensity, Matrix4f renderMatrix, IVertexBuilder vertexBuilder) {
        float red = (RANDOM.nextFloat() * 0.5F + 0.9F) * colorIntensity;
        float green = (RANDOM.nextFloat() * 0.5F + 0.0F) * colorIntensity;
        float blue = (RANDOM.nextFloat() * 0.5F + 0.0F) * colorIntensity;
        this.renderFace(renderMatrix, vertexBuilder, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, red, green, blue, Direction.SOUTH);
        this.renderFace(renderMatrix, vertexBuilder, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, red, green, blue, Direction.NORTH);
        this.renderFace(renderMatrix, vertexBuilder, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, red, green, blue, Direction.EAST);
        this.renderFace(renderMatrix, vertexBuilder, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, red, green, blue, Direction.WEST);
        this.renderFace(renderMatrix, vertexBuilder, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, red, green, blue, Direction.DOWN);
        this.renderFace(renderMatrix, vertexBuilder, 0.0F, 1.0F, offset, offset, 1.0F, 1.0F, 0.0F, 0.0F, red, green, blue, Direction.UP);
    }

    private void renderFace(Matrix4f renderMatrix, IVertexBuilder vertexBuilder, float lowX, float highX, float lowY, float highY, float lowLowZ, float highLowZ, float highHighZ, float lowHighZ, float red, float green, float blue, Direction facing) {
            vertexBuilder.pos(renderMatrix, lowX, lowY, lowLowZ).color(red, green, blue, 1.0F).endVertex();
            vertexBuilder.pos(renderMatrix, highX, lowY, highLowZ).color(red, green, blue, 1.0F).endVertex();
            vertexBuilder.pos(renderMatrix, highX, highY, highHighZ).color(red, green, blue, 1.0F).endVertex();
            vertexBuilder.pos(renderMatrix, lowX, highY, lowHighZ).color(red, green, blue, 1.0F).endVertex();
    }

    protected int getPasses(double renderDistance) {
        if (renderDistance > 36864.0D) {
            return 1;
        } else if (renderDistance > 25600.0D) {
            return 3;
        } else if (renderDistance > 16384.0D) {
            return 5;
        } else if (renderDistance > 9216.0D) {
            return 7;
        } else if (renderDistance > 4096.0D) {
            return 9;
        } else if (renderDistance > 1024.0D) {
            return 11;
        } else if (renderDistance > 576.0D) {
            return 13;
        } else {
            return renderDistance > 256.0D ? 14 : 15;
        }
    }

    protected float getOffset() {
        return 1F;
    }
}
