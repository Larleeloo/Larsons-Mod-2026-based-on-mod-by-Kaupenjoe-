package net.larson.larsonsmod.item;

import net.larson.larsonsmod.LarsonsMod;
import net.larson.larsonsmod.util.ModTags;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ModArmorMaterials {
    public static final RegistryKey<EquipmentAsset> RUBY_EQUIPMENT_ASSET =
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(LarsonsMod.MOD_ID, "ruby"));

    public static final int RUBY_DURABILITY_MULTIPLIER = 25;

    public static final ArmorMaterial RUBY = new ArmorMaterial(
            RUBY_DURABILITY_MULTIPLIER,              // durability
            Map.of(                                   // defense map
                    EquipmentType.BOOTS, 3,
                    EquipmentType.LEGGINGS, 6,
                    EquipmentType.CHESTPLATE, 8,
                    EquipmentType.HELMET, 3,
                    EquipmentType.BODY, 11
            ),
            19,                                      // enchantability
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, // equipSound
            2.0f,                                    // toughness
            0.1f,                                    // knockbackResistance
            ModTags.Items.RUBY_REPAIR,              // repairItems
            RUBY_EQUIPMENT_ASSET                     // asset key
    );
}
