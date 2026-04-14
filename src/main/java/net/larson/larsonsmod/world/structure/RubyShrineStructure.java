package net.larson.larsonsmod.world.structure;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Optional;

/**
 * A small stone-brick shrine decorated with ruby blocks.
 * Generates on the overworld surface (WORLD_SURFACE_WG heightmap).
 */
public class RubyShrineStructure extends Structure {
    public static final MapCodec<RubyShrineStructure> CODEC = createCodec(RubyShrineStructure::new);

    public RubyShrineStructure(Config config) {
        super(config);
    }

    @Override
    public Optional<StructurePosition> getStructurePosition(Context context) {
        return getStructurePosition(context, Heightmap.Type.WORLD_SURFACE_WG, (collector) -> {
            ChunkPos chunkPos = context.chunkPos();
            int centerX = chunkPos.getCenterX();
            int centerZ = chunkPos.getCenterZ();
            int surfaceY = context.chunkGenerator().getHeight(
                    centerX, centerZ, Heightmap.Type.WORLD_SURFACE_WG,
                    context.world(), context.noiseConfig());
            BlockPos pos = new BlockPos(centerX, surfaceY - 1, centerZ);
            collector.addPiece(new RubyShrinePiece(pos));
        });
    }

    @Override
    public StructureType<?> getType() {
        return ModStructures.RUBY_SHRINE_TYPE;
    }
}
