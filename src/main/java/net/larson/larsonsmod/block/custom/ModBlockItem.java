package net.larson.larsonsmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

/**
 * BlockItem subclass that uses the block's translation key instead of the
 * item's registry-derived key. In MC 1.20.5+ the default BlockItem uses the
 * item's own translation key (item.modid.name) once a registryKey is set on
 * Item.Settings, breaking lang files that only define block.modid.name.
 */
public class ModBlockItem extends BlockItem {
    public ModBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public String getTranslationKey() {
        return this.getBlock().getTranslationKey();
    }
}
