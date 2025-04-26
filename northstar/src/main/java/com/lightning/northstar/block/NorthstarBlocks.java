package com.lightning.northstar.block;

import static com.lightning.northstar.Northstar.REGISTRATE;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static net.minecraft.world.level.block.Blocks.OAK_PLANKS;
import static net.minecraft.world.level.block.Blocks.STONE;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import com.lightning.northstar.Northstar;
import com.lightning.northstar.item.NorthstarItems;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.GravelBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class NorthstarBlocks {


	public static final DeferredRegister<Block> BLOCKS =
			DeferredRegister.create(ForgeRegistries.BLOCKS, Northstar.MOD_ID);

	private static boolean never(BlockState blockstate, BlockGetter blockgetter, BlockPos blockpos) {
		return false;
	}

	//iron stuff
	public static final RegistryObject<Block> IRON_SHEETMETAL = registerBlock("iron_sheetmetal",
			() -> new Block(BlockBehaviour.Properties.of() .sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY)  .strength(4f,12f) .requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> IRON_SHEETMETAL_SLAB = registerBlock("iron_sheetmetal_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.of() .sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY) .strength(4f,12f) .requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> IRON_SHEETMETAL_VERTICAL_SLAB = registerBlock("iron_sheetmetal_vertical_slab",
			() -> new VerticalSlabBlock(BlockBehaviour.Properties.of() .sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY) .strength(4f,12f) .requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> IRON_PLATING = registerBlock("iron_plating",
			() -> new Block(BlockBehaviour.Properties.of() .sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY) .strength(4f,12f) .requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> IRON_PLATING_SLAB = registerBlock("iron_plating_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.of() .sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY) .strength(4f,12f) .requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> IRON_PLATING_VERTICAL_SLAB = registerBlock("iron_plating_vertical_slab",
			() -> new VerticalSlabBlock(BlockBehaviour.Properties.of() .sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY) .strength(4f,12f) .requiresCorrectToolForDrops()));
	@SuppressWarnings("deprecation")
	public static final RegistryObject<Block> IRON_PLATING_STAIRS = registerBlock("iron_plating_stairs",
			() -> new StairBlock(IRON_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.of() .sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY) .strength(4f,12f) .requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> IRON_PILLAR = registerBlock("iron_pillar",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.of() .sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY) .strength(4f,12f) .requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> IRON_GRATE = registerBlock("iron_grate",
			() -> new GrateBlock(BlockBehaviour.Properties.of().sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY)
					.strength(4f,8f).requiresCorrectToolForDrops().noOcclusion().isSuffocating(NorthstarBlocks::never).isViewBlocking(NorthstarBlocks::never)));


	public static final RegistryObject<Block> VENT_BLOCK = registerBlock("vent_block",
			() -> new GrateBlock(BlockBehaviour.Properties.of().sound(SoundType.NETHERITE_BLOCK) .mapColor(MapColor.COLOR_GRAY)
					.strength(4f,8f).requiresCorrectToolForDrops().noOcclusion().isSuffocating(NorthstarBlocks::never).isViewBlocking(NorthstarBlocks::never)));


	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}
	private static ToIntFunction<BlockState> litBlockEmission(int pLightValue) {
		return (p_50763_) -> {
			return p_50763_.getValue(BlockStateProperties.LIT) ? pLightValue : 0;
		};
	}

	private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
		return NorthstarItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	public static void register(IEventBus eventBus)
	{
		BLOCKS.register(eventBus);

	}
}