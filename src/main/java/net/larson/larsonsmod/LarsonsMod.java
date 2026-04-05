package net.larson.larsonsmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.larson.larsonsmod.block.ModBlocks;
import net.larson.larsonsmod.block.entity.ModBlockEntities;
import net.larson.larsonsmod.entity.ModBoats;
import net.larson.larsonsmod.entity.ModEntities;
import net.larson.larsonsmod.entity.custom.PorcupineEntity;
import net.larson.larsonsmod.entity.custom.SpecialEggLootHandler;
import net.larson.larsonsmod.item.ModItemGroups;
import net.larson.larsonsmod.item.ModItems;
import net.larson.larsonsmod.recipe.ModRecipes;
import net.larson.larsonsmod.screen.ModScreenHandlers;
import net.larson.larsonsmod.sound.ModSounds;
import net.larson.larsonsmod.util.ModCustomTrades;
import net.larson.larsonsmod.util.ModLootTableModifiers;
import net.larson.larsonsmod.villager.ModVillagers;
import net.larson.larsonsmod.world.gen.ModWorldGeneration;
import net.larson.larsonsmod.world.tree.ModFoliagePlacerTypes;
import net.larson.larsonsmod.world.tree.ModTrunkPlacerTypes;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LarsonsMod implements ModInitializer {
	public static final String MOD_ID = "larsonsmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModLootTableModifiers.modifyLootTables();
		ModCustomTrades.registerCustomTrades();

		ModVillagers.registerVillagers();
		ModSounds.registerSounds();

		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();

		ModRecipes.registerRecipes();
		ModTrunkPlacerTypes.register();

		ModFoliagePlacerTypes.register();

		FuelRegistryEvents.BUILD.register((builder, context) -> {
			builder.add(ModItems.COAL_BRIQUETTE, 200);
		});
		FabricDefaultAttributeRegistry.register(ModEntities.PORCUPINE, PorcupineEntity.createPorcupineAttributes());

		SpecialEggLootHandler.register();

		StrippableBlockRegistry.register(ModBlocks.CHESTNUT_LOG, ModBlocks.STRIPPED_CHESTNUT_LOG);
		StrippableBlockRegistry.register(ModBlocks.CHESTNUT_WOOD, ModBlocks.STRIPPED_CHESTNUT_WOOD);

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CHESTNUT_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CHESTNUT_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_CHESTNUT_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_CHESTNUT_WOOD, 5, 5);

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CHESTNUT_PLANKS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CHESTNUT_LEAVES, 30, 60);

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_RED_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_GREEN_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_BLUE_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_CYAN_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_MAGENTA_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_YELLOW_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_GRAY_WOOD, 5, 5);

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_RED_PLANKS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_GREEN_PLANKS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_BLUE_PLANKS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_CYAN_PLANKS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_MAGENTA_PLANKS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_YELLOW_PLANKS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_GRAY_PLANKS, 5, 20);

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_RED_LEAVES, 30, 60);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_GREEN_LEAVES, 30, 60);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_BLUE_LEAVES, 30, 60);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_CYAN_LEAVES, 30, 60);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_MAGENTA_LEAVES, 30, 60);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_YELLOW_LEAVES, 30, 60);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.NEON_GRAY_LEAVES, 30, 60);

		ModBoats.registerBoats();
		ModWorldGeneration.generateModWorldGen();

		CustomPortalBuilder.beginPortal()
				.frameBlock(ModBlocks.RUBY_BLOCK)
				.lightWithItem(ModItems.CORN)
				.destDimID(Identifier.of(LarsonsMod.MOD_ID, "kaupendim"))
				.tintColor(0xc76efa)
				.registerPortal();

		// Neon Dimension portal - built with neon planks, lit with ruby staff
		CustomPortalBuilder.beginPortal()
				.frameBlock(ModBlocks.NEON_MAGENTA_PLANKS)
				.lightWithItem(ModItems.RUBY_STAFF)
				.destDimID(Identifier.of(LarsonsMod.MOD_ID, "neondim"))
				.tintColor(0xff00ff)
				.registerPortal();
	}
}