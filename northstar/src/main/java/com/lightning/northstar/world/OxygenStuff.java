package com.lightning.northstar.world;

import java.util.*;
import java.util.Map.Entry;

import net.minecraft.world.level.material.FluidState;

import com.lightning.northstar.Northstar;
import com.lightning.northstar.NorthstarTags;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Northstar.MOD_ID, bus = Bus.FORGE)
public class OxygenStuff {
	public static final HashMap<Set<BlockPos>, ResourceKey<Level>> oxygenSources = new HashMap<>();
	public static boolean debugMode = true;
	public static final int maximumOxy = 2000; // Maximum oxygen capacity
	private static final List<LivingEntity> oxygenatedEntities = new ArrayList<>();

	public static void register() {
		System.out.println("Checking for oxygen for " + Northstar.MOD_ID);
	}

	public static boolean hasOxygen(BlockPos pos, ResourceKey<Level> level) {
		if (!oxygenSources.containsValue(level)) {
			return false;
		}
		for (Entry<Set<BlockPos>, ResourceKey<Level>> blocks : oxygenSources.entrySet()) {
			if (blocks.getValue() == level && blocks.getKey().contains(pos)) {
				return true;
			}
		}
		return false;
	}

	public static Set<BlockPos> spreadOxygen(Level level, BlockPos origin, int maxSize) {
		// Use FloodFill3D.run for efficient oxygen spreading
		FloodFill3D.SolidBlockPredicate oxygenPredicate = (lvl, pos, state, positions, queue, direction) -> {
			return getIsAir(state);
		};

		return FloodFill3D.run(level, origin, maxSize, oxygenPredicate, false);
	}

	public static void removeSource(BlockPos pos, Level level, Set<BlockPos> list) {
		oxygenSources.remove(list, level.dimension());
		if (!level.isClientSide) {
			for (BlockPos block : list) {
				if (!level.getBlockState(block).isAir()) {
					level.setBlock(block, level.getBlockState(block).updateShape(null, null, level, null, null), 2);
				}
			}
		}
	}

	public static boolean getIsAir(BlockState state) {
		return state.is(NorthstarTags.NorthstarBlockTags.AIR_PASSES_THROUGH.tag) || state.getFluidState().isEmpty();
	}

	public static boolean checkForAir(LivingEntity entity) {
		Level level = entity.level();
		BlockPos pos = entity.blockPosition();
		ResourceKey<Level> dimension = level.dimension();

		// Check if the entity's position is within an oxygenated area
		for (
				Map.Entry<Set<BlockPos>, ResourceKey<Level>> entry : oxygenSources.entrySet()) {
			if (entry.getValue().equals(dimension) && entry.getKey().contains(pos)) {
				return true;
			}
		}

		// Check if the block state at the entity's position allows air to pass through
		BlockState state = level.getBlockState(pos);
		return isAirPassable(state, level.getFluidState(pos));

	}

	private static boolean isAirPassable(BlockState state, FluidState fluidState) {
		return state.is(NorthstarTags.NorthstarBlockTags.AIR_PASSES_THROUGH.tag) || !fluidState.isEmpty();
	}

	public static ItemStack getOxy(LivingEntity entity) {
		for (ItemStack item : entity.getArmorSlots()) {
			if (item.is(NorthstarTags.NorthstarItemTags.OXYGEN_SOURCES.tag)) {
				return item;
			}
		}
		return new ItemStack(Items.AIR);
	}

	public static void depleteOxy(ItemStack stack) {
		if (stack.isEmpty()) return;

		CompoundTag tag = stack.getOrCreateTag();
		int currentOxy = tag.getInt("Oxygen");
		int newOxy = Math.max(currentOxy, 0);

		// Update the oxygen value in the ItemStack
		tag.putInt("Oxygen", newOxy);

		// Update the item lore to reflect the new oxygen value
		ListTag lore = new ListTag();
		lore.add(StringTag.valueOf(Component.Serializer.toJson(
				Component.literal("Oxygen: " + newOxy + "mb").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY).withItalic(false)))));
		stack.getOrCreateTagElement("display").put("Lore", lore);
	}

	public static void oxygenateEntity(LivingEntity entity) {
		if (!oxygenatedEntities.contains(entity)) {
			oxygenatedEntities.add(entity);
		}
	}

	public static void deoxygenateEntity(LivingEntity entity) {
		oxygenatedEntities.remove(entity);
	}

}