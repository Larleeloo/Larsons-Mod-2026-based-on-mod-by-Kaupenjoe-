package net.larson.larsonsmod.entity;

import net.larson.larsonsmod.LarsonsMod;
import net.minecraft.util.Identifier;

public class ModBoats {
    public static final Identifier CHESTNUT_BOAT_ID = Identifier.of(LarsonsMod.MOD_ID, "chestnut");

    public static void registerBoats() {
        // Boat items are now registered via TerraformBoatItemHelper in ModItems
    }
}
