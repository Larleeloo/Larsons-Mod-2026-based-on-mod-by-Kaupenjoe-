package net.larson.larsonsmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.larson.larsonsmod.block.ModBlocks;
import net.larson.larsonsmod.util.ModTags;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        valueLookupBuilder(ModTags.Blocks.METAL_DETECTOR_DETECTABLE_BLOCKS)
                .add(ModBlocks.RUBY_ORE)
                .forceAddTag(BlockTags.GOLD_ORES)
                .forceAddTag(BlockTags.EMERALD_ORES)
                .forceAddTag(BlockTags.REDSTONE_ORES)
                .forceAddTag(BlockTags.LAPIS_ORES)
                .forceAddTag(BlockTags.DIAMOND_ORES)
                .forceAddTag(BlockTags.IRON_ORES)
                .forceAddTag(BlockTags.COPPER_ORES)
                .forceAddTag(BlockTags.COAL_ORES);

        valueLookupBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.RAW_RUBY_BLOCK)
                .add(ModBlocks.RUBY_BLOCK)
                .add(ModBlocks.RUBY_ORE)
                .add(ModBlocks.DEEPSLATE_RUBY_ORE)
                .add(ModBlocks.NETHER_RUBY_ORE)
                .add(ModBlocks.END_STONE_RUBY_ORE)
                .add(ModBlocks.SOUND_BLOCK);

        valueLookupBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.RUBY_BLOCK);

        valueLookupBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.RAW_RUBY_BLOCK)
                .add(ModBlocks.RUBY_ORE);

        valueLookupBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.DEEPSLATE_RUBY_ORE);

        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("fabric", "needs_tool_level_4")))
                .add(ModBlocks.END_STONE_RUBY_ORE);

        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("fabric", "needs_tool_level_5")))
                .add(ModBlocks.SOUND_BLOCK);

        valueLookupBuilder(BlockTags.FENCES)
                .add(ModBlocks.RUBY_FENCE);
        valueLookupBuilder(BlockTags.FENCE_GATES)
                .add(ModBlocks.RUBY_FENCE_GATE);
        valueLookupBuilder(BlockTags.WALLS)
                .add(ModBlocks.RUBY_WALL);

        valueLookupBuilder(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.CHESTNUT_LOG)
                .add(ModBlocks.CHESTNUT_WOOD)
                .add(ModBlocks.STRIPPED_CHESTNUT_LOG)
                .add(ModBlocks.STRIPPED_CHESTNUT_WOOD)
                .add(ModBlocks.NEON_RED_WOOD)
                .add(ModBlocks.NEON_GREEN_WOOD)
                .add(ModBlocks.NEON_BLUE_WOOD)
                .add(ModBlocks.NEON_CYAN_WOOD)
                .add(ModBlocks.NEON_MAGENTA_WOOD)
                .add(ModBlocks.NEON_YELLOW_WOOD)
                .add(ModBlocks.NEON_GRAY_WOOD);

        valueLookupBuilder(BlockTags.AXE_MINEABLE)
                .add(ModBlocks.NEON_RED_WOOD)
                .add(ModBlocks.NEON_GREEN_WOOD)
                .add(ModBlocks.NEON_BLUE_WOOD)
                .add(ModBlocks.NEON_CYAN_WOOD)
                .add(ModBlocks.NEON_MAGENTA_WOOD)
                .add(ModBlocks.NEON_YELLOW_WOOD)
                .add(ModBlocks.NEON_RED_PLANKS)
                .add(ModBlocks.NEON_GREEN_PLANKS)
                .add(ModBlocks.NEON_BLUE_PLANKS)
                .add(ModBlocks.NEON_CYAN_PLANKS)
                .add(ModBlocks.NEON_MAGENTA_PLANKS)
                .add(ModBlocks.NEON_YELLOW_PLANKS)
                .add(ModBlocks.NEON_GRAY_WOOD)
                .add(ModBlocks.NEON_GRAY_PLANKS);

        valueLookupBuilder(BlockTags.HOE_MINEABLE)
                .add(ModBlocks.NEON_RED_LEAVES)
                .add(ModBlocks.NEON_GREEN_LEAVES)
                .add(ModBlocks.NEON_BLUE_LEAVES)
                .add(ModBlocks.NEON_CYAN_LEAVES)
                .add(ModBlocks.NEON_MAGENTA_LEAVES)
                .add(ModBlocks.NEON_YELLOW_LEAVES)
                .add(ModBlocks.NEON_GRAY_LEAVES);

        valueLookupBuilder(BlockTags.LEAVES)
                .add(ModBlocks.NEON_RED_LEAVES)
                .add(ModBlocks.NEON_GREEN_LEAVES)
                .add(ModBlocks.NEON_BLUE_LEAVES)
                .add(ModBlocks.NEON_CYAN_LEAVES)
                .add(ModBlocks.NEON_MAGENTA_LEAVES)
                .add(ModBlocks.NEON_YELLOW_LEAVES)
                .add(ModBlocks.NEON_GRAY_LEAVES);

        // Neon Dimension Terrain Blocks
        valueLookupBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.NEON_STONE);
        valueLookupBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ModBlocks.NEON_DIRT)
                .add(ModBlocks.NEON_GRASS_BLOCK);

        // Neon grass/dirt must be in the dirt tag so saplings and trees can grow on them
        valueLookupBuilder(BlockTags.DIRT)
                .add(ModBlocks.NEON_DIRT)
                .add(ModBlocks.NEON_GRASS_BLOCK);

        // Neon Saplings
        valueLookupBuilder(BlockTags.SAPLINGS)
                .add(ModBlocks.NEON_RED_SAPLING)
                .add(ModBlocks.NEON_GREEN_SAPLING)
                .add(ModBlocks.NEON_BLUE_SAPLING)
                .add(ModBlocks.NEON_CYAN_SAPLING)
                .add(ModBlocks.NEON_MAGENTA_SAPLING)
                .add(ModBlocks.NEON_YELLOW_SAPLING)
                .add(ModBlocks.NEON_GRAY_SAPLING);
    }
}
