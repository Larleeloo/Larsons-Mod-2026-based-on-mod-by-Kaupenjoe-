package net.larson.larsonsmod.entity.custom;

import net.larson.larsonsmod.entity.ModEntities;
import net.larson.larsonsmod.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class SpecialEggEntity extends ThrownItemEntity {
    private static final float MIN_SCALE = 0.1f;
    private static final float MAX_SCALE = 20.0f;

    public SpecialEggEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public SpecialEggEntity(LivingEntity livingEntity, World world) {
        super(ModEntities.SPECIAL_EGG_PROJECTILE, livingEntity, world, new ItemStack(ModItems.SPECIAL_EGG));
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SPECIAL_EGG;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if (!this.getWorld().isClient() && this.getWorld() instanceof ServerWorld serverWorld) {
            this.getWorld().sendEntityStatus(this, (byte) 3);

            EntityType<?> chosenType = getRandomMobType();
            if (chosenType != null) {
                spawnScaledMob(serverWorld, chosenType, hitResult);
            }

            this.discard();
        }
    }

    private EntityType<?> getRandomMobType() {
        List<EntityType<?>> mobTypes = new ArrayList<>();
        for (EntityType<?> type : Registries.ENTITY_TYPE) {
            if (type.getSpawnGroup() != SpawnGroup.MISC) {
                mobTypes.add(type);
            }
        }

        if (mobTypes.isEmpty()) return null;
        return mobTypes.get(this.random.nextInt(mobTypes.size()));
    }

    /**
     * Generates a weighted random scale using a U-shaped distribution.
     * Values cluster at the extremes (near MIN_SCALE and MAX_SCALE)
     * with equal probability on each side. Medium sizes are rare.
     */
    private float getWeightedScale() {
        float t = this.random.nextFloat();
        // Square to concentrate near 0
        t = t * t;
        float midpoint = (MIN_SCALE + MAX_SCALE) / 2.0f;

        if (this.random.nextBoolean()) {
            // Small end: cluster near MIN_SCALE
            return MIN_SCALE + t * (midpoint - MIN_SCALE);
        } else {
            // Large end: cluster near MAX_SCALE
            return MAX_SCALE - t * (MAX_SCALE - midpoint);
        }
    }

    private void spawnScaledMob(ServerWorld serverWorld, EntityType<?> entityType, HitResult hitResult) {
        Vec3d pos = hitResult.getPos();

        Entity entity = entityType.create(serverWorld, SpawnReason.MOB_SUMMONED);
        if (entity == null) return;

        entity.refreshPositionAndAngles(pos.x, pos.y, pos.z,
                this.random.nextFloat() * 360.0f, 0.0f);

        if (entity instanceof LivingEntity livingEntity) {
            float scale = getWeightedScale();

            // Apply scale attribute
            EntityAttributeInstance scaleAttr = livingEntity.getAttributeInstance(EntityAttributes.SCALE);
            if (scaleAttr != null) {
                scaleAttr.setBaseValue(scale);
            }

            // Scale max health based on size: base_health * scale^2
            EntityAttributeInstance healthAttr = livingEntity.getAttributeInstance(EntityAttributes.MAX_HEALTH);
            if (healthAttr != null) {
                double baseHealth = healthAttr.getBaseValue();
                double scaledHealth = baseHealth * scale * scale;
                healthAttr.setBaseValue(scaledHealth);
                livingEntity.setHealth((float) scaledHealth);
            }

            // Scale attack damage if applicable
            EntityAttributeInstance attackAttr = livingEntity.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);
            if (attackAttr != null) {
                double baseAttack = attackAttr.getBaseValue();
                attackAttr.setBaseValue(baseAttack * scale);
            }

            // Scale movement speed (larger = slightly slower, smaller = slightly faster)
            EntityAttributeInstance speedAttr = livingEntity.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
            if (speedAttr != null) {
                double baseSpeed = speedAttr.getBaseValue();
                speedAttr.setBaseValue(baseSpeed / Math.sqrt(scale));
            }

            // Give fire resistance to mobs that burn in daylight
            if (burnsInDaylight(entity)) {
                livingEntity.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.FIRE_RESISTANCE, StatusEffectInstance.INFINITE, 0, false, false));
            }
        }

        serverWorld.spawnEntity(entity);
    }

    /**
     * Destroys logs and leaves near a large scaled mob. Called from the tick handler.
     * Radius scales with the mob's size attribute.
     */
    public static void trampleTreesForEntity(LivingEntity entity) {
        EntityAttributeInstance scaleAttr = entity.getAttributeInstance(EntityAttributes.SCALE);
        if (scaleAttr == null) return;

        double scale = scaleAttr.getBaseValue();
        // Only trample trees if scale >= 3.0
        if (scale < 3.0) return;

        // Radius grows with scale: scale 3 = radius 1, scale 10 = radius 3, scale 20 = radius 5
        int radius = Math.min(5, (int) (scale / 3.0));
        int height = radius * 2 + 1;

        BlockPos entityPos = entity.getBlockPos();
        World world = entity.getWorld();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -1; y <= height; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos targetPos = entityPos.add(x, y, z);
                    BlockState state = world.getBlockState(targetPos);

                    if (state.isIn(BlockTags.LOGS) || state.isIn(BlockTags.LEAVES)) {
                        world.breakBlock(targetPos, true);
                    }
                }
            }
        }
    }

    private boolean burnsInDaylight(Entity entity) {
        return entity instanceof ZombieEntity
                || entity instanceof SkeletonEntity
                || entity instanceof StrayEntity
                || entity instanceof DrownedEntity
                || entity instanceof PhantomEntity;
    }
}
