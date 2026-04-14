package net.larson.larsonsmod.world.structure;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Optional;

/**
 * A tall glowing tower built from neon wood and planks.
 * Each instance randomly picks one of the seven neon colors.
 * Generates in the Neon dimension biomes.
 */
public class NeonTowerStructure extends Structure {
    public static final MapCodec<NeonTowerStructure> CODEC = createCodec(NeonTowerStructure::new);

    public NeonTowerStructure(Config config) {
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
            int color = context.random().nextInt(7);
            collector.addPiece(new NeonTowerPiece(pos, color));
        });
    }

    @Override
    public StructureType<?> getType() {
        return ModStructures.NEON_TOWER_TYPE;
    }
}
