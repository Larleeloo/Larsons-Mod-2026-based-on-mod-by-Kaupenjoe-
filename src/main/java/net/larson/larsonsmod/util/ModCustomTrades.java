package net.larson.larsonsmod.util;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.larson.larsonsmod.block.ModBlocks;
import net.larson.larsonsmod.item.ModItems;
import net.larson.larsonsmod.villager.ModVillagers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

import java.util.Optional;

public class ModCustomTrades {
    // RegistryKeys for vanilla professions
    private static final RegistryKey<VillagerProfession> FARMER_KEY =
            RegistryKey.of(RegistryKeys.VILLAGER_PROFESSION, Identifier.ofVanilla("farmer"));
    private static final RegistryKey<VillagerProfession> LIBRARIAN_KEY =
            RegistryKey.of(RegistryKeys.VILLAGER_PROFESSION, Identifier.ofVanilla("librarian"));

    public static void registerCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(FARMER_KEY, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new TradedItem(Items.EMERALD, 2),
                            new ItemStack(ModItems.TOMATO, 7),
                            6, 5, 0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new TradedItem(Items.EMERALD, 7),
                            new ItemStack(ModItems.TOMATO_SEEDS, 1),
                            2, 7, 0.075f));
                });

        TradeOfferHelper.registerVillagerOffers(FARMER_KEY, 2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new TradedItem(Items.GOLD_INGOT, 16),
                            Optional.of(new TradedItem(Items.DIAMOND, 12)),
                            new ItemStack(ModItems.CORN_SEEDS, 1),
                            2, 7, 0.075f));
                });

        TradeOfferHelper.registerVillagerOffers(LIBRARIAN_KEY, 1,
                factories -> {
                    factories.add((entity, random) -> {
                        RegistryEntry<Enchantment> piercing = entity.getWorld().getRegistryManager()
                                .getWrapperOrThrow(RegistryKeys.ENCHANTMENT)
                                .getOrThrow(Enchantments.PIERCING);
                        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
                        enchantedBook.addEnchantment(piercing, 2);
                        return new TradeOffer(
                                new TradedItem(ModItems.RUBY, 32),
                                enchantedBook,
                                3, 12, 0.075f);
                    });
                });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.SOUND_MASTER_KEY, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new TradedItem(ModItems.CORN, 32),
                            new ItemStack(ModBlocks.SOUND_BLOCK, 2),
                            6, 12, 0.075f));
                });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.SOUND_MASTER_KEY, 2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new TradedItem(ModItems.RUBY_SWORD, 1),
                            new ItemStack(ModItems.RUBY_HELMET, 1),
                            2, 12, 0.075f));
                });


        TradeOfferHelper.registerWanderingTraderOffers(1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new TradedItem(ModItems.RAW_RUBY, 16),
                            new ItemStack(ModItems.METAL_DETECTOR, 1),
                            1, 12, 0.075f));
                });

        TradeOfferHelper.registerWanderingTraderOffers(2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new TradedItem(ModItems.RAW_RUBY, 1),
                            new ItemStack(ModItems.COAL_BRIQUETTE, 1),
                            1, 12, 0.075f));
                });
    }
}
