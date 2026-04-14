package net.larson.larsonsmod;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.larson.larsonsmod.block.ModBlocks;
import net.larson.larsonsmod.block.entity.ModBlockEntities;
import net.larson.larsonsmod.block.entity.renderer.GemPolishingBlockEntityRenderer;
import net.larson.larsonsmod.entity.ModBoats;
import net.larson.larsonsmod.entity.ModEntities;
import net.larson.larsonsmod.entity.client.LavaLampModel;
import net.larson.larsonsmod.entity.client.LavaLampRenderer;
import net.larson.larsonsmod.entity.client.ModModelLayers;
import net.larson.larsonsmod.entity.client.PorcupineModel;
import net.larson.larsonsmod.entity.client.PorcupineRenderer;
import net.larson.larsonsmod.screen.GemPolishingScreen;
import net.larson.larsonsmod.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class LarsonsModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Cutout render layer for cross-model blocks (transparency in textures)
        // In 1.21.6+ the API moved to client.rendering.v1 and uses static putBlock()
        BlockRenderLayerMap.putBlock(ModBlocks.CHESTNUT_SAPLING, RenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.DAHLIA, RenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.POTTED_DAHLIA, RenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.NEON_RED_SAPLING, RenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.NEON_GREEN_SAPLING, RenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.NEON_BLUE_SAPLING, RenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.NEON_CYAN_SAPLING, RenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.NEON_MAGENTA_SAPLING, RenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.NEON_YELLOW_SAPLING, RenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.NEON_GRAY_SAPLING, RenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.RUBY_DOOR, RenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.RUBY_TRAPDOOR, RenderLayer.CUTOUT);

        EntityRendererRegistry.register(ModEntities.PORCUPINE, PorcupineRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.PORCUPINE, PorcupineModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.LAVA_LAMP, LavaLampRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.LAVA_LAMP, LavaLampModel::getTexturedModelData);

        HandledScreens.register(ModScreenHandlers.GEM_POLISHING_SCREEN_HANDLER, GemPolishingScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.GEM_POLISHING_STATION_BLOCK_ENTITY, GemPolishingBlockEntityRenderer::new);

        TerraformBoatClientHelper.registerModelLayers(ModBoats.CHESTNUT_BOAT_ID);

        EntityRendererRegistry.register(ModEntities.DICE_PROJECTILE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SPECIAL_EGG_PROJECTILE, FlyingItemEntityRenderer::new);
    }
}
