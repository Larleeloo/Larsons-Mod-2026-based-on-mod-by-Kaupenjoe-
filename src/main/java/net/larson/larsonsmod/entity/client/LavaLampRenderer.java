package net.larson.larsonsmod.entity.client;

import net.larson.larsonsmod.LarsonsMod;
import net.larson.larsonsmod.entity.custom.LavaLampEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;

public class LavaLampRenderer extends MobEntityRenderer<LavaLampEntity, LivingEntityRenderState, LavaLampModel> {
    private static final Identifier TEXTURE = Identifier.of(LarsonsMod.MOD_ID, "textures/entity/lava_lamp.png");

    public LavaLampRenderer(EntityRendererFactory.Context context) {
        super(context, new LavaLampModel(context.getPart(ModModelLayers.LAVA_LAMP)), 0.4f);
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}
