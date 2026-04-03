package net.larson.larsonsmod.block;

import com.terraformersmc.terraform.sign.api.block.TerraformHangingSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformWallHangingSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformWallSignBlock;
import net.larson.larsonsmod.LarsonsMod;
import net.larson.larsonsmod.block.custom.*;
import net.larson.larsonsmod.sound.ModSounds;
import net.larson.larsonsmod.world.tree.ModSaplingGenerators;
import net.minecraft.block.*;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.function.Function;

public class ModBlocks {
    public static final Block RUBY_BLOCK = registerBlock("ruby_block",
            Block::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.AMETHYST_BLOCK));
    public static final Block RAW_RUBY_BLOCK = registerBlock("raw_ruby_block",
            Block::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.AMETHYST_BLOCK));

    public static final Block RUBY_ORE = registerBlock("ruby_ore",
            settings -> new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), settings),
            AbstractBlock.Settings.copy(Blocks.STONE).strength(2f));
    public static final Block DEEPSLATE_RUBY_ORE = registerBlock("deepslate_ruby_ore",
            settings -> new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), settings),
            AbstractBlock.Settings.copy(Blocks.DEEPSLATE).strength(4f));
    public static final Block NETHER_RUBY_ORE = registerBlock("nether_ruby_ore",
            settings -> new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), settings),
            AbstractBlock.Settings.copy(Blocks.NETHERRACK).strength(1.5f));
    public static final Block END_STONE_RUBY_ORE = registerBlock("end_stone_ruby_ore",
            settings -> new ExperienceDroppingBlock(UniformIntProvider.create(4, 7), settings),
            AbstractBlock.Settings.copy(Blocks.END_STONE).strength(4f));

    public static final Block SOUND_BLOCK = registerBlock("sound_block",
            SoundBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).sounds(ModSounds.SOUND_BLOCK_SOUNDS));

    public static final Block RUBY_STAIRS = registerBlock("ruby_stairs",
            settings -> new StairsBlock(ModBlocks.RUBY_BLOCK.getDefaultState(), settings),
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block RUBY_SLAB = registerBlock("ruby_slab",
            SlabBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));

    public static final Block RUBY_BUTTON = registerBlock("ruby_button",
            settings -> new ButtonBlock(BlockSetType.IRON, 10, settings),
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block RUBY_PRESSURE_PLATE = registerBlock("ruby_pressure_plate",
            settings -> new PressurePlateBlock(BlockSetType.IRON, settings),
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));

    public static final Block RUBY_FENCE = registerBlock("ruby_fence",
            FenceBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block RUBY_FENCE_GATE = registerBlock("ruby_fence_gate",
            settings -> new FenceGateBlock(WoodType.ACACIA, settings),
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block RUBY_WALL = registerBlock("ruby_wall",
            WallBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));

    public static final Block RUBY_DOOR = registerBlock("ruby_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque());
    public static final Block RUBY_TRAPDOOR = registerBlock("ruby_trapdoor",
            settings -> new TrapdoorBlock(BlockSetType.IRON, settings),
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque());

    public static final Block TOMATO_CROP = registerBlockNoItem("tomato_crop",
            TomatoCropBlock::new, AbstractBlock.Settings.copy(Blocks.WHEAT));

    public static final Block CORN_CROP = registerBlockNoItem("corn_crop",
            CornCropBlock::new, AbstractBlock.Settings.copy(Blocks.WHEAT));

    public static final Block DAHLIA = registerBlock("dahlia",
            settings -> new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 10, settings),
            AbstractBlock.Settings.copy(Blocks.ALLIUM).nonOpaque().noCollision());
    public static final Block POTTED_DAHLIA = registerBlockNoItem("potted_dahlia",
            settings -> new FlowerPotBlock(DAHLIA, settings),
            AbstractBlock.Settings.copy(Blocks.POTTED_ALLIUM).nonOpaque());

    public static final Block GEM_POLISHING_STATION = registerBlock("gem_polishing_station",
            GemPolishingStationBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque());

    public static final Block CHESTNUT_LOG = registerBlock("chestnut_log",
            PillarBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(4f));
    public static final Block CHESTNUT_WOOD = registerBlock("chestnut_wood",
            PillarBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_WOOD).strength(4f));
    public static final Block STRIPPED_CHESTNUT_LOG = registerBlock("stripped_chestnut_log",
            PillarBlock::new, AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG).strength(4f));
    public static final Block STRIPPED_CHESTNUT_WOOD = registerBlock("stripped_chestnut_wood",
            PillarBlock::new, AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD).strength(4f));

    public static final Block CHESTNUT_PLANKS = registerBlock("chestnut_planks",
            Block::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(4f));
    public static final Block CHESTNUT_LEAVES = registerBlock("chestnut_leaves",
            settings -> new LeavesBlock(settings) {}, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(4f).nonOpaque());

    public static final Identifier CHESTNUT_SIGN_TEXTURE = Identifier.of(LarsonsMod.MOD_ID, "entity/signs/chestnut");
    public static final Identifier CHESTNUT_HANGING_SIGN_TEXTURE = Identifier.of(LarsonsMod.MOD_ID, "entity/signs/hanging/chestnut");
    public static final Identifier CHESTNUT_HANGING_GUI_SIGN_TEXTURE = Identifier.of(LarsonsMod.MOD_ID, "textures/gui/hanging_signs/chestnut");

    public static final Block STANDING_CHESTNUT_SIGN = registerBlockNoItem("chestnut_standing_sign",
            settings -> new TerraformSignBlock(CHESTNUT_SIGN_TEXTURE, settings),
            AbstractBlock.Settings.copy(Blocks.OAK_SIGN));
    public static final Block WALL_CHESTNUT_SIGN = registerBlockNoItem("chestnut_wall_sign",
            settings -> new TerraformWallSignBlock(CHESTNUT_SIGN_TEXTURE, settings),
            AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN));
    public static final Block HANGING_CHESTNUT_SIGN = registerBlockNoItem("chestnut_hanging_sign",
            settings -> new TerraformHangingSignBlock(CHESTNUT_HANGING_SIGN_TEXTURE, CHESTNUT_HANGING_GUI_SIGN_TEXTURE, settings),
            AbstractBlock.Settings.copy(Blocks.OAK_HANGING_SIGN));
    public static final Block WALL_HANGING_CHESTNUT_SIGN = registerBlockNoItem("chestnut_wall_hanging_sign",
            settings -> new TerraformWallHangingSignBlock(CHESTNUT_HANGING_SIGN_TEXTURE, CHESTNUT_HANGING_GUI_SIGN_TEXTURE, settings),
            AbstractBlock.Settings.copy(Blocks.OAK_WALL_HANGING_SIGN));

    public static final BlockFamily CHESTNUT_FAMILY = BlockFamilies.register(ModBlocks.CHESTNUT_PLANKS)
            .sign(ModBlocks.STANDING_CHESTNUT_SIGN, ModBlocks.WALL_CHESTNUT_SIGN)
            .group("wooden").unlockCriterionName("has_planks").build();

    public static final Block DICE_BLOCK = registerBlockNoItem("dice_block",
            DiceBlock::new, AbstractBlock.Settings.copy(Blocks.STONE));

    public static final Block CHESTNUT_SAPLING = registerBlock("chestnut_sapling",
            settings -> new SaplingBlock(ModSaplingGenerators.CHESTNUT, settings),
            AbstractBlock.Settings.copy(Blocks.OAK_SAPLING));

    // Neon Wood Logs (7 colors)
    public static final Block NEON_RED_WOOD = registerBlock("neon_red_wood",
            PillarBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(2f).luminance(state -> 12));
    public static final Block NEON_GREEN_WOOD = registerBlock("neon_green_wood",
            PillarBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(2f).luminance(state -> 12));
    public static final Block NEON_BLUE_WOOD = registerBlock("neon_blue_wood",
            PillarBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(2f).luminance(state -> 12));
    public static final Block NEON_CYAN_WOOD = registerBlock("neon_cyan_wood",
            PillarBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(2f).luminance(state -> 12));
    public static final Block NEON_MAGENTA_WOOD = registerBlock("neon_magenta_wood",
            PillarBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(2f).luminance(state -> 12));
    public static final Block NEON_YELLOW_WOOD = registerBlock("neon_yellow_wood",
            PillarBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(2f).luminance(state -> 12));
    public static final Block NEON_GRAY_WOOD = registerBlock("neon_gray_wood",
            PillarBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(2f).luminance(state -> 12));

    // Neon Planks (7 colors)
    public static final Block NEON_RED_PLANKS = registerBlock("neon_red_planks",
            Block::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(2f).luminance(state -> 10).nonOpaque());
    public static final Block NEON_GREEN_PLANKS = registerBlock("neon_green_planks",
            Block::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(2f).luminance(state -> 10).nonOpaque());
    public static final Block NEON_BLUE_PLANKS = registerBlock("neon_blue_planks",
            Block::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(2f).luminance(state -> 10).nonOpaque());
    public static final Block NEON_CYAN_PLANKS = registerBlock("neon_cyan_planks",
            Block::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(2f).luminance(state -> 10).nonOpaque());
    public static final Block NEON_MAGENTA_PLANKS = registerBlock("neon_magenta_planks",
            Block::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(2f).luminance(state -> 10).nonOpaque());
    public static final Block NEON_YELLOW_PLANKS = registerBlock("neon_yellow_planks",
            Block::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(2f).luminance(state -> 10).nonOpaque());
    public static final Block NEON_GRAY_PLANKS = registerBlock("neon_gray_planks",
            Block::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(2f).luminance(state -> 10).nonOpaque());

    // Neon Dimension Terrain Blocks
    public static final Block NEON_STONE = registerBlock("neon_stone",
            Block::new, AbstractBlock.Settings.copy(Blocks.STONE).strength(2f).luminance(state -> 3));
    public static final Block NEON_DIRT = registerBlock("neon_dirt",
            Block::new, AbstractBlock.Settings.copy(Blocks.DIRT).luminance(state -> 2));
    public static final Block NEON_GRASS_BLOCK = registerBlock("neon_grass_block",
            Block::new, AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK).luminance(state -> 5));

    // Neon Leaves (7 colors)
    public static final Block NEON_RED_LEAVES = registerBlock("neon_red_leaves",
            settings -> new LeavesBlock(settings) {}, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).luminance(state -> 10).nonOpaque());
    public static final Block NEON_GREEN_LEAVES = registerBlock("neon_green_leaves",
            settings -> new LeavesBlock(settings) {}, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).luminance(state -> 10).nonOpaque());
    public static final Block NEON_BLUE_LEAVES = registerBlock("neon_blue_leaves",
            settings -> new LeavesBlock(settings) {}, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).luminance(state -> 10).nonOpaque());
    public static final Block NEON_CYAN_LEAVES = registerBlock("neon_cyan_leaves",
            settings -> new LeavesBlock(settings) {}, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).luminance(state -> 10).nonOpaque());
    public static final Block NEON_MAGENTA_LEAVES = registerBlock("neon_magenta_leaves",
            settings -> new LeavesBlock(settings) {}, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).luminance(state -> 10).nonOpaque());
    public static final Block NEON_YELLOW_LEAVES = registerBlock("neon_yellow_leaves",
            settings -> new LeavesBlock(settings) {}, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).luminance(state -> 10).nonOpaque());
    public static final Block NEON_GRAY_LEAVES = registerBlock("neon_gray_leaves",
            settings -> new LeavesBlock(settings) {}, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).luminance(state -> 10).nonOpaque());


    private static RegistryKey<Block> blockKeyOf(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(LarsonsMod.MOD_ID, name));
    }

    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        RegistryKey<Block> blockKey = blockKeyOf(name);
        Block block = factory.apply(settings.registryKey(blockKey));
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static Block registerBlockNoItem(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        RegistryKey<Block> blockKey = blockKeyOf(name);
        Block block = factory.apply(settings.registryKey(blockKey));
        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static Item registerBlockItem(String name, Block block) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(LarsonsMod.MOD_ID, name));
        return Registry.register(Registries.ITEM, itemKey,
                new BlockItem(block, new Item.Settings().registryKey(itemKey)));
    }

    public static void registerModBlocks() {
        LarsonsMod.LOGGER.info("Registering ModBlocks for " + LarsonsMod.MOD_ID);
    }
}
