package com.lightning.northstar.item;

import com.lightning.northstar.Northstar;
import com.lightning.northstar.NorthstarTags.NorthstarItemTags;
import com.lightning.northstar.block.NorthstarBlocks;
import com.lightning.northstar.block.NorthstarTechBlocks;
import com.lightning.northstar.fluids.NorthstarFluids;
import com.lightning.northstar.world.OxygenStuff;
import com.simibubi.create.foundation.utility.Components;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class NorthstarCreativeModeTab {
	private static final DeferredRegister<CreativeModeTab> REGISTER =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Northstar.MOD_ID);

	public static final RegistryObject<CreativeModeTab> NORTHSTAR_TAB = REGISTER.register("northstartab",
			() -> CreativeModeTab.builder()
					.title(Components.translatable("itemGroup.northstartab"))
					.icon(() -> new ItemStack(NorthstarItems.IRON_SPACE_SUIT_HELMET.get()))
					.displayItems((pParameters, out) -> {
						out.accept(NorthstarItems.BROKEN_IRON_SPACE_SUIT_HELMET.get());
						out.accept(NorthstarItems.BROKEN_IRON_SPACE_SUIT_CHESTPIECE.get());
						out.accept(NorthstarItems.BROKEN_IRON_SPACE_SUIT_LEGGINGS.get());
						out.accept(NorthstarItems.BROKEN_IRON_SPACE_SUIT_BOOTS.get());
						out.accept(NorthstarItems.IRON_SPACE_SUIT_HELMET.get());
						addOxyItem(out, NorthstarItems.IRON_SPACE_SUIT_CHESTPIECE.get());
						out.accept(NorthstarItems.IRON_SPACE_SUIT_LEGGINGS.get());
						out.accept(NorthstarItems.IRON_SPACE_SUIT_BOOTS.get());

						out.accept(NorthstarFluids.LIQUID_OXYGEN.getBucket().get());

					})
					.build());
	public static final RegistryObject<CreativeModeTab> NORTHSTAR_BLOCKS = REGISTER.register("northstarblocks",
			() -> CreativeModeTab.builder()
					.title(Components.translatable("itemGroup.northstarblocks"))
					.icon(() -> new ItemStack(NorthstarBlocks.IRON_PLATING.get()))
					.displayItems((pParameters, out) -> {
						out.accept(NorthstarBlocks.IRON_PLATING.get());
						out.accept(NorthstarBlocks.IRON_PLATING_SLAB.get());
						out.accept(NorthstarBlocks.IRON_PLATING_VERTICAL_SLAB.get());
						out.accept(NorthstarBlocks.IRON_PLATING_STAIRS.get());
						out.accept(NorthstarBlocks.IRON_SHEETMETAL.get());
						out.accept(NorthstarBlocks.IRON_SHEETMETAL_SLAB.get());
						out.accept(NorthstarBlocks.IRON_SHEETMETAL_VERTICAL_SLAB.get());
						out.accept(NorthstarBlocks.IRON_GRATE.get());
						out.accept(NorthstarBlocks.IRON_PILLAR.get());
						out.accept(NorthstarBlocks.VENT_BLOCK.get());

						out.accept(NorthstarItems.EXTINGUISHED_TORCH.get());
						out.accept(NorthstarTechBlocks.EXTINGUISHED_LANTERN.get());

						out.accept(NorthstarItems.GLOWSTONE_TORCH.get());
						out.accept(NorthstarTechBlocks.GLOWSTONE_LANTERN.get());

					})
					.build());
	public static final RegistryObject<CreativeModeTab> NORTHSTAR_TECH = REGISTER.register("northstartech",
			() -> CreativeModeTab.builder()
					.title(Components.translatable("itemGroup.northstartech"))
					.icon(() -> new ItemStack(NorthstarTechBlocks.OXYGEN_CONCENTRATOR.get()))
					.displayItems((pParameters, out) -> {
						out.accept(NorthstarTechBlocks.OXYGEN_CONCENTRATOR.get());
						out.accept(NorthstarTechBlocks.OXYGEN_CONCENTRATOR.get());
						out.accept(NorthstarTechBlocks.OXYGEN_FILLER.get());
						out.accept(NorthstarTechBlocks.OXYGEN_GENERATOR.get());
						out.accept(NorthstarTechBlocks.OXYGEN_DETECTOR.get());
						out.accept(NorthstarTechBlocks.ELECTROLYSIS_MACHINE.get());
						out.accept(NorthstarTechBlocks.TEMPERATURE_REGULATOR.get());
						out.accept(NorthstarTechBlocks.IRON_SPACE_DOOR.get());
					})
					.build());


	public static void register(IEventBus modEventBus) {
		REGISTER.register(modEventBus);
	}

	public static void addOxyItem(CreativeModeTab.Output pGroup, Item item) {
		ItemStack stack = new ItemStack(item);
		CompoundTag nbt = new CompoundTag();
		nbt.putInt("Oxygen", OxygenStuff.maximumOxy);
		stack.setTag(nbt);
		if(stack.is(NorthstarItemTags.OXYGEN_SOURCES.tag)) {
			ListTag lore = new ListTag();
			lore.add(StringTag.valueOf(Component.Serializer.toJson(Component.literal( "Oxygen: " + OxygenStuff.maximumOxy + "mb").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY).withItalic(false))).toString()));
			stack.getOrCreateTagElement("display").put("Lore", lore);
		}
		pGroup.accept(stack);
	}
}