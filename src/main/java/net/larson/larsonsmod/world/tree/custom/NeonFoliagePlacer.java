package net.larson.larsonsmod.world.tree.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.larson.larsonsmod.world.tree.ModFoliagePlacerTypes;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class NeonFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<NeonFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            fillFoliagePlacerFields(instance).and(com.mojang.serialization.Codec.intRange(0, 16).fieldOf("height")
                    .forGetter(fp -> fp.height)).apply(instance, NeonFoliagePlacer::new));
    private final int height;

    public NeonFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
        super(radius, offset);
        this.height = height;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ModFoliagePlacerTypes.NEON_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config,
                            int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        // Pick foliage variant based on trunk height for per-tree consistency
        int variant = trunkHeight % 4;

        switch (variant) {
            case 0 -> generateLayered(world, placer, random, config, treeNode, offset);
            case 1 -> generateSphere(world, placer, random, config, treeNode, offset);
            case 2 -> generateCone(world, placer, random, config, treeNode, offset);
            case 3 -> generateSparseCloud(world, placer, random, config, treeNode, offset);
        }
    }

    // =============================================
    // Variant 0: Dense Layered Canopy (original style)
    // Multiple stacked square layers of decreasing radius
    // =============================================
    private void generateLayered(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config,
                                  TreeNode treeNode, int offset) {
        generateSquare(world, placer, random, config, treeNode.getCenter(), 3, -1, treeNode.isGiantTrunk());
        generateSquare(world, placer, random, config, treeNode.getCenter(), 3, 0, treeNode.isGiantTrunk());
        generateSquare(world, placer, random, config, treeNode.getCenter(), 2, 1, treeNode.isGiantTrunk());
        generateSquare(world, placer, random, config, treeNode.getCenter(), 2, 2, treeNode.isGiantTrunk());
        generateSquare(world, placer, random, config, treeNode.getCenter(), 1, 3, treeNode.isGiantTrunk());
    }

    // =============================================
    // Variant 1: Sphere Canopy
    // Rounded 3D globe shape using variable radii per Y layer
    // =============================================
    private void generateSphere(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config,
                                 TreeNode treeNode, int offset) {
        int r = 2 + (random.nextInt(2)); // radius 2-3

        for (int dy = -r; dy <= r; dy++) {
            double effectiveR = Math.sqrt(r * r - dy * dy);
            int layerRadius = (int) Math.round(effectiveR);
            if (layerRadius >= 0) {
                generateSquare(world, placer, random, config, treeNode.getCenter(), layerRadius, dy, treeNode.isGiantTrunk());
            }
        }
    }

    // =============================================
    // Variant 2: Cone / Pyramid Canopy
    // Conical shape - narrow at top, wide at base, like a spruce/fir tree
    // =============================================
    private void generateCone(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config,
                               TreeNode treeNode, int offset) {
        int coneHeight = 5 + random.nextInt(2); // 5-6 layers
        int baseRadius = 3;

        // Tip at top (center), widening downward
        for (int dy = 0; dy < coneHeight; dy++) {
            int layerRadius = (dy * baseRadius) / (coneHeight - 1);
            generateSquare(world, placer, random, config, treeNode.getCenter(), layerRadius, -dy, treeNode.isGiantTrunk());
        }
        // Cap at the very top
        generateSquare(world, placer, random, config, treeNode.getCenter(), 0, 1, treeNode.isGiantTrunk());
    }

    // =============================================
    // Variant 3: Sparse Cloud Canopy
    // Wide, airy patches of leaves with visible gaps - ethereal look
    // =============================================
    private void generateSparseCloud(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config,
                                      TreeNode treeNode, int offset) {
        // Main thin canopy layer - wide but only 2 blocks tall
        generateSquare(world, placer, random, config, treeNode.getCenter(), 4, 0, treeNode.isGiantTrunk());
        generateSquare(world, placer, random, config, treeNode.getCenter(), 3, 1, treeNode.isGiantTrunk());

        // Scattered small clusters around the node for an airy look
        for (int i = 0; i < 3 + random.nextInt(3); i++) {
            int dx = random.nextInt(5) - 2;
            int dz = random.nextInt(5) - 2;
            int dy = random.nextInt(3) - 1;
            generateSquare(world, placer, random, config, treeNode.getCenter().add(dx, 0, dz), 1, dy, treeNode.isGiantTrunk());
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return this.height;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        // Skip corners on wider layers for organic shape
        if (radius >= 4) {
            return (Math.abs(dx) + Math.abs(dz) > radius + 1) || (dx == radius && dz == radius);
        }
        if (radius >= 3) {
            return dx == radius && dz == radius && random.nextFloat() < 0.4f;
        }
        return false;
    }
}
