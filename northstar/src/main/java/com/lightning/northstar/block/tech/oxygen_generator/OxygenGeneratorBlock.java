package com.lightning.northstar.block.tech.oxygen_generator;

import java.util.HashSet;

import com.lightning.northstar.block.entity.NorthstarBlockEntityTypes;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class OxygenGeneratorBlock extends HorizontalKineticBlock implements IBE<OxygenGeneratorBlockEntity> {

	public OxygenGeneratorBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return IBE.super.newBlockEntity(pos, state);
	}

	@Override
	public void onRemove(BlockState oldState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!oldState.is(newState.getBlock())) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof OxygenGeneratorBlockEntity oxygenGenerator) {
				// Remove oxygen blobs when the block is removed
				oxygenGenerator.removeOxygen();
			}
			super.onRemove(oldState, level, pos, newState, isMoving);
		}
	}

	@Override
	public Axis getRotationAxis(BlockState state) {
		return Axis.Y;
	}

	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return face == Direction.DOWN;
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
		return false;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public Class<OxygenGeneratorBlockEntity> getBlockEntityClass() {
		return OxygenGeneratorBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends OxygenGeneratorBlockEntity> getBlockEntityType() {
		return NorthstarBlockEntityTypes.OXYGEN_GENERATOR.get();
	}
}