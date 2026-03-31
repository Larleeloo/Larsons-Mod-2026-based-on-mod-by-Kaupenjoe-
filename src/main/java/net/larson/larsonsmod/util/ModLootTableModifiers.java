package net.larson.larsonsmod.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.larson.larsonsmod.item.ModItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

public class ModLootTableModifiers {

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

            if(LootTables.CREEPER_ENTITY.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f))
                        .with(ItemEntry.builder(ModItems.COAL_BRIQUETTE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
        });

        LootTableEvents.REPLACE.register((key, original, source, registries) -> {
            // if(LootTables.DESERT_PYRAMID_ARCHAEOLOGY.equals(key)) {
            //     List<LootPoolEntry> entries = new ArrayList<>(Arrays.asList(original.pools[0].entries));
            //     entries.add(ItemEntry.builder(ModItems.METAL_DETECTOR).build());
            //     entries.add(ItemEntry.builder(ModItems.COAL_BRIQUETTE).build());
            //
            //     LootPool.Builder pool = LootPool.builder().with(entries);
            //     return LootTable.builder().pool(pool).build();
            // }

            return null;
        });
    }
}
