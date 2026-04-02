package net.larson.larsonsmod.item;

import net.larson.larsonsmod.util.ModTags;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;

public class ModToolMaterial {
    public static final ToolMaterial RUBY = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 650, 4.5f, 3.5f, 26,
            ModTags.Items.RUBY_REPAIR);
}
