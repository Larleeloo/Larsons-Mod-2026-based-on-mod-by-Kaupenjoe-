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

        int variant = random.nextInt(5);

        return switch (variant) {
            case 0 -> generateBranching(world, replacer, random, height, startPos, config);
            case 1 -> generateSpire(world, replacer, random, height, startPos, config);
            case 2 -> generateMultiFork(world, replacer, random, height, startPos, config);
            case 3 -> generateTwisted(world, replacer, random, height, startPos, config);
            case 4 -> generateUmbrella(world, replacer, random, height, startPos, config);
            default -> generateBranching(world, replacer, random, height, startPos, config);
        };
    }

    private static Direction.Axis getAxisForDirection(Direction dir) {
        return switch (dir) {
            case NORTH, SOUTH -> Direction.Axis.Z;
            case EAST, WEST -> Direction.Axis.X;
            default -> Direction.Axis.Y;
        };
    }

    private void placeBranch(BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos,
                             TreeFeatureConfig config, Direction.Axis axis) {
        replacer.accept(pos, config.trunkProvider.get(random, pos).with(PillarBlock.AXIS, axis));
    }

    // =============================================
    // Variant 0: Standard Branching Tree
    // Medium height trunk with 2-4 side branches, sub-branches, and exposed roots
    // =============================================
    private List<FoliagePlacer.TreeNode> generateBranching(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer,
                                                            Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        int rawHeight = height + random.nextBetween(firstRandomHeight, firstRandomHeight + 2)
                + random.nextBetween(secondRandomHeight - 1, secondRandomHeight + 1);
        int trunkHeight = Math.min(rawHeight, 12);

        List<FoliagePlacer.TreeNode> foliageNodes = new ArrayList<>();

        for (int i = 0; i < trunkHeight; i++) {
            getAndSetState(world, replacer, random, startPos.up(i), config);
        }

        foliageNodes.add(new FoliagePlacer.TreeNode(startPos.up(trunkHeight), 0, false));

        int branchCount = 2 + random.nextInt(3);
        int branchSpacing = Math.max(2, (trunkHeight - 3) / branchCount);

        for (int b = 0; b < branchCount; b++) {
            int branchHeight = 3 + b * branchSpacing + random.nextInt(2);
            if (branchHeight >= trunkHeight - 1) break;

            Direction dir = Direction.fromHorizontalQuarterTurns(random.nextInt(4));
            Direction perpendicular = dir.rotateYClockwise();
            int branchLength = 2 + random.nextInt(3);

            for (int j = 1; j <= branchLength; j++) {
                BlockPos branchPos = startPos.up(branchHeight).offset(dir, j);
                if (j > 1 && random.nextFloat() > 0.4f) {
                    branchPos = branchPos.up(1);
                    branchHeight++;
                }

                placeBranch(replacer, random, branchPos, config, getAxisForDirection(dir));

                if (j >= 2 && random.nextFloat() > 0.5f) {
                    BlockPos subBranch = branchPos.offset(perpendicular, 1);
                    placeBranch(replacer, random, subBranch, config, getAxisForDirection(perpendicular));

                    if (random.nextFloat() > 0.5f) {
                        BlockPos subBranch2 = branchPos.offset(perpendicular.getOpposite(), 1);
                        placeBranch(replacer, random, subBranch2, config, getAxisForDirection(perpendicular));
                    }
                }
            }

            BlockPos branchEnd = startPos.up(branchHeight).offset(dir, branchLength);
            foliageNodes.add(new FoliagePlacer.TreeNode(branchEnd.up(1), 0, false));
        }

        // Exposed roots
        for (Direction rootDir : Direction.Type.HORIZONTAL) {
            if (random.nextFloat() > 0.35f) {
                BlockPos rootPos = startPos.offset(rootDir, 1);
                getAndSetState(world, replacer, random, rootPos, config);
                if (random.nextFloat() > 0.5f) {
                    getAndSetState(world, replacer, random, rootPos.up(1), config);
                }
                if (random.nextFloat() > 0.6f) {
                    getAndSetState(world, replacer, random, rootPos.offset(rootDir, 1), config);
                }
            }
        }

        return ImmutableList.copyOf(foliageNodes);
    }

    // =============================================
    // Variant 1: Tall Spire Tree
    // Very tall thin trunk with small nubs, conifer-like silhouette
    // =============================================
    private List<FoliagePlacer.TreeNode> generateSpire(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer,
                                                        Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        int trunkHeight = 12 + random.nextInt(7); // 12-18 blocks tall
        List<FoliagePlacer.TreeNode> foliageNodes = new ArrayList<>();

        for (int i = 0; i < trunkHeight; i++) {
            getAndSetState(world, replacer, random, startPos.up(i), config);

            // Small 1-block nubs alternating sides
            if (i >= 3 && i < trunkHeight - 2 && i % 2 == 0) {
                Direction dir = Direction.fromHorizontalQuarterTurns((i / 2) % 4);
                BlockPos nub = startPos.up(i).offset(dir, 1);
                getAndSetState(world, replacer, random, nub, config);

                // Occasional 2-block nub
                if (random.nextFloat() > 0.6f) {
                    BlockPos nub2 = nub.offset(dir, 1);
                    placeBranch(replacer, random, nub2, config, getAxisForDirection(dir));
                }

                // Add foliage at nubs in upper half
                if (i > trunkHeight / 2) {
                    foliageNodes.add(new FoliagePlacer.TreeNode(nub.up(1), 0, false));
                }
            }
        }

        // Top foliage node
        foliageNodes.add(new FoliagePlacer.TreeNode(startPos.up(trunkHeight), 0, false));

        return ImmutableList.copyOf(foliageNodes);
    }

    // =============================================
    // Variant 2: Multi-Fork / Candelabra Tree
    // Short base that splits into 2-3 separate trunks rising upward
    // =============================================
    private List<FoliagePlacer.TreeNode> generateMultiFork(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer,
                                                            Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        int baseHeight = 3 + random.nextInt(3); // 3-5 blocks base
        List<FoliagePlacer.TreeNode> foliageNodes = new ArrayList<>();

        // Main base trunk
        for (int i = 0; i < baseHeight; i++) {
            getAndSetState(world, replacer, random, startPos.up(i), config);
        }

        // Fork into 2-3 arms
        int armCount = 2 + random.nextInt(2);
        List<Direction> usedDirs = new ArrayList<>();

        for (int a = 0; a < armCount; a++) {
            Direction dir;
            int attempts = 0;
            do {
                dir = Direction.fromHorizontalQuarterTurns(random.nextInt(4));
                attempts++;
            } while (usedDirs.contains(dir) && attempts < 8);
            usedDirs.add(dir);

            int armHeight = 4 + random.nextInt(4); // 4-7 blocks per arm
            BlockPos armPos = startPos.up(baseHeight);

            // First 1-2 blocks: spread outward
            for (int i = 0; i < 2; i++) {
                armPos = armPos.offset(dir, 1).up(1);
                placeBranch(replacer, random, armPos, config, getAxisForDirection(dir));
            }

            // Rest goes mostly up
            for (int i = 2; i < armHeight; i++) {
                armPos = armPos.up(1);
                if (random.nextFloat() > 0.7f) {
                    armPos = armPos.offset(dir, 1);
                }
                getAndSetState(world, replacer, random, armPos, config);
            }

            foliageNodes.add(new FoliagePlacer.TreeNode(armPos.up(1), 0, false));
        }

        // Small roots
        for (Direction rootDir : Direction.Type.HORIZONTAL) {
            if (random.nextFloat() > 0.5f) {
                getAndSetState(world, replacer, random, startPos.offset(rootDir, 1), config);
            }
        }

        return ImmutableList.copyOf(foliageNodes);
    }

    // =============================================
    // Variant 3: Twisted / Zigzag Tree
    // Trunk winds and shifts horizontally as it grows, creating an organic shape
    // =============================================
    private List<FoliagePlacer.TreeNode> generateTwisted(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer,
                                                          Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        int trunkHeight = 8 + random.nextInt(5); // 8-12 blocks
        List<FoliagePlacer.TreeNode> foliageNodes = new ArrayList<>();

        BlockPos current = startPos;
        Direction currentDir = Direction.fromHorizontalQuarterTurns(random.nextInt(4));
        int nextTurn = 2 + random.nextInt(2); // blocks until next direction change

        for (int i = 0; i < trunkHeight; i++) {
            getAndSetState(world, replacer, random, current, config);

            // Direction change at intervals
            if (i > 0 && i == nextTurn && i < trunkHeight - 2) {
                // Shift horizontally
                currentDir = random.nextFloat() > 0.5f
                        ? currentDir.rotateYClockwise()
                        : currentDir.rotateYCounterclockwise();
                current = current.offset(currentDir, 1);
                placeBranch(replacer, random, current, config, getAxisForDirection(currentDir));
                nextTurn = i + 2 + random.nextInt(2);

                // Small branch at turn point
                if (random.nextFloat() > 0.4f) {
                    Direction branchDir = currentDir.rotateYClockwise();
                    BlockPos branchPos = current.offset(branchDir, 1);
                    placeBranch(replacer, random, branchPos, config, getAxisForDirection(branchDir));

                    if (random.nextFloat() > 0.5f) {
                        BlockPos branchPos2 = branchPos.offset(branchDir, 1);
                        placeBranch(replacer, random, branchPos2, config, getAxisForDirection(branchDir));
                        if (i > trunkHeight / 2) {
                            foliageNodes.add(new FoliagePlacer.TreeNode(branchPos2.up(1), 0, false));
                        }
                    }
                }
            }

            current = current.up(1);
        }

        foliageNodes.add(new FoliagePlacer.TreeNode(current, 0, false));

        // Gnarled roots
        for (Direction rootDir : Direction.Type.HORIZONTAL) {
            if (random.nextFloat() > 0.4f) {
                BlockPos rootPos = startPos.offset(rootDir, 1);
                getAndSetState(world, replacer, random, rootPos, config);
                if (random.nextFloat() > 0.6f) {
                    getAndSetState(world, replacer, random, rootPos.down(1), config);
                }
            }
        }

        return ImmutableList.copyOf(foliageNodes);
    }

    // =============================================
    // Variant 4: Broad Umbrella Tree
    // Short thick trunk with wide spreading horizontal branches at the canopy level
    // =============================================
    private List<FoliagePlacer.TreeNode> generateUmbrella(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer,
                                                           Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        int trunkHeight = 4 + random.nextInt(3); // 4-6 blocks
        List<FoliagePlacer.TreeNode> foliageNodes = new ArrayList<>();

        // Thick 2x1 base for first 2 blocks (adds girth)
        Direction thickDir = Direction.fromHorizontalQuarterTurns(random.nextInt(4));
        for (int i = 0; i < Math.min(2, trunkHeight); i++) {
            getAndSetState(world, replacer, random, startPos.up(i), config);
            getAndSetState(world, replacer, random, startPos.up(i).offset(thickDir, 1), config);
        }

        // Single trunk above
        for (int i = 2; i < trunkHeight; i++) {
            getAndSetState(world, replacer, random, startPos.up(i), config);
        }

        // Long horizontal branches radiating outward at the top
        for (Direction dir : Direction.Type.HORIZONTAL) {
            if (random.nextFloat() > 0.15f) { // ~85% chance per direction
                int branchLen = 3 + random.nextInt(3); // 3-5 blocks out
                int branchY = trunkHeight - 1;
                BlockPos branchEnd = startPos.up(branchY);

                for (int j = 1; j <= branchLen; j++) {
                    branchEnd = startPos.up(branchY).offset(dir, j);
                    // Slight upward curve at the ends
                    if (j == branchLen && random.nextFloat() > 0.4f) {
                        branchEnd = branchEnd.up(1);
                    }
                    placeBranch(replacer, random, branchEnd, config, getAxisForDirection(dir));
                }

                foliageNodes.add(new FoliagePlacer.TreeNode(branchEnd.up(1), 0, false));
            }
        }

        // Central top foliage
        foliageNodes.add(new FoliagePlacer.TreeNode(startPos.up(trunkHeight), 0, false));

        // Buttress roots
        for (Direction rootDir : Direction.Type.HORIZONTAL) {
            if (random.nextFloat() > 0.5f) {
                BlockPos rootPos = startPos.offset(rootDir, 1);
                getAndSetState(world, replacer, random, rootPos, config);
                if (random.nextFloat() > 0.7f) {
                    getAndSetState(world, replacer, random, rootPos.offset(rootDir, 1), config);
                }
            }
        }

        return ImmutableList.copyOf(foliageNodes);
    }
}
