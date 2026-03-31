package net.larson.larsonsmod.world.tree;

import net.larson.larsonsmod.LarsonsMod;
import net.larson.larsonsmod.mixin.TrunkPlacerTypeInvoker;
import net.larson.larsonsmod.world.tree.custom.ChestnutTrunkPlacer;
import net.larson.larsonsmod.world.tree.custom.NeonTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTrunkPlacerTypes {
    public static final TrunkPlacerType<?> CHESTNUT_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("chestnut_trunk_placer", ChestnutTrunkPlacer.CODEC);
    public static final TrunkPlacerType<?> NEON_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("neon_trunk_placer", NeonTrunkPlacer.CODEC);

    public static void register() {
        LarsonsMod.LOGGER.info("Registering Trunk Placers for " + LarsonsMod.MOD_ID);
    }
}
