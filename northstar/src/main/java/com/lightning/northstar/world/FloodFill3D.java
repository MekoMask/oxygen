package com.lightning.northstar.world;

//import earth.terrarium.adastra.common.blocks.SlidingDoorBlock;
//import earth.terrarium.adastra.common.tags.ModBlockTags;
import com.lightning.northstar.NorthstarTags;
import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public final class FloodFill3D {

    private static final Direction[] DIRECTIONS = Direction.values();

    public static final SolidBlockPredicate TEST_FULL_SEAL = (level, pos, state, positions, queue, direction) -> {
        if (state.isAir()) return true;
        if (state.is(NorthstarTags.NorthstarBlockTags.AIR_PASSES_THROUGH.tag)) return true;
        //if (state.is(ModBlockTags.BLOCKS_FLOOD_FILL)) return false;
        if (state.isCollisionShapeFullBlock(level, pos)) return false;

        VoxelShape collisionShape = state.getCollisionShape(level, pos);

        //if (state.getBlock() instanceof SlidingDoorBlock block) {
        //    collisionShape = block.getCollisionShape(state, level, pos, CollisionContext.empty());
        //}

        if (collisionShape.isEmpty()) return true;
        if (!isSideSolid(collisionShape, direction)) return true;
        if (!isFaceSturdy(collisionShape, direction) && !isFaceSturdy(collisionShape, direction.getOpposite())) {
            return true;
        }

        // Check the other directions to find a potential path for the partial block.
        for (Direction dir : DIRECTIONS) {
            if (dir.getAxis() == direction.getAxis()) continue;
            var adjacentPos = pos.relative(dir);
            var adjacentState = level.getBlockState(adjacentPos);
            if (adjacentState.isAir()) return true;
        }

        positions.add(pos.asLong());
        return false;
    };

    public static Set<BlockPos> run(Level level, BlockPos start, int limit, SolidBlockPredicate predicate, boolean retainOrder) {
        level.getProfiler().push("adastra-floodfill");

        LongSet positions = retainOrder ? new LongLinkedOpenHashSet(limit) : new LongOpenHashSet(limit);
        LongArrayFIFOQueue queue = new LongArrayFIFOQueue(limit);
        queue.enqueue(start.asLong());

        while (!queue.isEmpty() && positions.size() < limit) {
            long packedPos = queue.dequeueLong();
            if (positions.contains(packedPos)) continue;
            positions.add(packedPos);

            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(BlockPos.getX(packedPos), BlockPos.getY(packedPos), BlockPos.getZ(packedPos));
            for (Direction direction : DIRECTIONS) {
                pos.set(packedPos);
                pos.move(direction);
                BlockState state = level.getBlockState(pos);
                if (!predicate.test(level, pos, state, positions, queue, direction)) continue;
                queue.enqueue(pos.asLong());
            }
        }

        Set<BlockPos> result = retainOrder ? new LinkedHashSet<>(positions.size()) : new HashSet<>(positions.size());
        for (long pos : positions) {
            result.add(BlockPos.of(pos));
        }

        level.getProfiler().pop();
        return result;
    }

    private static boolean isSideSolid(VoxelShape collisionShape, Direction dir) {
        return switch (dir.getAxis()) {
            case X -> isAxisCovered(collisionShape, Direction.Axis.Y, Direction.Axis.Z);
            case Y -> isAxisCovered(collisionShape, Direction.Axis.X, Direction.Axis.Z);
            case Z -> isAxisCovered(collisionShape, Direction.Axis.X, Direction.Axis.Y);
        };
    }

    private static boolean isAxisCovered(VoxelShape shape, Direction.Axis axis1, Direction.Axis axis2) {
        return shape.min(axis1) <= 0 && shape.max(axis1) >= 1 && shape.min(axis2) <= 0 && shape.max(axis2) >= 1;
    }


    private static boolean isFaceSturdy(VoxelShape collisionShape, Direction dir) {
        VoxelShape faceShape = collisionShape.getFaceShape(dir);
        if (faceShape.isEmpty()) return true;
        var aabbs = faceShape.toAabbs();
        if (aabbs.isEmpty()) return true;
        return checkBounds(aabbs.get(0), dir.getAxis());
    }

    private static boolean checkBounds(AABB bounds, Direction.Axis axis) {
        return switch (axis) {
            case X -> bounds.minY <= 0 && bounds.maxY >= 1 && bounds.minZ <= 0 && bounds.maxZ >= 1;
            case Y -> bounds.minX <= 0 && bounds.maxX >= 1 && bounds.minZ <= 0 && bounds.maxZ >= 1;
            case Z -> bounds.minX <= 0 && bounds.maxX >= 1 && bounds.minY <= 0 && bounds.maxY >= 1;
        };
    }

    @FunctionalInterface
    public interface SolidBlockPredicate {

        boolean test(Level level, BlockPos pos, BlockState state, LongSet positions, LongArrayFIFOQueue queue, Direction direction);
    }
}