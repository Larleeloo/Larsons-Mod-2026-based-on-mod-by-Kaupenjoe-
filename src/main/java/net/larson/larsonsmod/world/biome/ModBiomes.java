package net.larson.larsonsmod.world.biome;

import net.larson.larsonsmod.LarsonsMod;
import net.larson.larsonsmod.entity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.larson.larsonsmod.world.ModPlacedFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class ModBiomes {
    public static final RegistryKey<Biome> TEST_BIOME = RegistryKey.of(RegistryKeys.BIOME,
            Identifier.of(LarsonsMod.MOD_ID, "test_biome"));

    // Neon Dimension Biomes
    public static final RegistryKey<Biome> NEON_ROLLING_HILLS = RegistryKey.of(RegistryKeys.BIOME,
            Identifier.of(LarsonsMod.MOD_ID, "neon_rolling_hills"));
    public static final RegistryKey<Biome> NEON_FLOATING_ISLANDS = RegistryKey.of(RegistryKeys.BIOME,
            Identifier.of(LarsonsMod.MOD_ID, "neon_floating_islands"));
    public static final RegistryKey<Biome> NEON_DENSE_FOREST = RegistryKey.of(RegistryKeys.BIOME,
            Identifier.of(LarsonsMod.MOD_ID, "neon_dense_forest"));
    public static final RegistryKey<Biome> NEON_TWILIGHT_MEADOWS = RegistryKey.of(RegistryKeys.BIOME,
            Identifier.of(LarsonsMod.MOD_ID, "neon_twilight_meadows"));
    public static final RegistryKey<Biome> NEON_CRYSTAL_WASTES = RegistryKey.of(RegistryKeys.BIOME,
            Identifier.of(LarsonsMod.MOD_ID, "neon_crystal_wastes"));

    public static void boostrap(Registerable<Biome> context) {
        context.register(TEST_BIOME, testBiome(context));
        context.register(NEON_ROLLING_HILLS, neonRollingHills(context));
        context.register(NEON_FLOATING_ISLANDS, neonFloatingIslands(context));
        context.register(NEON_DENSE_FOREST, neonDenseForest(context));
        context.register(NEON_TWILIGHT_MEADOWS, neonTwilightMeadows(context));
        context.register(NEON_CRYSTAL_WASTES, neonCrystalWastes(context));
    }

    public static void globalOverworldGeneration(GenerationSettings.LookupBackedBuilder builder) {
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addAmethystGeodes(builder);
        DefaultBiomeFeatures.addDungeons(builder);
        DefaultBiomeFeatures.addMineables(builder);
        DefaultBiomeFeatures.addSprings(builder);
        DefaultBiomeFeatures.addFrozenTopLayer(builder);
    }

    public static void neonDimensionBaseGeneration(GenerationSettings.LookupBackedBuilder builder) {
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addAmethystGeodes(builder);
        DefaultBiomeFeatures.addDungeons(builder);
        DefaultBiomeFeatures.addMineables(builder);
    }

    // ========================
    // NEON ROLLING HILLS - Warm colors (red, yellow, magenta) on gentle terrain
    // ========================
    public static Biome neonRollingHills(Registerable<Biome> context) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.CREATURE, 10, new SpawnSettings.SpawnEntry(ModEntities.PORCUPINE, 2, 4));
        DefaultBiomeFeatures.addFarmAnimals(spawnBuilder);
        DefaultBiomeFeatures.addMonsters(spawnBuilder, 95, 5, 0, 100, false);
        DefaultBiomeFeatures.addCaveMobs(spawnBuilder);

        GenerationSettings.LookupBackedBuilder biomeBuilder =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        neonDimensionBaseGeneration(biomeBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeBuilder);

        // Warm-colored neon trees at moderate density
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_RED_TREE_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_YELLOW_TREE_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_MAGENTA_TREE_PLACED_KEY);
        DefaultBiomeFeatures.addForestFlowers(biomeBuilder);
        DefaultBiomeFeatures.addDefaultVegetation(biomeBuilder, true);

        return new Biome.Builder()
                .precipitation(true)
                .downfall(0.3f)
                .temperature(0.8f)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects((new BiomeEffects.Builder())
                        .waterColor(0xff3366)
                        .grassColor(0x000000)
                        .foliageColor(0xff3399)
                        .build())
                .build();
    }

    // ========================
    // NEON FLOATING ISLANDS - Cool colors (blue, cyan) ethereal floating terrain
    // ========================
    public static Biome neonFloatingIslands(Registerable<Biome> context) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.CREATURE, 5, new SpawnSettings.SpawnEntry(ModEntities.PORCUPINE, 1, 2));
        DefaultBiomeFeatures.addMonsters(spawnBuilder, 95, 5, 0, 100, false);
        DefaultBiomeFeatures.addCaveMobs(spawnBuilder);

        GenerationSettings.LookupBackedBuilder biomeBuilder =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        neonDimensionBaseGeneration(biomeBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeBuilder);

        // Cool-colored neon trees, moderate density
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_BLUE_TREE_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_CYAN_TREE_PLACED_KEY);
        DefaultBiomeFeatures.addDefaultVegetation(biomeBuilder, true);

        return new Biome.Builder()
                .precipitation(false)
                .downfall(0.0f)
                .temperature(0.5f)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects((new BiomeEffects.Builder())
                        .waterColor(0x00ccff)
                        .grassColor(0x000000)
                        .foliageColor(0x00ffff)
                        .build())
                .build();
    }

    // ========================
    // NEON DENSE FOREST - All 7 colors, extremely thick tree coverage
    // ========================
    public static Biome neonDenseForest(Registerable<Biome> context) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.CREATURE, 10, new SpawnSettings.SpawnEntry(ModEntities.PORCUPINE, 3, 6));
        spawnBuilder.spawn(SpawnGroup.CREATURE, 8, new SpawnSettings.SpawnEntry(EntityType.WOLF, 2, 4));
        DefaultBiomeFeatures.addFarmAnimals(spawnBuilder);
        DefaultBiomeFeatures.addMonsters(spawnBuilder, 95, 5, 0, 100, false);
        DefaultBiomeFeatures.addCaveMobs(spawnBuilder);

        GenerationSettings.LookupBackedBuilder biomeBuilder =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        neonDimensionBaseGeneration(biomeBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeBuilder);

        // All 7 colors at extra dense placement
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_RED_TREE_DENSE_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_GREEN_TREE_DENSE_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_BLUE_TREE_DENSE_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_CYAN_TREE_DENSE_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_MAGENTA_TREE_DENSE_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_YELLOW_TREE_DENSE_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_GRAY_TREE_DENSE_KEY);
        DefaultBiomeFeatures.addForestFlowers(biomeBuilder);
        DefaultBiomeFeatures.addLargeFerns(biomeBuilder);
        DefaultBiomeFeatures.addDefaultMushrooms(biomeBuilder);
        DefaultBiomeFeatures.addDefaultVegetation(biomeBuilder, true);

        return new Biome.Builder()
                .precipitation(true)
                .downfall(0.6f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects((new BiomeEffects.Builder())
                        .waterColor(0x9933ff)
                        .grassColor(0x000000)
                        .foliageColor(0xff00cc)
                        .build())
                .build();
    }

    // ========================
    // NEON TWILIGHT MEADOWS - Open fields with sparse green/cyan trees
    // ========================
    public static Biome neonTwilightMeadows(Registerable<Biome> context) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.CREATURE, 8, new SpawnSettings.SpawnEntry(ModEntities.PORCUPINE, 2, 3));
        DefaultBiomeFeatures.addFarmAnimals(spawnBuilder);
        DefaultBiomeFeatures.addMonsters(spawnBuilder, 95, 5, 0, 100, false);
        DefaultBiomeFeatures.addCaveMobs(spawnBuilder);

        GenerationSettings.LookupBackedBuilder biomeBuilder =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        neonDimensionBaseGeneration(biomeBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeBuilder);

        // Sparse green and cyan trees
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_GREEN_TREE_SPARSE_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_CYAN_TREE_SPARSE_KEY);
        DefaultBiomeFeatures.addForestFlowers(biomeBuilder);
        DefaultBiomeFeatures.addForestGrass(biomeBuilder);
        DefaultBiomeFeatures.addDefaultVegetation(biomeBuilder, true);

        return new Biome.Builder()
                .precipitation(true)
                .downfall(0.4f)
                .temperature(0.6f)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects((new BiomeEffects.Builder())
                        .waterColor(0x33ff99)
                        .grassColor(0x000000)
                        .foliageColor(0x33ffcc)
                        .build())
                .build();
    }

    // ========================
    // NEON CRYSTAL WASTES - Barren, otherworldly terrain with sparse gray neon trees
    // ========================
    public static Biome neonCrystalWastes(Registerable<Biome> context) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addMonsters(spawnBuilder, 95, 5, 0, 100, false);
        DefaultBiomeFeatures.addCaveMobs(spawnBuilder);

        GenerationSettings.LookupBackedBuilder biomeBuilder =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        neonDimensionBaseGeneration(biomeBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeBuilder);

        // Only sparse gray trees in this desolate landscape
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_GRAY_TREE_SPARSE_KEY);

        return new Biome.Builder()
                .precipitation(false)
                .downfall(0.0f)
                .temperature(0.3f)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects((new BiomeEffects.Builder())
                        .waterColor(0x8899aa)
                        .grassColor(0x000000)
                        .foliageColor(0xaabbdd)
                        .build())
                .build();
    }

    public static Biome testBiome(Registerable<Biome> context) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.CREATURE, 10, new SpawnSettings.SpawnEntry(ModEntities.PORCUPINE, 3, 5));

        spawnBuilder.spawn(SpawnGroup.CREATURE, 10, new SpawnSettings.SpawnEntry(EntityType.WOLF, 4, 4));

        DefaultBiomeFeatures.addFarmAnimals(spawnBuilder);
        DefaultBiomeFeatures.addMonsters(spawnBuilder, 95, 5, 0, 100, false);
        DefaultBiomeFeatures.addCaveMobs(spawnBuilder);

        GenerationSettings.LookupBackedBuilder biomeBuilder =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        globalOverworldGeneration(biomeBuilder);
        DefaultBiomeFeatures.addMossyRocks(biomeBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeBuilder);
        DefaultBiomeFeatures.addExtraGoldOre(biomeBuilder);

        // Dense neon forest - all 7 colors of neon trees
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_RED_TREE_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_GREEN_TREE_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_BLUE_TREE_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_CYAN_TREE_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_MAGENTA_TREE_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_YELLOW_TREE_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NEON_GRAY_TREE_PLACED_KEY);
        DefaultBiomeFeatures.addForestFlowers(biomeBuilder);
        DefaultBiomeFeatures.addLargeFerns(biomeBuilder);

        DefaultBiomeFeatures.addDefaultMushrooms(biomeBuilder);
        DefaultBiomeFeatures.addDefaultVegetation(biomeBuilder, true);

        return new Biome.Builder()
                .precipitation(true)
                .downfall(0.4f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects((new BiomeEffects.Builder())
                        .waterColor(0xe82e3b)
                        .grassColor(0x7f03fc)
                        .foliageColor(0xd203fc)
                        .build())
                .build();
    }
}
