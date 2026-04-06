// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class lava_lamp extends EntityModel<Entity> {
	private final ModelPart base;
	private final ModelPart 2;
	private final ModelPart 3;
	private final ModelPart 4;
	private final ModelPart 5;
	private final ModelPart 6;
	private final ModelPart 7;
	public lava_lamp(ModelPart root) {
		this.base = root.getChild("base");
		this.2 = this.base.getChild("2");
		this.3 = this.2.getChild("3");
		this.4 = this.3.getChild("4");
		this.5 = this.4.getChild("5");
		this.6 = this.5.getChild("6");
		this.7 = this.6.getChild("7");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData base = modelPartData.addChild("base", ModelPartBuilder.create().uv(34, 15).cuboid(-5.0F, -1.0F, -4.0F, 9.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData 2 = base.addChild("2", ModelPartBuilder.create().uv(34, 24).cuboid(-4.0F, -3.0F, -3.0F, 7.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData 3 = 2.addChild("3", ModelPartBuilder.create().uv(0, 15).cuboid(-5.0F, -6.0F, -4.0F, 9.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData 4 = 3.addChild("4", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -11.0F, -5.0F, 11.0F, 5.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData 5 = 4.addChild("5", ModelPartBuilder.create().uv(0, 26).cuboid(-5.0F, -14.0F, -4.0F, 9.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData 6 = 5.addChild("6", ModelPartBuilder.create().uv(34, 32).cuboid(-4.0F, -16.0F, -3.0F, 7.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData 7 = 6.addChild("7", ModelPartBuilder.create().uv(0, 37).cuboid(-3.0F, -17.0F, -2.0F, 5.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		base.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}