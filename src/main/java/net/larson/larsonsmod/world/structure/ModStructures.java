package net.larson.larsonsmod.world.structure;

import net.larson.larsonsmod.LarsonsMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.StructureType;

/**
 * Registers custom Structure types and Structure Piece types for NBT-backed
 * structure placement in the overworld and Neon dimension.
 *
 * Structure (JSON) and StructureSet (JSON) instances live under
 * data/larsonsmod/worldgen/structure/ and data/larsonsmod/worldgen/structure_set/.
 * Biome placement is controlled via the has_structure/* biome tags.
 */
public class ModStructures {
    public static final StructureType<RubyShrineStructure> RUBY_SHRINE_TYPE =
            () -> RubyShrineStructure.CODEC;
    public static final StructureType<NeonTowerStructure> NEON_TOWER_TYPE =
            () -> NeonTowerStructure.CODEC;

    public static final StructurePieceType RUBY_SHRINE_PIECE = RubyShrinePiece::new;
    public static final StructurePieceType NEON_TOWER_PIECE = NeonTowerPiece::new;

    public static void register() {
        Registry.register(Registries.STRUCTURE_TYPE,
                Identifier.of(LarsonsMod.MOD_ID, "ruby_shrine"), RUBY_SHRINE_TYPE);
        Registry.register(Registries.STRUCTURE_TYPE,
                Identifier.of(LarsonsMod.MOD_ID, "neon_tower"), NEON_TOWER_TYPE);

        Registry.register(Registries.STRUCTURE_PIECE,
                Identifier.of(LarsonsMod.MOD_ID, "ruby_shrine_piece"), RUBY_SHRINE_PIECE);
        Registry.register(Registries.STRUCTURE_PIECE,
                Identifier.of(LarsonsMod.MOD_ID, "neon_tower_piece"), NEON_TOWER_PIECE);

        LarsonsMod.LOGGER.info("Registering ModStructures for " + LarsonsMod.MOD_ID);
    }
}
