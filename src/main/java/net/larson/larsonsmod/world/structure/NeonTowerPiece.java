package net.larson.larsonsmod.world.structure;

import net.larson.larsonsmod.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.StructureAccessor;

/**
 * A 7-wide, 16-tall glowing neon tower. Each tower picks one of the seven
 * neon color palettes at spawn time, persisted through NBT so the rest of
 * the tower re-generates consistently when chunks reload.
 */
public class NeonTowerPiece extends StructurePiece {
    private static final int WIDTH = 7;
    private static final int HEIGHT = 16;
    private static final int DEPTH = 7;

    private static final String COLOR_KEY = "NeonColor";

    private static final Block[] NEON_WOOD = {
            ModBlocks.NEON_RED_WOOD, ModBlocks.NEON_GREEN_WOOD, ModBlocks.NEON_BLUE_WOOD,
            ModBlocks.NEON_CYAN_WOOD, ModBlocks.NEON_MAGENTA_WOOD, ModBlocks.NEON_YELLOW_WOOD,
            ModBlocks.NEON_GRAY_WOOD
    };
    private static final Block[] NEON_PLANKS = {
            ModBlocks.NEON_RED_PLANKS, ModBlocks.NEON_GREEN_PLANKS, ModBlocks.NEON_BLUE_PLANKS,
            ModBlocks.NEON_CYAN_PLANKS, ModBlocks.NEON_MAGENTA_PLANKS, ModBlocks.NEON_YELLOW_PLANKS,
            ModBlocks.NEON_GRAY_PLANKS
    };
    private static final Block[] NEON_LEAVES = {
            ModBlocks.NEON_RED_LEAVES, ModBlocks.NEON_GREEN_LEAVES, ModBlocks.NEON_BLUE_LEAVES,
            ModBlocks.NEON_CYAN_LEAVES, ModBlocks.NEON_MAGENTA_LEAVES, ModBlocks.NEON_YELLOW_LEAVES,
            ModBlocks.NEON_GRAY_LEAVES
    };

    private final int colorIndex;

    public NeonTowerPiece(BlockPos pos, int colorIndex) {
        super(ModStructures.NEON_TOWER_PIECE, 0, makeBox(pos));
        this.setOrientation(Direction.NORTH);
        this.colorIndex = Math.floorMod(colorIndex, NEON_WOOD.length);
    }

    public NeonTowerPiece(StructureContext context, NbtCompound nbt) {
        super(ModStructures.NEON_TOWER_PIECE, nbt);
        this.colorIndex = Math.floorMod(nbt.getInt(COLOR_KEY, 0), NEON_WOOD.length);
    }

    private static BlockBox makeBox(BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        return new BlockBox(
                x - 3, y, z - 3,
                x + 3, y + HEIGHT - 1, z + 3);
    }

    @Override
    protected void writeNbt(StructureContext context, NbtCompound nbt) {
        nbt.putInt(COLOR_KEY, this.colorIndex);
    }

    @Override
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor,
                         ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox,
                         ChunkPos chunkPos, BlockPos pivot) {
        BlockState stone = ModBlocks.NEON_STONE.getDefaultState();
        BlockState wood = NEON_WOOD[colorIndex].getDefaultState();
        BlockState planks = NEON_PLANKS[colorIndex].getDefaultState();
        BlockState leaves = NEON_LEAVES[colorIndex].getDefaultState();
        BlockState air = Blocks.AIR.getDefaultState();

        int maxX = WIDTH - 1;   // 6
        int maxZ = DEPTH - 1;   // 6
        int maxY = HEIGHT - 1;  // 15

        // Stone foundation (y=0).
        for (int x = 0; x <= maxX; x++) {
            for (int z = 0; z <= maxZ; z++) {
                this.addBlock(world, stone, x, 0, z, chunkBox);
            }
        }

        // Shaft walls (y=1..12): neon wood outline, air interior.
        this.fillWithOutline(world, chunkBox,
                0, 1, 0, maxX, 12, maxZ,
                wood, air, false);

        // Interior plank floors at y=4, y=8, y=12 (acts as landings).
        for (int x = 1; x < maxX; x++) {
            for (int z = 1; z < maxZ; z++) {
                this.addBlock(world, planks, x, 4, z, chunkBox);
                this.addBlock(world, planks, x, 8, z, chunkBox);
                this.addBlock(world, planks, x, 12, z, chunkBox);
            }
        }

        // Doorway on the north face (z=0), 2 blocks tall.
        this.addBlock(world, air, 3, 1, 0, chunkBox);
        this.addBlock(world, air, 3, 2, 0, chunkBox);

        // Cross-shaped window on each face at y=6 and y=10.
        for (int windowY : new int[]{6, 10}) {
            this.addBlock(world, air, 3, windowY, 0, chunkBox);
            this.addBlock(world, air, 3, windowY, maxZ, chunkBox);
            this.addBlock(world, air, 0, windowY, 3, chunkBox);
            this.addBlock(world, air, maxX, windowY, 3, chunkBox);
        }

        // Parapet (y=13): crenellations — alternating wood blocks around the edge.
        for (int x = 0; x <= maxX; x++) {
            if (x % 2 == 0) {
                this.addBlock(world, wood, x, 13, 0, chunkBox);
                this.addBlock(world, wood, x, 13, maxZ, chunkBox);
            }
        }
        for (int z = 0; z <= maxZ; z++) {
            if (z % 2 == 0) {
                this.addBlock(world, wood, 0, 13, z, chunkBox);
                this.addBlock(world, wood, maxX, 13, z, chunkBox);
            }
        }

        // Leaf crown: a 5x5 cap of neon leaves centered above the tower,
        // with a single wood spire in the middle.
        for (int x = 1; x < maxX; x++) {
            for (int z = 1; z < maxZ; z++) {
                this.addBlock(world, leaves, x, 14, z, chunkBox);
            }
        }
        this.addBlock(world, leaves, 2, 15, 3, chunkBox);
        this.addBlock(world, leaves, 4, 15, 3, chunkBox);
        this.addBlock(world, leaves, 3, 15, 2, chunkBox);
        this.addBlock(world, leaves, 3, 15, 4, chunkBox);
        this.addBlock(world, wood, 3, 15, 3, chunkBox);
    }
}
