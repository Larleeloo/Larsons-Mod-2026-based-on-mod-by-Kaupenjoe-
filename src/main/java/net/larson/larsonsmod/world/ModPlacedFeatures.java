package net.larson.larsonsmod.world;

import net.larson.larsonsmod.LarsonsMod;
import net.larson.larsonsmod.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> RUBY_ORE_PLACED_KEY = registerKey("ruby_ore_placed");
    public static final RegistryKey<PlacedFeature> NETHER_RUBY_ORE_PLACED_KEY = registerKey("nether_ruby_ore_placed");
    public static final RegistryKey<PlacedFeature> END_RUBY_ORE_PLACED_KEY = registerKey("end_ruby_ore_placed");

    public static final RegistryKey<PlacedFeature> CHESTNUT_PLACED_KEY = registerKey("chestnut_placed");

    public static final RegistryKey<PlacedFeature> NEON_RED_TREE_PLACED_KEY = registerKey("neon_red_tree_placed");
    public static final RegistryKey<PlacedFeature> NEON_GREEN_TREE_PLACED_KEY = registerKey("neon_green_tree_placed");
    public static final RegistryKey<PlacedFeature> NEON_BLUE_TREE_PLACED_KEY = registerKey("neon_blue_tree_placed");
    public static final RegistryKey<PlacedFeature> NEON_CYAN_TREE_PLACED_KEY = registerKey("neon_cyan_tree_placed");
    public static final RegistryKey<PlacedFeature> NEON_MAGENTA_TREE_PLACED_KEY = registerKey("neon_magenta_tree_placed");
    public static final RegistryKey<PlacedFeature> NEON_YELLOW_TREE_PLACED_KEY = registerKey("neon_yellow_tree_placed");
    public static final RegistryKey<PlacedFeature> NEON_GRAY_TREE_PLACED_KEY = registerKey("neon_gray_tree_placed");

    // Sparse neon tree placements for meadow/wastes biomes
    public static final RegistryKey<PlacedFeature> NEON_GREEN_TREE_SPARSE_KEY = registerKey("neon_green_tree_sparse");
    public static final RegistryKey<PlacedFeature> NEON_CYAN_TREE_SPARSE_KEY = registerKey("neon_cyan_tree_sparse");
    public static final RegistryKey<PlacedFeature> NEON_GRAY_TREE_SPARSE_KEY = registerKey("neon_gray_tree_sparse");

    // Extra dense neon tree placements for dense forest biome
    public static final RegistryKey<PlacedFeature> NEON_RED_TREE_DENSE_KEY = registerKey("neon_red_tree_dense");
    public static final RegistryKey<PlacedFeature> NEON_GREEN_TREE_DENSE_KEY = registerKey("neon_green_tree_dense");
    public static final RegistryKey<PlacedFeature> NEON_BLUE_TREE_DENSE_KEY = registerKey("neon_blue_tree_dense");
    public static final RegistryKey<PlacedFeature> NEON_CYAN_TREE_DENSE_KEY = registerKey("neon_cyan_tree_dense");
    public static final RegistryKey<PlacedFeature> NEON_MAGENTA_TREE_DENSE_KEY = registerKey("neon_magenta_tree_dense");
    public static final RegistryKey<PlacedFeature> NEON_YELLOW_TREE_DENSE_KEY = registerKey("neon_yellow_tree_dense");
    public static final RegistryKey<PlacedFeature> NEON_GRAY_TREE_DENSE_KEY = registerKey("neon_gray_tree_dense");

    public static void boostrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, RUBY_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.RUBY_ORE_KEY),
                ModOrePlacement.modifiersWithCount(12, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
        register(context, NETHER_RUBY_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.NETHER_RUBY_ORE_KEY),
                ModOrePlacement.modifiersWithCount(12, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
        register(context, END_RUBY_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.END_RUBY_ORE_KEY),
                ModOrePlacement.modifiersWithCount(12, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));

        register(context, CHESTNUT_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.CHESTNUT_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(2, 0.1f, 2),
                        ModBlocks.CHESTNUT_SAPLING));

        // Neon trees - dense placement (3 base + 0.5 chance of 2 extra = dense forest)
        registerNeonTreePlaced(context, NEON_RED_TREE_PLACED_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_RED_TREE_KEY);
        registerNeonTreePlaced(context, NEON_GREEN_TREE_PLACED_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_GREEN_TREE_KEY);
        registerNeonTreePlaced(context, NEON_BLUE_TREE_PLACED_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_BLUE_TREE_KEY);
        registerNeonTreePlaced(context, NEON_CYAN_TREE_PLACED_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_CYAN_TREE_KEY);
        registerNeonTreePlaced(context, NEON_MAGENTA_TREE_PLACED_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_MAGENTA_TREE_KEY);
        registerNeonTreePlaced(context, NEON_YELLOW_TREE_PLACED_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_YELLOW_TREE_KEY);
        registerNeonTreePlaced(context, NEON_GRAY_TREE_PLACED_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_GRAY_TREE_KEY);

        // Sparse placements (1 base + 0.1 chance of 1 extra) for meadows/wastes
        registerNeonTreeSparse(context, NEON_GREEN_TREE_SPARSE_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_GREEN_TREE_KEY);
        registerNeonTreeSparse(context, NEON_CYAN_TREE_SPARSE_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_CYAN_TREE_KEY);
        registerNeonTreeSparse(context, NEON_GRAY_TREE_SPARSE_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_GRAY_TREE_KEY);

        // Dense placements (6 base + 0.5 chance of 3 extra) for dense forest
        registerNeonTreeDense(context, NEON_RED_TREE_DENSE_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_RED_TREE_KEY);
        registerNeonTreeDense(context, NEON_GREEN_TREE_DENSE_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_GREEN_TREE_KEY);
        registerNeonTreeDense(context, NEON_BLUE_TREE_DENSE_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_BLUE_TREE_KEY);
        registerNeonTreeDense(context, NEON_CYAN_TREE_DENSE_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_CYAN_TREE_KEY);
        registerNeonTreeDense(context, NEON_MAGENTA_TREE_DENSE_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_MAGENTA_TREE_KEY);
        registerNeonTreeDense(context, NEON_YELLOW_TREE_DENSE_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_YELLOW_TREE_KEY);
        registerNeonTreeDense(context, NEON_GRAY_TREE_DENSE_KEY, configuredFeatureRegistryEntryLookup, ModConfiguredFeatures.NEON_GRAY_TREE_KEY);
    }

    private static void registerNeonTreePlaced(Registerable<PlacedFeature> context,
                                                RegistryKey<PlacedFeature> placedKey,
                                                net.minecraft.registry.RegistryEntryLookup<ConfiguredFeature<?, ?>> lookup,
                                                RegistryKey<ConfiguredFeature<?, ?>> configuredKey) {
        register(context, placedKey, lookup.getOrThrow(configuredKey),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(3, 0.5f, 2),
                        Blocks.OAK_SAPLING));
    }

    private static void registerNeonTreeSparse(Registerable<PlacedFeature> context,
                                                RegistryKey<PlacedFeature> placedKey,
                                                net.minecraft.registry.RegistryEntryLookup<ConfiguredFeature<?, ?>> lookup,
                                                RegistryKey<ConfiguredFeature<?, ?>> configuredKey) {
        register(context, placedKey, lookup.getOrThrow(configuredKey),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(1, 0.1f, 1),
                        Blocks.OAK_SAPLING));
    }

    private static void registerNeonTreeDense(Registerable<PlacedFeature> context,
                                               RegistryKey<PlacedFeature> placedKey,
                                               net.minecraft.registry.RegistryEntryLookup<ConfiguredFeature<?, ?>> lookup,
                                               RegistryKey<ConfiguredFeature<?, ?>> configuredKey) {
        register(context, placedKey, lookup.getOrThrow(configuredKey),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(6, 0.5f, 3),
                        Blocks.OAK_SAPLING));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(LarsonsMod.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
