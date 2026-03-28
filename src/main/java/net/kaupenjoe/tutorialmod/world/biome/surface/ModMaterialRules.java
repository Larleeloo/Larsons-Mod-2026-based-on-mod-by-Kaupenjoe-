package net.kaupenjoe.tutorialmod.world.biome.surface;

import net.kaupenjoe.tutorialmod.block.ModBlocks;
import net.kaupenjoe.tutorialmod.world.biome.ModBiomes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ModMaterialRules {
    private static final MaterialRules.MaterialRule DIRT = makeStateRule(Blocks.DIRT);
    private static final MaterialRules.MaterialRule GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final MaterialRules.MaterialRule RUBY = makeStateRule(ModBlocks.RUBY_BLOCK);
    private static final MaterialRules.MaterialRule RAW_RUBY = makeStateRule(ModBlocks.RAW_RUBY_BLOCK);

    // Neon Dimension surface materials - uses vanilla grass/dirt with black grass color from biome effects
    private static final MaterialRules.MaterialRule NEON_GRASS = makeStateRule(Blocks.GRASS_BLOCK);
    private static final MaterialRules.MaterialRule NEON_DIRT = makeStateRule(Blocks.DIRT);
    private static final MaterialRules.MaterialRule NEON_STONE = makeStateRule(ModBlocks.NEON_STONE);

    public static MaterialRules.MaterialRule makeRules() {
        MaterialRules.MaterialCondition isAtOrAboveWaterLevel = MaterialRules.water(-1, 0);

        MaterialRules.MaterialRule grassSurface = MaterialRules.sequence(MaterialRules.condition(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);

        return MaterialRules.sequence(
                MaterialRules.sequence(MaterialRules.condition(MaterialRules.biome(ModBiomes.TEST_BIOME),
                                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, GRASS_BLOCK)),
                        MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, DIRT)),

                // Neon Dimension biome surface rules
                makeNeonBiomeSurface(ModBiomes.NEON_ROLLING_HILLS),
                makeNeonBiomeSurface(ModBiomes.NEON_FLOATING_ISLANDS),
                makeNeonBiomeSurface(ModBiomes.NEON_DENSE_FOREST),
                makeNeonBiomeSurface(ModBiomes.NEON_TWILIGHT_MEADOWS),
                makeNeonBiomeSurface(ModBiomes.NEON_CRYSTAL_WASTES),

                // Default to a grass and dirt surface
                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, grassSurface)
        );
    }

    private static MaterialRules.MaterialRule makeNeonBiomeSurface(
            net.minecraft.registry.RegistryKey<net.minecraft.world.biome.Biome> biomeKey) {
        return MaterialRules.sequence(
                MaterialRules.condition(MaterialRules.biome(biomeKey),
                        MaterialRules.sequence(
                                // Top layer: neon grass
                                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, NEON_GRASS),
                                // Below surface: neon dirt for a few layers
                                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH,
                                        NEON_DIRT),
                                // Deep underground: neon stone
                                MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, NEON_STONE)
                        )));
    }

    private static MaterialRules.MaterialRule makeStateRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
