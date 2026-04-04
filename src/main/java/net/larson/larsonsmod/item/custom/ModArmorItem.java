package net.larson.larsonsmod.item.custom;

import com.google.common.collect.ImmutableMap;
import net.larson.larsonsmod.item.ModArmorMaterials;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;

public class ModArmorItem extends Item {
    private static final Map<RegistryKey<EquipmentAsset>, StatusEffectInstance> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<RegistryKey<EquipmentAsset>, StatusEffectInstance>())
                    .put(ModArmorMaterials.RUBY_EQUIPMENT_ASSET, new StatusEffectInstance(StatusEffects.HASTE, 400, 1,
                            false, false, true)).build();

    public ModArmorItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, EquipmentSlot slot) {
        if(entity instanceof PlayerEntity player && hasFullSuitOfArmorOn(player)) {
            evaluateArmorEffects(player);
        }

        super.inventoryTick(stack, world, entity, slot);
    }

    private void evaluateArmorEffects(PlayerEntity player) {
        for (Map.Entry<RegistryKey<EquipmentAsset>, StatusEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            RegistryKey<EquipmentAsset> mapAssetKey = entry.getKey();
            StatusEffectInstance mapStatusEffect = entry.getValue();

            if(hasCorrectArmorOn(mapAssetKey, player)) {
                addStatusEffectForMaterial(player, mapAssetKey, mapStatusEffect);
            }
        }
    }

    private void addStatusEffectForMaterial(PlayerEntity player, RegistryKey<EquipmentAsset> assetKey, StatusEffectInstance mapStatusEffect) {
        boolean hasPlayerEffect = player.hasStatusEffect(mapStatusEffect.getEffectType());

        if(hasCorrectArmorOn(assetKey, player) && !hasPlayerEffect) {
            player.addStatusEffect(new StatusEffectInstance(mapStatusEffect));
        }
    }

    private boolean hasFullSuitOfArmorOn(PlayerEntity player) {
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack breastplate = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);

        return !helmet.isEmpty() && !breastplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }

    private boolean hasCorrectArmorOn(RegistryKey<EquipmentAsset> assetKey, PlayerEntity player) {
        for (EquipmentSlot equipSlot : new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD}) {
            ItemStack armorStack = player.getEquippedStack(equipSlot);
            EquippableComponent equippable = armorStack.get(DataComponentTypes.EQUIPPABLE);
            if (equippable == null) {
                return false;
            }
            if (!equippable.assetId().isPresent() || !equippable.assetId().get().equals(assetKey)) {
                return false;
            }
        }
        return true;
    }
}
