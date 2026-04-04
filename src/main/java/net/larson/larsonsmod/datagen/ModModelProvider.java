package net.larson.larsonsmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.larson.larsonsmod.block.ModBlocks;
import net.larson.larsonsmod.block.custom.CornCropBlock;
import net.larson.larsonsmod.block.custom.TomatoCropBlock;
import net.larson.larsonsmod.item.ModItems;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        BlockStateModelGenerator.BlockTexturePool rubyPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.RUBY_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_RUBY_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RUBY_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_RUBY_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHER_RUBY_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.END_STONE_RUBY_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SOUND_BLOCK);

        rubyPool.stairs(ModBlocks.RUBY_STAIRS);
        rubyPool.slab(ModBlocks.RUBY_SLAB);
        rubyPool.button(ModBlocks.RUBY_BUTTON);
        rubyPool.pressurePlate(ModBlocks.RUBY_PRESSURE_PLATE);
        rubyPool.fence(ModBlocks.RUBY_FENCE);
        rubyPool.fenceGate(ModBlocks.RUBY_FENCE_GATE);
        rubyPool.wall(ModBlocks.RUBY_WALL);

        blockStateModelGenerator.registerDoor(ModBlocks.RUBY_DOOR);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.RUBY_TRAPDOOR);

        blockStateModelGenerator.registerCrop(ModBlocks.TOMATO_CROP, TomatoCropBlock.AGE, 0, 1, 2, 3, 4, 5);
        blockStateModelGenerator.registerCrop(ModBlocks.CORN_CROP, CornCropBlock.AGE, 0, 1, 2, 3, 4, 5, 6, 7, 8);

        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.DAHLIA, ModBlocks.POTTED_DAHLIA, BlockStateModelGenerator.CrossType.NOT_TINTED);

        blockStateModelGenerator.registerSimpleState(ModBlocks.GEM_POLISHING_STATION);

        blockStateModelGenerator.createLogTexturePool(ModBlocks.CHESTNUT_LOG).log(ModBlocks.CHESTNUT_LOG).wood(ModBlocks.CHESTNUT_WOOD);
        blockStateModelGenerator.createLogTexturePool(ModBlocks.STRIPPED_CHESTNUT_LOG).log(ModBlocks.STRIPPED_CHESTNUT_LOG).wood(ModBlocks.STRIPPED_CHESTNUT_WOOD);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CHESTNUT_LEAVES);
        blockStateModelGenerator.registerTintableCross(ModBlocks.CHESTNUT_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED);

        BlockStateModelGenerator.BlockTexturePool chestnut_pool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CHESTNUT_PLANKS);
        chestnut_pool.family(ModBlocks.CHESTNUT_FAMILY);

        // Neon Wood Logs (pillar blocks with side + top textures)
        blockStateModelGenerator.createLogTexturePool(ModBlocks.NEON_RED_WOOD).log(ModBlocks.NEON_RED_WOOD);
        blockStateModelGenerator.createLogTexturePool(ModBlocks.NEON_GREEN_WOOD).log(ModBlocks.NEON_GREEN_WOOD);
        blockStateModelGenerator.createLogTexturePool(ModBlocks.NEON_BLUE_WOOD).log(ModBlocks.NEON_BLUE_WOOD);
        blockStateModelGenerator.createLogTexturePool(ModBlocks.NEON_CYAN_WOOD).log(ModBlocks.NEON_CYAN_WOOD);
        blockStateModelGenerator.createLogTexturePool(ModBlocks.NEON_MAGENTA_WOOD).log(ModBlocks.NEON_MAGENTA_WOOD);
        blockStateModelGenerator.createLogTexturePool(ModBlocks.NEON_YELLOW_WOOD).log(ModBlocks.NEON_YELLOW_WOOD);
        blockStateModelGenerator.createLogTexturePool(ModBlocks.NEON_GRAY_WOOD).log(ModBlocks.NEON_GRAY_WOOD);

        // Neon Planks (single texture cubes)
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_RED_PLANKS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_GREEN_PLANKS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_BLUE_PLANKS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_CYAN_PLANKS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_MAGENTA_PLANKS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_YELLOW_PLANKS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_GRAY_PLANKS);

        // Neon Leaves (single texture cubes)
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_RED_LEAVES);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_GREEN_LEAVES);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_BLUE_LEAVES);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_CYAN_LEAVES);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_MAGENTA_LEAVES);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_YELLOW_LEAVES);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_GRAY_LEAVES);

        // Neon Dimension Terrain Blocks
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_DIRT);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEON_GRASS_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.RUBY, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_RUBY, Models.GENERATED);

        itemModelGenerator.register(ModItems.COAL_BRIQUETTE, Models.GENERATED);
        itemModelGenerator.register(ModItems.TOMATO, Models.GENERATED);
        itemModelGenerator.register(ModItems.METAL_DETECTOR, Models.GENERATED);

        itemModelGenerator.register(ModItems.RUBY_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.RUBY_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.RUBY_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.RUBY_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.RUBY_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.CORN, Models.GENERATED);
        itemModelGenerator.register(ModItems.BAR_BRAWL_MUSIC_DISC, Models.GENERATED);

        itemModelGenerator.register(ModItems.RUBY_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.RUBY_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RUBY_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.RUBY_BOOTS, Models.GENERATED);

        itemModelGenerator.register(ModItems.HANGING_CHESTNUT_SIGN, Models.GENERATED);

        itemModelGenerator.register(ModItems.CHESTNUT_BOAT, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHESTNUT_CHEST_BOAT, Models.GENERATED);
        itemModelGenerator.register(ModItems.DICE, Models.GENERATED);

        itemModelGenerator.register(ModItems.PORCUPINE_SPAWN_EGG, Models.GENERATED);

    }
}
