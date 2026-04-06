package net.larson.larsonsmod.entity.client;

import net.larson.larsonsmod.entity.animation.ModAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

public class LavaLampModel extends EntityModel<LivingEntityRenderState> {
    private final ModelPart base;
    private final Animation idleAnimation;

    public LavaLampModel(ModelPart root) {
        super(root);
        this.base = root.getChild("base");
        this.idleAnimation = ModAnimations.LAVA_LAMP_IDLE.createAnimation(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData base = modelPartData.addChild("base", ModelPartBuilder.create().uv(34, 15).cuboid(-5.0F, -1.0F, -4.0F, 9.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData part2 = base.addChild("2", ModelPartBuilder.create().uv(34, 24).cuboid(-4.0F, -3.0F, -3.0F, 7.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData part3 = part2.addChild("3", ModelPartBuilder.create().uv(0, 15).cuboid(-5.0F, -6.0F, -4.0F, 9.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData part4 = part3.addChild("4", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -11.0F, -5.0F, 11.0F, 5.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData part5 = part4.addChild("5", ModelPartBuilder.create().uv(0, 26).cuboid(-5.0F, -14.0F, -4.0F, 9.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData part6 = part5.addChild("6", ModelPartBuilder.create().uv(34, 32).cuboid(-4.0F, -16.0F, -3.0F, 7.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData part7 = part6.addChild("7", ModelPartBuilder.create().uv(0, 37).cuboid(-3.0F, -17.0F, -2.0F, 5.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(LivingEntityRenderState state) {
        super.setAngles(state);
        this.base.traverse().forEach(ModelPart::resetTransform);
        this.idleAnimation.applyWalking(state.age, 1.0f, 1.0f, 1.0f);
    }
}
