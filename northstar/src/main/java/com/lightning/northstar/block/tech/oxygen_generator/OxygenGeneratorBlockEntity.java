package com.lightning.northstar.block.tech.oxygen_generator;

import java.util.HashSet;
import java.util.Set;

import com.lightning.northstar.NorthstarTags;
import com.lightning.northstar.world.OxygenStuff;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class OxygenGeneratorBlockEntity extends KineticBlockEntity {
	public Set<BlockPos> oxygenBlobs = new HashSet<>();
	private static final int SPREAD_INTERVAL = 40;

	public OxygenGeneratorBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
	}

	@Override
	public void tick() {
		super.tick();
		long gameTime = level.getGameTime();

		if (gameTime % SPREAD_INTERVAL == 0) {
			if (canGenerateOxygen()) {
				BlockPos positionAbove = getBlockPos().above();
				BlockState stateAbove = getBlockStateSafe(level, positionAbove);
				if (stateAbove != null && stateAbove.is(NorthstarTags.NorthstarBlockTags.AIR_PASSES_THROUGH.tag)) {
					Set<BlockPos> newBlobs = OxygenStuff.spreadOxygen(level, positionAbove, getMaxOxygenCapacity());
					OxygenStuff.removeSource(getBlockPos(), level, oxygenBlobs);
					oxygenBlobs = newBlobs;
				}
			} else {
				removeOxygen();
			}
		}
	}

	private boolean canGenerateOxygen() {
		return !isOverStressed() && hasSufficientOxygen();
	}

	private boolean hasSufficientOxygen() {
		return true; // Placeholder logic for oxygen sufficiency
	}

	private int getMaxOxygenCapacity() {
		return Math.abs((int) this.speed) * 20; // Example calculation
	}

	public void removeOxygen() {
		if (getBlockPos() == null) {
			System.err.println("OxygenGeneratorBlockEntity: BlockPos is null during oxygen removal!");
			return;
		}

		OxygenStuff.removeSource(getBlockPos(), level, oxygenBlobs);
		oxygenBlobs.clear();
	}

	private BlockState getBlockStateSafe(Level level, BlockPos pos) {
		if (level == null || pos == null) {
			return null;
		}
		return level.getBlockState(pos);
	}
}