package net.larson.larsonsmod.entity;

import net.larson.larsonsmod.LarsonsMod;
import net.larson.larsonsmod.entity.custom.DiceProjectileEntity;
import net.larson.larsonsmod.entity.custom.PorcupineEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<PorcupineEntity> PORCUPINE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(LarsonsMod.MOD_ID, "porcupine"),
            EntityType.Builder.create(PorcupineEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1f, 1f).build());

    public static final EntityType<DiceProjectileEntity> DICE_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(LarsonsMod.MOD_ID, "dice_projectile"),
            EntityType.Builder.<DiceProjectileEntity>create(DiceProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f).build());

    public static void registerModEntities() {
        LarsonsMod.LOGGER.info("Registering Entities for " + LarsonsMod.MOD_ID);
    }
}
