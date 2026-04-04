package net.larson.larsonsmod.item;

import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.larson.larsonsmod.LarsonsMod;
import net.larson.larsonsmod.block.ModBlocks;
import net.larson.larsonsmod.entity.ModBoats;
import net.larson.larsonsmod.entity.ModEntities;
import net.larson.larsonsmod.item.custom.DiceItem;
import net.larson.larsonsmod.item.custom.MetalDetectorItem;
import net.larson.larsonsmod.item.custom.ModArmorItem;
import net.larson.larsonsmod.sound.ModSounds;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    // Simple items
    public static final Item RUBY = registerItem("ruby", new Item.Settings());
    public static final Item RAW_RUBY = registerItem("raw_ruby", new Item.Settings());

    public static final Item METAL_DETECTOR = registerItem("metal_detector",
            MetalDetectorItem::new, new Item.Settings().maxDamage(64));

    public static final Item TOMATO = registerItem("tomato", new Item.Settings().food(ModFoodComponents.TOMATO));
    public static final Item COAL_BRIQUETTE = registerItem("coal_briquette", new Item.Settings());

    public static final Item RUBY_STAFF = registerItem("ruby_staff", new Item.Settings().maxCount(1));

    // Tools - use Item.Settings methods instead of removed SwordItem/PickaxeItem/etc classes
    public static final Item RUBY_PICKAXE = registerItem("ruby_pickaxe",
            new Item.Settings().pickaxe(ModToolMaterial.RUBY, 2f, -2.8f));
    public static final Item RUBY_AXE = registerItem("ruby_axe",
            new Item.Settings().axe(ModToolMaterial.RUBY, 3f, -3.0f));
    public static final Item RUBY_SHOVEL = registerItem("ruby_shovel",
            new Item.Settings().shovel(ModToolMaterial.RUBY, 0f, -3.0f));
    public static final Item RUBY_SWORD = registerItem("ruby_sword",
            new Item.Settings().sword(ModToolMaterial.RUBY, 5f, -2.4f));
    public static final Item RUBY_HOE = registerItem("ruby_hoe",
            new Item.Settings().hoe(ModToolMaterial.RUBY, 0f, -3.0f));

    // Armor - use Item.Settings.armor() instead of removed ArmorItem class
    public static final Item RUBY_HELMET = registerItem("ruby_helmet",
            ModArmorItem::new,
            new Item.Settings().armor(ModArmorMaterials.RUBY, EquipmentType.HELMET)
                    .maxDamage(EquipmentType.HELMET.getMaxDamage(ModArmorMaterials.RUBY_DURABILITY_MULTIPLIER)));
    public static final Item RUBY_CHESTPLATE = registerItem("ruby_chestplate",
            new Item.Settings().armor(ModArmorMaterials.RUBY, EquipmentType.CHESTPLATE)
                    .maxDamage(EquipmentType.CHESTPLATE.getMaxDamage(ModArmorMaterials.RUBY_DURABILITY_MULTIPLIER)));
    public static final Item RUBY_LEGGINGS = registerItem("ruby_leggings",
            new Item.Settings().armor(ModArmorMaterials.RUBY, EquipmentType.LEGGINGS)
                    .maxDamage(EquipmentType.LEGGINGS.getMaxDamage(ModArmorMaterials.RUBY_DURABILITY_MULTIPLIER)));
    public static final Item RUBY_BOOTS = registerItem("ruby_boots",
            new Item.Settings().armor(ModArmorMaterials.RUBY, EquipmentType.BOOTS)
                    .maxDamage(EquipmentType.BOOTS.getMaxDamage(ModArmorMaterials.RUBY_DURABILITY_MULTIPLIER)));

    // Crop seeds
    public static final Item TOMATO_SEEDS = registerItem("tomato_seeds",
            settings -> new BlockItem(ModBlocks.TOMATO_CROP, settings), new Item.Settings());

    public static final Item CORN_SEEDS = registerItem("corn_seeds",
            settings -> new BlockItem(ModBlocks.CORN_CROP, settings), new Item.Settings());
    public static final Item CORN = registerItem("corn", new Item.Settings());

    // Music disc
    public static final Item BAR_BRAWL_MUSIC_DISC = registerItem("bar_brawl_music_disc",
            new Item.Settings().maxCount(1).jukeboxPlayable(ModSounds.BAR_BRAWL_KEY));

    // Spawn egg - color params removed in 1.21.5+
    public static final Item PORCUPINE_SPAWN_EGG = registerItem("porcupine_spawn_egg",
            settings -> new SpawnEggItem(ModEntities.PORCUPINE, settings), new Item.Settings());

    // Signs
    public static final Item CHESTNUT_SIGN = registerItem("chestnut_sign",
            settings -> new SignItem(ModBlocks.STANDING_CHESTNUT_SIGN, ModBlocks.WALL_CHESTNUT_SIGN, settings.maxCount(16)),
            new Item.Settings());
    public static final Item HANGING_CHESTNUT_SIGN = registerItem("chestnut_hanging_sign",
            settings -> new HangingSignItem(ModBlocks.HANGING_CHESTNUT_SIGN, ModBlocks.WALL_HANGING_CHESTNUT_SIGN, settings.maxCount(16)),
            new Item.Settings());

    // Boats (Terraform handles registration)
    public static final Item CHESTNUT_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.CHESTNUT_BOAT_ID, false);
    public static final Item CHESTNUT_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.CHESTNUT_BOAT_ID, true);

    // Dice
    public static final Item DICE = registerItem("dice", DiceItem::new, new Item.Settings());


    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(RUBY);
        entries.add(RAW_RUBY);
    }

    private static RegistryKey<Item> keyOf(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(LarsonsMod.MOD_ID, name));
    }

    // Register with factory function
    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        RegistryKey<Item> key = keyOf(name);
        Item item = factory.apply(settings.registryKey(key));
        return Registry.register(Registries.ITEM, key, item);
    }

    // Register simple Item with just settings
    private static Item registerItem(String name, Item.Settings settings) {
        return registerItem(name, Item::new, settings);
    }

    public static void registerModItems() {
        LarsonsMod.LOGGER.info("Registering Mod Items for " + LarsonsMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
