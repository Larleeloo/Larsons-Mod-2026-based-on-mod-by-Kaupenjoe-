package net.larson.larsonsmod.entity.client;

import net.larson.larsonsmod.LarsonsMod;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer PORCUPINE =
            new EntityModelLayer(Identifier.of(LarsonsMod.MOD_ID, "porcupine"), "main");
}
