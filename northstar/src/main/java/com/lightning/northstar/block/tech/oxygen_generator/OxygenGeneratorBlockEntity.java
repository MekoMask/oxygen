package com.lightning.northstar.block.tech.oxygen_generator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lightning.northstar.NorthstarTags;
import com.lightning.northstar.fluids.NorthstarFluids;
import com.lightning.northstar.particle.OxyFlowParticleData;
import com.lightning.northstar.sound.NorthstarSounds;
import com.lightning.northstar.world.OxygenStuff;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.equipment.goggles.IHaveHoveringInformation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;
import com.simibubi.create.infrastructure.config.AllConfigs;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
//import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

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
				Set<BlockPos> newBlobs = OxygenStuff.spreadOxygen(level, getBlockPos().above(), getMaxOxygenCapacity());
				OxygenStuff.removeSource(getBlockPos(), level, oxygenBlobs);
				oxygenBlobs = newBlobs;
			} else {
				removeOxygen();
			}
		}
	}

	private boolean canGenerateOxygen() {
		// Add logic for checking whether the generator can produce oxygen
		return !isOverStressed() && hasSufficientOxygen();
	}

	private boolean hasSufficientOxygen() {
		// Add logic for checking oxygen levels in the tank
		return true; // Placeholder
	}

	private int getMaxOxygenCapacity() {
		return Math.abs((int) this.speed) * 20; // Example calculation
	}

	public void removeOxygen() {
		OxygenStuff.removeSource(getBlockPos(), level, oxygenBlobs);
		oxygenBlobs.clear();
	}
}