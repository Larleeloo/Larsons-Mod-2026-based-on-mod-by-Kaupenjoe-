package net.larson.larsonsmod.entity.custom;

import net.larson.larsonsmod.entity.ModEntities;
import net.larson.larsonsmod.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
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
            // Include all living entity types by filtering out MISC (projectiles, items, etc.)
            if (type.getSpawnGroup() != SpawnGroup.MISC) {
                mobTypes.add(type);
            }
        }

        if (mobTypes.isEmpty()) return null;
        return mobTypes.get(this.random.nextInt(mobTypes.size()));
    }

    private void spawnScaledMob(ServerWorld serverWorld, EntityType<?> entityType, HitResult hitResult) {
        Vec3d pos = hitResult.getPos();

        Entity entity = entityType.create(serverWorld, SpawnReason.MOB_SUMMONED);
        if (entity == null) return;

        entity.refreshPositionAndAngles(pos.x, pos.y, pos.z,
                this.random.nextFloat() * 360.0f, 0.0f);

        if (entity instanceof LivingEntity livingEntity) {
            // Random scale between MIN_SCALE and MAX_SCALE
            float scale = MIN_SCALE + this.random.nextFloat() * (MAX_SCALE - MIN_SCALE);

            // Apply scale attribute (available in 1.20.5+)
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
        }

        serverWorld.spawnEntity(entity);
    }
}
