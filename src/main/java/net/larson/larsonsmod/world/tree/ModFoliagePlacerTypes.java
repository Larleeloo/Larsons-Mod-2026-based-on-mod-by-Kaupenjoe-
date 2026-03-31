package net.larson.larsonsmod.world.tree;

import net.larson.larsonsmod.LarsonsMod;
import net.larson.larsonsmod.mixin.FoliagePlacerTypeInvoker;
import net.larson.larsonsmod.world.tree.custom.ChestnutFoliagePlacer;
import net.larson.larsonsmod.world.tree.custom.NeonFoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class ModFoliagePlacerTypes {
    public static final FoliagePlacerType<?> CHESTNUT_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("chestnut_foliage_placer", ChestnutFoliagePlacer.CODEC);
    public static final FoliagePlacerType<?> NEON_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("neon_foliage_placer", NeonFoliagePlacer.CODEC);

    public static void register() {
        LarsonsMod.LOGGER.info("Registering Foliage Placer for " + LarsonsMod.MOD_ID);
    }
}
