package net.larson.larsonsmod.world.structure;

import net.larson.larsonsmod.block.ModBlocks;
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
import net.minecraft.world.chunk.ChunkGenerator;
import net.minecraft.world.gen.StructureAccessor;

/**
 * A 7x6x7 stone-brick shrine with ruby block corner pillars and a center altar.
 * Coordinates are relative to the bounding box minimum corner.
 */
public class RubyShrinePiece extends StructurePiece {
    private static final int WIDTH = 7;
    private static final int HEIGHT = 6;
    private static final int DEPTH = 7;

    public RubyShrinePiece(BlockPos pos) {
        super(ModStructures.RUBY_SHRINE_PIECE, 0, makeBox(pos));
        this.setOrientation(Direction.NORTH);
    }

    public RubyShrinePiece(StructureContext context, NbtCompound nbt) {
        super(ModStructures.RUBY_SHRINE_PIECE, nbt);
    }

    private static BlockBox makeBox(BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        // Center the 7x7 footprint horizontally on pos, with floor at pos.getY().
        return new BlockBox(
                x - 3, y, z - 3,
                x + 3, y + HEIGHT - 1, z + 3);
    }

    @Override
    protected void writeNbt(StructureContext context, NbtCompound nbt) {
        // No extra state to persist — bounding box / chain length are handled by the base class.
    }

    @Override
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor,
                         ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox,
                         ChunkPos chunkPos, BlockPos pivot) {
        BlockState stoneBricks = Blocks.STONE_BRICKS.getDefaultState();
        BlockState mossyBricks = Blocks.MOSSY_STONE_BRICKS.getDefaultState();
        BlockState crackedBricks = Blocks.CRACKED_STONE_BRICKS.getDefaultState();
        BlockState ruby = ModBlocks.RUBY_BLOCK.getDefaultState();
        BlockState air = Blocks.AIR.getDefaultState();
        BlockState stoneBrickSlab = Blocks.STONE_BRICK_SLAB.getDefaultState();
        BlockState torch = Blocks.TORCH.getDefaultState();

        int maxX = WIDTH - 1;   // 6
        int maxZ = DEPTH - 1;   // 6
        int maxY = HEIGHT - 1;  // 5

        // Floor (y=0) — stone bricks, lightly weathered.
        for (int x = 0; x <= maxX; x++) {
            for (int z = 0; z <= maxZ; z++) {
                BlockState floor = (x + z) % 3 == 0 ? mossyBricks : stoneBricks;
                this.addBlock(world, floor, x, 0, z, chunkBox);
            }
        }

        // Walls + clear interior (y=1..3).
        this.fillWithOutline(world, chunkBox,
                0, 1, 0, maxX, 3, maxZ,
                stoneBricks, air, false);

        // Scattered weathered blocks in the walls for flavor.
        this.addBlock(world, crackedBricks, 0, 2, 2, chunkBox);
        this.addBlock(world, crackedBricks, maxX, 2, 4, chunkBox);
        this.addBlock(world, mossyBricks, 2, 3, 0, chunkBox);
        this.addBlock(world, mossyBricks, 4, 3, maxZ, chunkBox);

        // Corner ruby pillars rising to the roofline.
        for (int y = 1; y <= 4; y++) {
            this.addBlock(world, ruby, 0, y, 0, chunkBox);
            this.addBlock(world, ruby, maxX, y, 0, chunkBox);
            this.addBlock(world, ruby, 0, y, maxZ, chunkBox);
            this.addBlock(world, ruby, maxX, y, maxZ, chunkBox);
        }

        // Roof (y=4) — stone brick slabs around the edge, open sky in the center.
        for (int x = 1; x < maxX; x++) {
            this.addBlock(world, stoneBrickSlab, x, 4, 0, chunkBox);
            this.addBlock(world, stoneBrickSlab, x, 4, maxZ, chunkBox);
        }
        for (int z = 1; z < maxZ; z++) {
            this.addBlock(world, stoneBrickSlab, 0, 4, z, chunkBox);
            this.addBlock(world, stoneBrickSlab, maxX, 4, z, chunkBox);
        }
        // Ruby capstones on top of each corner pillar.
        this.addBlock(world, ruby, 0, 5, 0, chunkBox);
        this.addBlock(world, ruby, maxX, 5, 0, chunkBox);
        this.addBlock(world, ruby, 0, 5, maxZ, chunkBox);
        this.addBlock(world, ruby, maxX, 5, maxZ, chunkBox);

        // Center altar — ruby pedestal with a torch on top.
        this.addBlock(world, ruby, 3, 1, 3, chunkBox);
        this.addBlock(world, torch, 3, 2, 3, chunkBox);

        // Doorway on the north (z=0) face, 2 blocks tall.
        this.addBlock(world, air, 3, 1, 0, chunkBox);
        this.addBlock(world, air, 3, 2, 0, chunkBox);
    }
}
