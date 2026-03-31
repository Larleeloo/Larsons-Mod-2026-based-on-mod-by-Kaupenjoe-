package net.larson.larsonsmod.item;

import net.larson.larsonsmod.LarsonsMod;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final RegistryEntry<ArmorMaterial> RUBY = registerMaterial("ruby",
            Map.of(
                    ArmorItem.Type.BOOTS, 3,
                    ArmorItem.Type.LEGGINGS, 6,
                    ArmorItem.Type.CHESTPLATE, 8,
                    ArmorItem.Type.HELMET, 3,
                    ArmorItem.Type.BODY, 5
            ),
            19,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
            2.0f,
            0.1f,
            () -> Ingredient.ofItems(ModItems.RUBY),
            25
    );

    public static final int RUBY_DURABILITY_MULTIPLIER = 25;

    private static RegistryEntry<ArmorMaterial> registerMaterial(String id,
            Map<ArmorItem.Type, Integer> defense, int enchantability,
            RegistryEntry<net.minecraft.sound.SoundEvent> equipSound, float toughness,
            float knockbackResistance, Supplier<Ingredient> repairIngredient, int durabilityMultiplier) {
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(Identifier.of(LarsonsMod.MOD_ID, id)));
        ArmorMaterial material = new ArmorMaterial(defense, enchantability, equipSound,
                repairIngredient, layers, toughness, knockbackResistance);
        return Registry.registerReference(Registries.ARMOR_MATERIAL,
                Identifier.of(LarsonsMod.MOD_ID, id), material);
    }
}
