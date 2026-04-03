package net.larson.larsonsmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.larson.larsonsmod.block.ModBlocks;
import net.larson.larsonsmod.item.ModItems;
import net.larson.larsonsmod.util.ModTags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getTagBuilder(ModTags.Items.RUBY_REPAIR)
                .add(ModItems.RUBY);

        getTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.RUBY_HELMET, ModItems.RUBY_CHESTPLATE, ModItems.RUBY_LEGGINGS, ModItems.RUBY_BOOTS);

        getTagBuilder(ItemTags.PLANKS)
                .add(ModBlocks.CHESTNUT_PLANKS.asItem());

        getTagBuilder(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.CHESTNUT_LOG.asItem())
                .add(ModBlocks.CHESTNUT_WOOD.asItem())
                .add(ModBlocks.STRIPPED_CHESTNUT_LOG.asItem())
                .add(ModBlocks.STRIPPED_CHESTNUT_WOOD.asItem())
                .add(ModBlocks.NEON_RED_WOOD.asItem())
                .add(ModBlocks.NEON_GREEN_WOOD.asItem())
                .add(ModBlocks.NEON_BLUE_WOOD.asItem())
                .add(ModBlocks.NEON_CYAN_WOOD.asItem())
                .add(ModBlocks.NEON_MAGENTA_WOOD.asItem())
                .add(ModBlocks.NEON_YELLOW_WOOD.asItem())
                .add(ModBlocks.NEON_GRAY_WOOD.asItem());

        getTagBuilder(ItemTags.PLANKS)
                .add(ModBlocks.NEON_RED_PLANKS.asItem())
                .add(ModBlocks.NEON_GREEN_PLANKS.asItem())
                .add(ModBlocks.NEON_BLUE_PLANKS.asItem())
                .add(ModBlocks.NEON_CYAN_PLANKS.asItem())
                .add(ModBlocks.NEON_MAGENTA_PLANKS.asItem())
                .add(ModBlocks.NEON_YELLOW_PLANKS.asItem())
                .add(ModBlocks.NEON_GRAY_PLANKS.asItem());
    }
}
