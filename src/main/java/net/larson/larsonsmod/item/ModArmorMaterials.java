package net.larson.larsonsmod.item;

import net.larson.larsonsmod.LarsonsMod;
import net.larson.larsonsmod.util.ModTags;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModArmorMaterials {
    public static final RegistryKey<EquipmentAsset> RUBY_EQUIPMENT_ASSET =
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(LarsonsMod.MOD_ID, "ruby"));

    public static final int RUBY_DURABILITY_MULTIPLIER = 25;

    public static final ArmorMaterial RUBY = new ArmorMaterial(
            19,                                      // enchantability
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, // equipSound
            2.0f,                                    // toughness
            0.1f,                                    // knockbackResistance
            ModTags.Items.RUBY_REPAIR,              // repairItems
            RUBY_EQUIPMENT_ASSET                     // asset key
    );
}
