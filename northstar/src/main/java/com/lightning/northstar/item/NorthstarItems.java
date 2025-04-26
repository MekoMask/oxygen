package com.lightning.northstar.item;

import static com.lightning.northstar.Northstar.REGISTRATE;

import com.lightning.northstar.Northstar;
import com.lightning.northstar.block.NorthstarBlocks;
import com.lightning.northstar.block.NorthstarTechBlocks;
import com.lightning.northstar.item.armor.BrokenIronSpaceSuitArmorItem;
import com.lightning.northstar.item.armor.IronSpaceSuitArmorItem;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.equipment.sandPaper.SandPaperItem;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NorthstarItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Northstar.MOD_ID);
	static {
		REGISTRATE.setCreativeTab(NorthstarCreativeModeTab.NORTHSTAR_TAB);
	}

	public static final RegistryObject<StandingAndWallBlockItem> EXTINGUISHED_TORCH = ITEMS.register("extinguished_torch", () -> new StandingAndWallBlockItem(NorthstarTechBlocks.EXTINGUISHED_TORCH.get(), NorthstarTechBlocks.EXTINGUISHED_TORCH_WALL.get(), new Item.Properties(), Direction.DOWN));
	public static final RegistryObject<StandingAndWallBlockItem> GLOWSTONE_TORCH = ITEMS.register("glowstone_torch", () -> new StandingAndWallBlockItem(NorthstarTechBlocks.GLOWSTONE_TORCH.get(), NorthstarTechBlocks.GLOWSTONE_TORCH_WALL.get(), new Item.Properties(), Direction.DOWN));

	public static final RegistryObject<IronSpaceSuitArmorItem> IRON_SPACE_SUIT_HELMET = ITEMS.register("iron_space_suit_helmet",() -> new IronSpaceSuitArmorItem(NorthstarArmorTiers.IRON_SPACE_SUIT, ArmorItem.Type.HELMET, new Item.Properties()));
	public static final RegistryObject<IronSpaceSuitArmorItem> IRON_SPACE_SUIT_CHESTPIECE = ITEMS.register("iron_space_suit_chestpiece",() -> new IronSpaceSuitArmorItem(NorthstarArmorTiers.IRON_SPACE_SUIT, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final RegistryObject<IronSpaceSuitArmorItem> IRON_SPACE_SUIT_LEGGINGS = ITEMS.register("iron_space_suit_leggings",() -> new IronSpaceSuitArmorItem(NorthstarArmorTiers.IRON_SPACE_SUIT, ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final RegistryObject<IronSpaceSuitArmorItem> IRON_SPACE_SUIT_BOOTS = ITEMS.register("iron_space_suit_boots",() -> new IronSpaceSuitArmorItem(NorthstarArmorTiers.IRON_SPACE_SUIT, ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final RegistryObject<BrokenIronSpaceSuitArmorItem> BROKEN_IRON_SPACE_SUIT_HELMET = ITEMS.register("broken_iron_space_suit_helmet",() -> new BrokenIronSpaceSuitArmorItem(NorthstarArmorTiers.IRON_SPACE_SUIT, ArmorItem.Type.HELMET, new Item.Properties()));
	public static final RegistryObject<BrokenIronSpaceSuitArmorItem> BROKEN_IRON_SPACE_SUIT_CHESTPIECE = ITEMS.register("broken_iron_space_suit_chestpiece",() -> new BrokenIronSpaceSuitArmorItem(NorthstarArmorTiers.IRON_SPACE_SUIT, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final RegistryObject<BrokenIronSpaceSuitArmorItem> BROKEN_IRON_SPACE_SUIT_LEGGINGS = ITEMS.register("broken_iron_space_suit_leggings",() -> new BrokenIronSpaceSuitArmorItem(NorthstarArmorTiers.IRON_SPACE_SUIT, ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final RegistryObject<BrokenIronSpaceSuitArmorItem> BROKEN_IRON_SPACE_SUIT_BOOTS = ITEMS.register("broken_iron_space_suit_boots",() -> new BrokenIronSpaceSuitArmorItem(NorthstarArmorTiers.IRON_SPACE_SUIT, ArmorItem.Type.BOOTS, new Item.Properties()));

	public static void register(IEventBus eventBus)
	{ITEMS.register(eventBus);}
	public ItemStack asStack(int count) {
		return new ItemStack(get(), count);
	}

	private static ItemEntry<SequencedAssemblyItem> sequencedIngredient(String name) {
		return REGISTRATE.item(name, SequencedAssemblyItem::new)
				.register();
	}
	public Holder<Item> get() {
		// TODO Auto-generated method stub
		return null;
	}
	public static void register() {}
}