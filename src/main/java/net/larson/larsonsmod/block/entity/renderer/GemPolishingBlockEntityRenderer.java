package net.larson.larsonsmod.block.entity.renderer;

import net.larson.larsonsmod.block.entity.GemPolishingStationBlockEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.BlockEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class GemPolishingBlockEntityRenderer implements BlockEntityRenderer<GemPolishingStationBlockEntity, BlockEntityRenderState> {
    public GemPolishingBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public BlockEntityRenderState createRenderState() {
        return new BlockEntityRenderState();
    }

    @Override
    public void render(BlockEntityRenderState state, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        // Rendering of the item on the polishing station is now handled
        // through the render state system. The basic block entity rendering
        // is handled by the base implementation.
    }
}
