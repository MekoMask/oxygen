package com.lightning.northstar.block.tech.oxygen_filler;

import java.util.List;

import com.lightning.northstar.NorthstarTags;
import com.lightning.northstar.NorthstarTags.NorthstarItemTags;
import com.lightning.northstar.fluids.NorthstarFluids;
import com.lightning.northstar.sound.NorthstarSounds;
import com.lightning.northstar.world.OxygenStuff;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

@SuppressWarnings("removal")
public class OxygenFillerBlockEntity extends SmartBlockEntity {

	private static final int MAX_OXYGEN = OxygenStuff.maximumOxy;

	protected LazyOptional<IItemHandlerModifiable> itemCapability;
	protected ItemStackHandler inventory;
	protected SmartFluidTankBehaviour tank;
	public Container container = new SimpleContainer(1) {
		public void setChanged() {
			super.setChanged();
		}
	};

	public OxygenFillerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
		inventory = new ItemStackHandler();
		itemCapability = LazyOptional.of(() -> new CombinedInvWrapper(inventory));
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		tank = SmartFluidTankBehaviour.single(this, 10000);
		behaviours.add(tank);
	}

	public void slotsChanged(Container pInventory) {
		if (pInventory == this.container) {
			ItemStack item = container.getItem(0);

			if (item.is(NorthstarItemTags.OXYGEN_SOURCES.tag)) {
				CompoundTag tag = item.getOrCreateTag();
				ListTag lore = new ListTag();
				int currentOxygen = tag.getInt("Oxygen");

				// Determine how much oxygen to fill
				int oxygenToAdd = Math.min(MAX_OXYGEN - currentOxygen, tank.getPrimaryHandler().getFluidAmount());
				if (oxygenToAdd > 0) {
					currentOxygen += oxygenToAdd;

					// Update item oxygen data
					tag.putInt("Oxygen", currentOxygen);
					lore.add(StringTag.valueOf(Component.Serializer.toJson(
							Component.literal("Oxygen: " + currentOxygen + "mb").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY).withItalic(false)))));
					item.getOrCreateTagElement("display").put("Lore", lore);
					item.setTag(tag);

					// Drain oxygen from the tank
					tank.getPrimaryHandler().drain(new FluidStack(NorthstarFluids.OXYGEN.get(), oxygenToAdd), FluidAction.EXECUTE);
				}
			}
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public void tick() {
		super.tick();
		slotsChanged(container);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, net.minecraft.core.Direction side) {
		if (cap == ForgeCapabilities.FLUID_HANDLER) {
			return tank.getCapability().cast();
		}
		return super.getCapability(cap, side);
	}
}