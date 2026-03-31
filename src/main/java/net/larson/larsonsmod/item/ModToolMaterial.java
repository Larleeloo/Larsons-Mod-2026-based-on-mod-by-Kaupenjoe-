package net.larson.larsonsmod.item;

import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

public class ModToolMaterial {
    // Ruby: beyond diamond/netherite level (mining level 5 equivalent)
    // incorrectBlocksForDrops: blocks that this tool CANNOT properly mine
    // Using INCORRECT_FOR_NETHERITE_TOOL means this tool mines everything netherite can
    public static final ToolMaterial RUBY = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL, // same mining capability as netherite
            650, // durability
            4.5f, // mining speed
            3.5f, // attack damage bonus
            26, // enchantability
            () -> Ingredient.ofItems(ModItems.RUBY)
    );
}
