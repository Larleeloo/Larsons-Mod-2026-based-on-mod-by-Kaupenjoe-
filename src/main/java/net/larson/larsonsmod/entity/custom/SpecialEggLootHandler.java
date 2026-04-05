package net.larson.larsonsmod.entity.custom;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.List;

public class SpecialEggLootHandler {

    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register(SpecialEggLootHandler::onEntityDeath);
    }

    private static void onEntityDeath(LivingEntity entity, DamageSource damageSource) {
        if (entity.getWorld().isClient()) return;

        EntityAttributeInstance scaleAttr = entity.getAttributeInstance(EntityAttributes.SCALE);
        if (scaleAttr == null) return;

        double scale = scaleAttr.getBaseValue();
        // Only apply bonus loot for entities with non-default scale (spawned by special egg)
        if (Math.abs(scale - 1.0) < 0.01) return;

        ServerWorld serverWorld = (ServerWorld) entity.getWorld();

        // Calculate bonus multiplier: larger scale = more loot
        // scale of 5.0 -> multiplier of ~5, scale of 0.5 -> multiplier of ~0.5
        float multiplier = (float) scale;

        // Build a pool of possible bonus loot items
        List<ItemStack> bonusLoot = new ArrayList<>();

        // Common drops - quantity scales with size
        int baseDropCount = Math.max(1, Math.round(multiplier));

        // Diamonds (rare, only for scale >= 2.0)
        if (scale >= 2.0) {
            int diamondCount = Math.max(1, (int) (multiplier * 0.5f));
            bonusLoot.add(new ItemStack(Items.DIAMOND, diamondCount));
        }

        // Gold ingots (scale >= 1.5)
        if (scale >= 1.5) {
            int goldCount = Math.max(1, (int) (multiplier * 0.8f));
            bonusLoot.add(new ItemStack(Items.GOLD_INGOT, goldCount));
        }

        // Iron ingots (always)
        bonusLoot.add(new ItemStack(Items.IRON_INGOT, baseDropCount));

        // Experience bottles (scale >= 1.0)
        if (scale >= 1.0) {
            bonusLoot.add(new ItemStack(Items.EXPERIENCE_BOTTLE, baseDropCount));
        }

        // Emeralds (scale >= 3.0)
        if (scale >= 3.0) {
            int emeraldCount = Math.max(1, (int) (multiplier * 0.4f));
            bonusLoot.add(new ItemStack(Items.EMERALD, emeraldCount));
        }

        // Netherite scrap (scale >= 4.0, very rare)
        if (scale >= 4.0) {
            bonusLoot.add(new ItemStack(Items.NETHERITE_SCRAP, 1));
        }

        // Ender pearls (scale >= 2.5)
        if (scale >= 2.5) {
            bonusLoot.add(new ItemStack(Items.ENDER_PEARL, Math.max(1, (int) (multiplier * 0.3f))));
        }

        // Food drops
        bonusLoot.add(new ItemStack(Items.COOKED_BEEF, baseDropCount));

        // Drop all bonus loot at the entity's position
        for (ItemStack stack : bonusLoot) {
            entity.dropStack(serverWorld, stack);
        }
    }
}
