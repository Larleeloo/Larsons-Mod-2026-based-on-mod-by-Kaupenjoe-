package net.larson.larsonsmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.larson.larsonsmod.block.ModBlocks;
import net.larson.larsonsmod.item.ModItems;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModRecipeProvider extends FabricRecipeProvider {
    private static final List<ItemConvertible> RUBY_SMELTABLES = List.of(ModItems.RAW_RUBY,
            ModBlocks.RUBY_ORE, ModBlocks.DEEPSLATE_RUBY_ORE, ModBlocks.NETHER_RUBY_ORE, ModBlocks.END_STONE_RUBY_ORE);

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerSmelting(exporter, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,
                0.7f, 200, "ruby");
        offerBlasting(exporter, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,
                0.7f, 100, "ruby");

        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.RUBY, RecipeCategory.DECORATIONS,
                ModBlocks.RUBY_BLOCK);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_RUBY, 1)
                .pattern("SSS")
                .pattern("SRS")
                .pattern("SSS")
                .input('S', Items.STONE)
                .input('R', ModItems.RUBY)
                .criterion(hasItem(Items.STONE), conditionsFromItem(Items.STONE))
                .criterion(hasItem(ModItems.RUBY), conditionsFromItem(ModItems.RUBY))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.RAW_RUBY)));

        // Neon Planks from Neon Woods (1 wood -> 4 planks)
        offerNeonPlanksRecipe(exporter, ModBlocks.NEON_RED_WOOD, ModBlocks.NEON_RED_PLANKS);
        offerNeonPlanksRecipe(exporter, ModBlocks.NEON_GREEN_WOOD, ModBlocks.NEON_GREEN_PLANKS);
        offerNeonPlanksRecipe(exporter, ModBlocks.NEON_BLUE_WOOD, ModBlocks.NEON_BLUE_PLANKS);
        offerNeonPlanksRecipe(exporter, ModBlocks.NEON_CYAN_WOOD, ModBlocks.NEON_CYAN_PLANKS);
        offerNeonPlanksRecipe(exporter, ModBlocks.NEON_MAGENTA_WOOD, ModBlocks.NEON_MAGENTA_PLANKS);
        offerNeonPlanksRecipe(exporter, ModBlocks.NEON_YELLOW_WOOD, ModBlocks.NEON_YELLOW_PLANKS);
        offerNeonPlanksRecipe(exporter, ModBlocks.NEON_GRAY_WOOD, ModBlocks.NEON_GRAY_PLANKS);
    }

    private static void offerNeonPlanksRecipe(RecipeExporter exporter, ItemConvertible wood, ItemConvertible planks) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, planks, 4)
                .input(wood)
                .criterion(hasItem(wood), conditionsFromItem(wood))
                .offerTo(exporter, new Identifier(getRecipeName(planks)));
    }
}
