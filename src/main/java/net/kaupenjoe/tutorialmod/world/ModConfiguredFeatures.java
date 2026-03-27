package net.kaupenjoe.tutorialmod.world;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.block.ModBlocks;
import net.kaupenjoe.tutorialmod.world.tree.custom.ChestnutFoliagePlacer;
import net.kaupenjoe.tutorialmod.world.tree.custom.ChestnutTrunkPlacer;
import net.kaupenjoe.tutorialmod.world.tree.custom.NeonFoliagePlacer;
import net.kaupenjoe.tutorialmod.world.tree.custom.NeonTrunkPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> RUBY_ORE_KEY = registerKey("ruby_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NETHER_RUBY_ORE_KEY = registerKey("nether_ruby_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> END_RUBY_ORE_KEY = registerKey("end_ruby_ore");

    public static final RegistryKey<ConfiguredFeature<?, ?>> CHESTNUT_KEY = registerKey("chestnut");

    public static final RegistryKey<ConfiguredFeature<?, ?>> NEON_RED_TREE_KEY = registerKey("neon_red_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NEON_GREEN_TREE_KEY = registerKey("neon_green_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NEON_BLUE_TREE_KEY = registerKey("neon_blue_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NEON_CYAN_TREE_KEY = registerKey("neon_cyan_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NEON_MAGENTA_TREE_KEY = registerKey("neon_magenta_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NEON_YELLOW_TREE_KEY = registerKey("neon_yellow_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NEON_GRAY_TREE_KEY = registerKey("neon_gray_tree");

    public static void boostrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplacables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplacables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplacables = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
        RuleTest endReplacables = new BlockMatchRuleTest(Blocks.END_STONE);

        List<OreFeatureConfig.Target> overworldRubyOres =
                List.of(OreFeatureConfig.createTarget(stoneReplacables, ModBlocks.RUBY_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplacables, ModBlocks.DEEPSLATE_RUBY_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> netherRubyOres =
                List.of(OreFeatureConfig.createTarget(netherReplacables, ModBlocks.NETHER_RUBY_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> endRubyOres =
                List.of(OreFeatureConfig.createTarget(endReplacables, ModBlocks.END_STONE_RUBY_ORE.getDefaultState()));

        register(context, RUBY_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldRubyOres, 12));
        register(context, NETHER_RUBY_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherRubyOres, 12));
        register(context, END_RUBY_ORE_KEY, Feature.ORE, new OreFeatureConfig(endRubyOres, 12));

        register(context, CHESTNUT_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.CHESTNUT_LOG),
                new ChestnutTrunkPlacer(5, 4, 3),

                BlockStateProvider.of(ModBlocks.CHESTNUT_LEAVES),
                new ChestnutFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2),

                new TwoLayersFeatureSize(1, 0, 2)).build());

        // Neon Trees - each color has its own configured feature
        registerNeonTree(context, NEON_RED_TREE_KEY, ModBlocks.NEON_RED_WOOD, ModBlocks.NEON_RED_LEAVES);
        registerNeonTree(context, NEON_GREEN_TREE_KEY, ModBlocks.NEON_GREEN_WOOD, ModBlocks.NEON_GREEN_LEAVES);
        registerNeonTree(context, NEON_BLUE_TREE_KEY, ModBlocks.NEON_BLUE_WOOD, ModBlocks.NEON_BLUE_LEAVES);
        registerNeonTree(context, NEON_CYAN_TREE_KEY, ModBlocks.NEON_CYAN_WOOD, ModBlocks.NEON_CYAN_LEAVES);
        registerNeonTree(context, NEON_MAGENTA_TREE_KEY, ModBlocks.NEON_MAGENTA_WOOD, ModBlocks.NEON_MAGENTA_LEAVES);
        registerNeonTree(context, NEON_YELLOW_TREE_KEY, ModBlocks.NEON_YELLOW_WOOD, ModBlocks.NEON_YELLOW_LEAVES);
        registerNeonTree(context, NEON_GRAY_TREE_KEY, ModBlocks.NEON_GRAY_WOOD, ModBlocks.NEON_GRAY_LEAVES);
    }

    private static void registerNeonTree(Registerable<ConfiguredFeature<?, ?>> context,
                                         RegistryKey<ConfiguredFeature<?, ?>> key, Block wood, Block leaves) {
        register(context, key, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(wood),
                new NeonTrunkPlacer(4, 2, 2),
                BlockStateProvider.of(leaves),
                new NeonFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(1), 3),
                new TwoLayersFeatureSize(1, 0, 2))
                .dirtProvider(BlockStateProvider.of(Blocks.DIRT))
                .build());
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(TutorialMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
