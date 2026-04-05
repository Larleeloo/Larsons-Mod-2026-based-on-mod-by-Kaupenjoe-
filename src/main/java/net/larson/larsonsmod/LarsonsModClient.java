package net.larson.larsonsmod;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.larson.larsonsmod.block.ModBlocks;
import net.larson.larsonsmod.block.entity.ModBlockEntities;
import net.larson.larsonsmod.block.entity.renderer.GemPolishingBlockEntityRenderer;
import net.larson.larsonsmod.entity.ModBoats;
import net.larson.larsonsmod.entity.ModEntities;
import net.larson.larsonsmod.entity.client.ModModelLayers;
import net.larson.larsonsmod.entity.client.PorcupineModel;
import net.larson.larsonsmod.entity.client.PorcupineRenderer;
import net.larson.larsonsmod.screen.GemPolishingScreen;
import net.larson.larsonsmod.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class LarsonsModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // BlockRenderLayerMap was removed in 1.21.5+
        // Render layers are now handled via block model JSON files (render_type field)
        // or automatically through block properties like .nonOpaque()
        // Blocks with .nonOpaque() in their settings will render correctly.

        EntityRendererRegistry.register(ModEntities.PORCUPINE, PorcupineRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.PORCUPINE, PorcupineModel::getTexturedModelData);

        HandledScreens.register(ModScreenHandlers.GEM_POLISHING_SCREEN_HANDLER, GemPolishingScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.GEM_POLISHING_STATION_BLOCK_ENTITY, GemPolishingBlockEntityRenderer::new);

        TerraformBoatClientHelper.registerModelLayers(ModBoats.CHESTNUT_BOAT_ID);

        EntityRendererRegistry.register(ModEntities.DICE_PROJECTILE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SPECIAL_EGG_PROJECTILE, FlyingItemEntityRenderer::new);
    }
}
