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
        // Dense, layered canopy - multiple layers with varying radii for a full look
        // Bottom layers: wider spread
        generateSquare(world, placer, random, config, treeNode.getCenter().down(1), 3, offset, treeNode.isGiantTrunk());
        generateSquare(world, placer, random, config, treeNode.getCenter(), 3, offset, treeNode.isGiantTrunk());
        generateSquare(world, placer, random, config, treeNode.getCenter().up(1), 2, offset, treeNode.isGiantTrunk());
        generateSquare(world, placer, random, config, treeNode.getCenter().up(2), 2, offset, treeNode.isGiantTrunk());
        // Top: narrower crown
        generateSquare(world, placer, random, config, treeNode.getCenter().up(3), 1, offset, treeNode.isGiantTrunk());
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return this.height;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        // Only skip corners of the widest layers for a more natural shape
        if (radius >= 3) {
            return dx == radius && dz == radius && random.nextFloat() < 0.4f;
        }
        return false;
    }
}
