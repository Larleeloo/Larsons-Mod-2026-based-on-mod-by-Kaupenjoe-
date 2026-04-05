package net.larson.larsonsmod.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.larson.larsonsmod.item.ModItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.loot.LootTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModLootTableModifiers {

    // All base hostile mobs that should have a chance to drop the Easter Egg
    private static final List<EntityType<?>> HOSTILE_MOB_TYPES = List.of(
            EntityType.ZOMBIE,
            EntityType.SKELETON,
            EntityType.CREEPER,
            EntityType.SPIDER,
            EntityType.ENDERMAN,
            EntityType.WITCH,
            EntityType.SLIME,
            EntityType.PHANTOM,
            EntityType.DROWNED,
            EntityType.HUSK,
            EntityType.STRAY,
            EntityType.CAVE_SPIDER,
            EntityType.SILVERFISH,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.PIGLIN,
            EntityType.BLAZE,
            EntityType.GHAST,
            EntityType.MAGMA_CUBE,
            EntityType.WITHER_SKELETON,
            EntityType.HOGLIN,
            EntityType.ZOGLIN,
            EntityType.PILLAGER,
            EntityType.VINDICATOR,
            EntityType.EVOKER,
            EntityType.RAVAGER,
            EntityType.VEX,
            EntityType.GUARDIAN,
            EntityType.SHULKER,
            EntityType.BREEZE,
            EntityType.BOGGED
    );

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if(LootTables.JUNGLE_TEMPLE_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 100% of the time
                        .with(ItemEntry.builder(ModItems.METAL_DETECTOR))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }

            if(EntityType.CREEPER.getLootTableKey().isPresent() && EntityType.CREEPER.getLootTableKey().get().equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f))
                        .with(ItemEntry.builder(ModItems.COAL_BRIQUETTE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }

            // Easter Egg: rare drop (2% chance) from all base hostile mobs
            for (EntityType<?> mobType : HOSTILE_MOB_TYPES) {
                Optional<RegistryKey<LootTable>> lootKey = mobType.getLootTableKey();
                if (lootKey.isPresent() && lootKey.get().equals(key)) {
                    LootPool.Builder easterEggPool = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.02f)) // 2% drop chance
                            .with(ItemEntry.builder(ModItems.SPECIAL_EGG))
                            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)).build());

                    tableBuilder.pool(easterEggPool.build());
                    break;
                }
            }
        });

        LootTableEvents.REPLACE.register((key, original, source, registries) -> {
            return null;
        });
    }
}
