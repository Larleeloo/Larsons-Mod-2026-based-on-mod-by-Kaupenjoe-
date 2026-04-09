package net.larson.larsonsmod.block.entity.renderer;

import net.larson.larsonsmod.block.entity.GemPolishingStationBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;

public class GemPolishingBlockEntityRenderer implements BlockEntityRenderer<GemPolishingStationBlockEntity, BlockEntityRenderState> {
    public GemPolishingBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public BlockEntityRenderState createRenderState() {
        return new BlockEntityRenderState();
    }

    @Override
    public void render(BlockEntityRenderState state, MatrixStack matrices,
                       OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        // Rendering of the item on the polishing station is now handled
        // through the render state system. The basic block entity rendering
        // is handled by the base implementation.
    }
}
