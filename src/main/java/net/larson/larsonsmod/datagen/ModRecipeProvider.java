package net.larson.larsonsmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.larson.larsonsmod.block.ModBlocks;
import net.larson.larsonsmod.item.ModItems;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    private static final List<ItemConvertible> RUBY_SMELTABLES = List.of(ModItems.RAW_RUBY,
            ModBlocks.RUBY_ORE, ModBlocks.DEEPSLATE_RUBY_ORE, ModBlocks.NETHER_RUBY_ORE, ModBlocks.END_STONE_RUBY_ORE);

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        return new RecipeGenerator(registries, exporter) {
            @Override
            public void generate() {
                offerSmelting(RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,
                        0.7f, 200, "ruby");
                offerBlasting(RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,
                        0.7f, 100, "ruby");

                offerReversibleCompactingRecipes(RecipeCategory.BUILDING_BLOCKS, ModItems.RUBY, RecipeCategory.DECORATIONS,
                        ModBlocks.RUBY_BLOCK);

                createShaped(RecipeCategory.MISC, ModItems.RAW_RUBY, 1)
                        .pattern("SSS")
                        .pattern("SRS")
                        .pattern("SSS")
                        .input('S', Items.STONE)
                        .input('R', ModItems.RUBY)
                        .criterion(hasItem(Items.STONE), conditionsFromItem(Items.STONE))
                        .criterion(hasItem(ModItems.RUBY), conditionsFromItem(ModItems.RUBY))
                        .offerTo(exporter, Identifier.of(getRecipeName(ModItems.RAW_RUBY)));

                // Neon Planks from Neon Woods (1 wood -> 4 planks)
                offerNeonPlanksRecipe(ModBlocks.NEON_RED_WOOD, ModBlocks.NEON_RED_PLANKS);
                offerNeonPlanksRecipe(ModBlocks.NEON_GREEN_WOOD, ModBlocks.NEON_GREEN_PLANKS);
                offerNeonPlanksRecipe(ModBlocks.NEON_BLUE_WOOD, ModBlocks.NEON_BLUE_PLANKS);
                offerNeonPlanksRecipe(ModBlocks.NEON_CYAN_WOOD, ModBlocks.NEON_CYAN_PLANKS);
                offerNeonPlanksRecipe(ModBlocks.NEON_MAGENTA_WOOD, ModBlocks.NEON_MAGENTA_PLANKS);
                offerNeonPlanksRecipe(ModBlocks.NEON_YELLOW_WOOD, ModBlocks.NEON_YELLOW_PLANKS);
                offerNeonPlanksRecipe(ModBlocks.NEON_GRAY_WOOD, ModBlocks.NEON_GRAY_PLANKS);
            }

            private void offerNeonPlanksRecipe(ItemConvertible wood, ItemConvertible planks) {
                createShapeless(RecipeCategory.BUILDING_BLOCKS, planks, 4)
                        .input(wood)
                        .criterion(hasItem(wood), conditionsFromItem(wood))
                        .offerTo(exporter, Identifier.of(getRecipeName(planks)));
            }
        };
    }
}
