package net.larson.larsonsmod.entity.custom;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
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

        // Tick handler for tree trampling by large scaled mobs
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            // Only run every 5 ticks to reduce performance impact
            if (world.getServer().getTicks() % 5 != 0) return;

            for (var entity : world.iterateEntities()) {
                if (entity instanceof LivingEntity livingEntity && livingEntity.isAlive()) {
                    EntityAttributeInstance scaleAttr = livingEntity.getAttributeInstance(EntityAttributes.SCALE);
                    if (scaleAttr != null && scaleAttr.getBaseValue() >= 3.0) {
                        SpecialEggEntity.trampleTreesForEntity(livingEntity);
                    }
                }
            }
        });
    }

    private static void onEntityDeath(LivingEntity entity, DamageSource damageSource) {
        if (entity.getWorld().isClient()) return;

        EntityAttributeInstance scaleAttr = entity.getAttributeInstance(EntityAttributes.SCALE);
        if (scaleAttr == null) return;

        double scale = scaleAttr.getBaseValue();
        // Only apply bonus loot for entities with non-default scale (spawned by Easter Egg)
        if (Math.abs(scale - 1.0) < 0.01) return;

        ServerWorld serverWorld = (ServerWorld) entity.getWorld();
        float multiplier = (float) scale;
        int baseDropCount = Math.max(1, Math.round(multiplier));

        List<ItemStack> bonusLoot = new ArrayList<>();

        // Increased base loot: drop extra copies of the mob's normal drops scaled by size
        addScaledBaseLoot(entity, bonusLoot, baseDropCount);

        // All mobs get food drops scaled by size
        bonusLoot.add(new ItemStack(Items.COOKED_BEEF, baseDropCount));

        // Only hostile mobs drop the expensive loot
        boolean isHostile = entity.getType().getSpawnGroup() == SpawnGroup.MONSTER;
        if (isHostile) {
            // Iron ingots (always for hostiles)
            bonusLoot.add(new ItemStack(Items.IRON_INGOT, baseDropCount));

            // Experience bottles (scale >= 1.0)
            if (scale >= 1.0) {
                bonusLoot.add(new ItemStack(Items.EXPERIENCE_BOTTLE, baseDropCount));
            }

            // Gold ingots (scale >= 1.5)
            if (scale >= 1.5) {
                int goldCount = Math.max(1, (int) (multiplier * 0.8f));
                bonusLoot.add(new ItemStack(Items.GOLD_INGOT, goldCount));
            }

            // Diamonds (scale >= 2.0)
            if (scale >= 2.0) {
                int diamondCount = Math.max(1, (int) (multiplier * 0.5f));
                bonusLoot.add(new ItemStack(Items.DIAMOND, diamondCount));
            }

            // Ender pearls (scale >= 2.5)
            if (scale >= 2.5) {
                bonusLoot.add(new ItemStack(Items.ENDER_PEARL, Math.max(1, (int) (multiplier * 0.3f))));
            }

            // Emeralds (scale >= 3.0)
            if (scale >= 3.0) {
                int emeraldCount = Math.max(1, (int) (multiplier * 0.4f));
                bonusLoot.add(new ItemStack(Items.EMERALD, emeraldCount));
            }

            // Netherite scrap (scale >= 4.0)
            if (scale >= 4.0) {
                bonusLoot.add(new ItemStack(Items.NETHERITE_SCRAP, 1));
            }
        }

        for (ItemStack stack : bonusLoot) {
            entity.dropStack(serverWorld, stack);
        }
    }

    /**
     * Adds extra copies of the mob's normal loot items, scaled by size.
     * A scale-5 zombie drops 5x rotten flesh, a scale-10 skeleton drops 10x bones, etc.
     */
    private static void addScaledBaseLoot(LivingEntity entity, List<ItemStack> loot, int count) {
        EntityType<?> type = entity.getType();

        if (type == EntityType.ZOMBIE || type == EntityType.HUSK || type == EntityType.DROWNED) {
            loot.add(new ItemStack(Items.ROTTEN_FLESH, count));
        }
        if (type == EntityType.DROWNED) {
            loot.add(new ItemStack(Items.COPPER_INGOT, Math.max(1, count / 2)));
        }
        if (type == EntityType.SKELETON || type == EntityType.STRAY || type == EntityType.BOGGED) {
            loot.add(new ItemStack(Items.BONE, count));
            loot.add(new ItemStack(Items.ARROW, count));
        }
        if (type == EntityType.CREEPER) {
            loot.add(new ItemStack(Items.GUNPOWDER, count));
        }
        if (type == EntityType.SPIDER || type == EntityType.CAVE_SPIDER) {
            loot.add(new ItemStack(Items.STRING, count));
            loot.add(new ItemStack(Items.SPIDER_EYE, Math.max(1, count / 2)));
        }
        if (type == EntityType.ENDERMAN) {
            loot.add(new ItemStack(Items.ENDER_PEARL, count));
        }
        if (type == EntityType.BLAZE) {
            loot.add(new ItemStack(Items.BLAZE_ROD, count));
        }
        if (type == EntityType.GHAST) {
            loot.add(new ItemStack(Items.GHAST_TEAR, count));
            loot.add(new ItemStack(Items.GUNPOWDER, count));
        }
        if (type == EntityType.SLIME) {
            loot.add(new ItemStack(Items.SLIME_BALL, count));
        }
        if (type == EntityType.MAGMA_CUBE) {
            loot.add(new ItemStack(Items.MAGMA_CREAM, count));
        }
        if (type == EntityType.WITCH) {
            loot.add(new ItemStack(Items.GLOWSTONE_DUST, count));
            loot.add(new ItemStack(Items.REDSTONE, count));
        }
        if (type == EntityType.WITHER_SKELETON) {
            loot.add(new ItemStack(Items.BONE, count));
            loot.add(new ItemStack(Items.COAL, count));
        }
        if (type == EntityType.PHANTOM) {
            loot.add(new ItemStack(Items.PHANTOM_MEMBRANE, count));
        }
        if (type == EntityType.GUARDIAN || type == EntityType.ELDER_GUARDIAN) {
            loot.add(new ItemStack(Items.PRISMARINE_SHARD, count));
            loot.add(new ItemStack(Items.COD, count));
        }
        if (type == EntityType.SHULKER) {
            loot.add(new ItemStack(Items.SHULKER_SHELL, count));
        }
        if (type == EntityType.PILLAGER || type == EntityType.VINDICATOR) {
            loot.add(new ItemStack(Items.EMERALD, Math.max(1, count / 3)));
        }
        if (type == EntityType.EVOKER) {
            loot.add(new ItemStack(Items.TOTEM_OF_UNDYING, 1));
            loot.add(new ItemStack(Items.EMERALD, Math.max(1, count / 2)));
        }
        if (type == EntityType.HOGLIN || type == EntityType.ZOGLIN) {
            loot.add(new ItemStack(Items.LEATHER, count));
            loot.add(new ItemStack(Items.PORKCHOP, count));
        }
        if (type == EntityType.PIGLIN) {
            loot.add(new ItemStack(Items.GOLD_INGOT, Math.max(1, count / 2)));
        }
        if (type == EntityType.ZOMBIFIED_PIGLIN) {
            loot.add(new ItemStack(Items.ROTTEN_FLESH, count));
            loot.add(new ItemStack(Items.GOLD_NUGGET, count));
        }
        if (type == EntityType.BREEZE) {
            loot.add(new ItemStack(Items.BREEZE_ROD, count));
        }
        // Passive mobs
        if (type == EntityType.COW || type == EntityType.MOOSHROOM) {
            loot.add(new ItemStack(Items.LEATHER, count));
            loot.add(new ItemStack(Items.BEEF, count));
        }
        if (type == EntityType.PIG) {
            loot.add(new ItemStack(Items.PORKCHOP, count));
        }
        if (type == EntityType.SHEEP) {
            loot.add(new ItemStack(Items.MUTTON, count));
            loot.add(new ItemStack(Items.WHITE_WOOL, count));
        }
        if (type == EntityType.CHICKEN) {
            loot.add(new ItemStack(Items.CHICKEN, count));
            loot.add(new ItemStack(Items.FEATHER, count));
        }
        if (type == EntityType.RABBIT) {
            loot.add(new ItemStack(Items.RABBIT, count));
            loot.add(new ItemStack(Items.RABBIT_HIDE, count));
        }
        if (type == EntityType.SQUID || type == EntityType.GLOW_SQUID) {
            loot.add(new ItemStack(Items.INK_SAC, count));
        }
        if (type == EntityType.IRON_GOLEM) {
            loot.add(new ItemStack(Items.IRON_INGOT, count));
        }
    }
}
