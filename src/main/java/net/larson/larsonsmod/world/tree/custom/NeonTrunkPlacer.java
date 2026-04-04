package net.larson.larsonsmod.world.tree.custom;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.larson.larsonsmod.world.tree.ModTrunkPlacerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class NeonTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<NeonTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(objectInstance ->
            fillTrunkPlacerFields(objectInstance).apply(objectInstance, NeonTrunkPlacer::new));

    public NeonTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTrunkPlacerTypes.NEON_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer,
                                                 Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        setToDirt(world, replacer, random, startPos.down(), config);

        // Cap total trunk height to keep trees in the 8-16 block range (trunk + foliage)
        int rawHeight = height + random.nextBetween(firstRandomHeight, firstRandomHeight + 2)
                + random.nextBetween(secondRandomHeight - 1, secondRandomHeight + 1);
        int trunkHeight = Math.min(rawHeight, 12);

        List<FoliagePlacer.TreeNode> foliageNodes = new ArrayList<>();

        // Main trunk
        for (int i = 0; i < trunkHeight; i++) {
            getAndSetState(world, replacer, random, startPos.up(i), config);
        }

        // Top foliage node
        foliageNodes.add(new FoliagePlacer.TreeNode(startPos.up(trunkHeight), 0, false));

        // Generate 2-4 major branches at different heights
        int branchCount = 2 + random.nextInt(3);
        int branchSpacing = Math.max(2, (trunkHeight - 3) / branchCount);

        for (int b = 0; b < branchCount; b++) {
            int branchHeight = 3 + b * branchSpacing + random.nextInt(2);
            if (branchHeight >= trunkHeight - 1) break;

            // Pick a random horizontal direction for this branch
            Direction dir = Direction.fromHorizontalQuarterTurns(random.nextInt(4));
            Direction perpendicular = dir.rotateYClockwise();

            int branchLength = 2 + random.nextInt(3);

            for (int j = 1; j <= branchLength; j++) {
                BlockPos branchPos = startPos.up(branchHeight).offset(dir, j);
                // Branches angle upward slightly
                if (j > 1 && random.nextFloat() > 0.4f) {
                    branchPos = branchPos.up(1);
                    branchHeight++;
                }

                Direction.Axis axis = (dir == Direction.NORTH || dir == Direction.SOUTH) ? Direction.Axis.Z : Direction.Axis.X;
                replacer.accept(branchPos, (BlockState) Function.identity().apply(
                        config.trunkProvider.get(random, branchPos).with(PillarBlock.AXIS, axis)));

                // Sub-branches: small offshoots from main branches
                if (j >= 2 && random.nextFloat() > 0.5f) {
                    BlockPos subBranch = branchPos.offset(perpendicular, 1);
                    replacer.accept(subBranch, (BlockState) Function.identity().apply(
                            config.trunkProvider.get(random, subBranch).with(PillarBlock.AXIS,
                                    (perpendicular == Direction.NORTH || perpendicular == Direction.SOUTH) ? Direction.Axis.Z : Direction.Axis.X)));

                    if (random.nextFloat() > 0.5f) {
                        BlockPos subBranch2 = branchPos.offset(perpendicular.getOpposite(), 1);
                        replacer.accept(subBranch2, (BlockState) Function.identity().apply(
                                config.trunkProvider.get(random, subBranch2).with(PillarBlock.AXIS,
                                        (perpendicular == Direction.NORTH || perpendicular == Direction.SOUTH) ? Direction.Axis.Z : Direction.Axis.X)));
                    }
                }
            }

            // Add foliage node at end of each branch
            BlockPos branchEnd = startPos.up(branchHeight).offset(dir, branchLength);
            foliageNodes.add(new FoliagePlacer.TreeNode(branchEnd.up(1), 0, false));
        }

        // Roots: thick base with exposed roots
        for (Direction rootDir : Direction.Type.HORIZONTAL) {
            if (random.nextFloat() > 0.35f) {
                BlockPos rootPos = startPos.offset(rootDir, 1);
                getAndSetState(world, replacer, random, rootPos, config);
                if (random.nextFloat() > 0.5f) {
                    getAndSetState(world, replacer, random, rootPos.up(1), config);
                }
                if (random.nextFloat() > 0.6f) {
                    BlockPos rootExtend = rootPos.offset(rootDir, 1);
                    getAndSetState(world, replacer, random, rootExtend, config);
                }
            }
        }

        return ImmutableList.copyOf(foliageNodes);
    }
}
