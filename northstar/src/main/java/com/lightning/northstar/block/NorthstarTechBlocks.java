package com.lightning.northstar.block;

import static com.lightning.northstar.Northstar.REGISTRATE;
import static com.simibubi.create.AllInteractionBehaviours.interactionBehaviour;
import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;
import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.assignDataBehaviour;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

import com.lightning.northstar.block.tech.electrolysis_machine.ElectrolysisMachineBlock;
import com.lightning.northstar.block.tech.oxygen_concentrator.OxygenConcentratorBlock;
import com.lightning.northstar.block.tech.oxygen_detector.OxygenDetectorBlock;
import com.lightning.northstar.block.tech.oxygen_filler.OxygenFillerBlock;
import com.lightning.northstar.block.tech.oxygen_generator.OxygenGeneratorBlock;
import com.lightning.northstar.block.tech.temperature_regulator.TemperatureRegulatorBlock;
import com.lightning.northstar.item.NorthstarCreativeModeTab;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.content.kinetics.simpleRelays.CogwheelBlockItem;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.content.processing.basin.BasinMovementBehaviour;
import com.simibubi.create.content.redstone.displayLink.source.StationSummaryDisplaySource;
import com.simibubi.create.content.redstone.displayLink.source.TrainStatusDisplaySource;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public class NorthstarTechBlocks {

	static {
		REGISTRATE.setCreativeTab(NorthstarCreativeModeTab.NORTHSTAR_TECH);
	}



	@SuppressWarnings("removal")
	public static final BlockEntry<OxygenConcentratorBlock> OXYGEN_CONCENTRATOR = REGISTRATE.block("oxygen_concentrator", OxygenConcentratorBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.properties(p -> p.isViewBlocking(NorthstarTechBlocks::never)
					.strength(6, 6).mapColor(MapColor.COLOR_LIGHT_GRAY))
			.properties(BlockBehaviour.Properties::noOcclusion)
			.transform(axeOrPickaxe())
			.blockstate(BlockStateGen.horizontalBlockProvider(true))
			.transform(BlockStressDefaults.setImpact(16.0))
			.addLayer(() -> RenderType::cutoutMipped)
			.simpleItem()
			.register();
	@SuppressWarnings("removal")
	public static final BlockEntry<OxygenFillerBlock> OXYGEN_FILLER = REGISTRATE.block("oxygen_filler", OxygenFillerBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.properties(p -> p.isViewBlocking(NorthstarTechBlocks::never)
					.strength(6, 6).mapColor(MapColor.COLOR_GRAY))
			.properties(BlockBehaviour.Properties::noOcclusion)
			.transform(axeOrPickaxe())
			.blockstate(BlockStateGen.horizontalBlockProvider(true))
			.addLayer(() -> RenderType::cutoutMipped)
			.simpleItem()
			.register();

	@SuppressWarnings("removal")
	public static final BlockEntry<TemperatureRegulatorBlock> TEMPERATURE_REGULATOR = REGISTRATE.block("temperature_regulator", TemperatureRegulatorBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.properties(p -> p.isViewBlocking(NorthstarTechBlocks::never)
					.strength(8, 8).mapColor(MapColor.COLOR_GRAY))
			.properties(BlockBehaviour.Properties::noOcclusion)
			.transform(axeOrPickaxe())
			.blockstate(BlockStateGen.horizontalBlockProvider(true))
			.transform(BlockStressDefaults.setImpact(16.0))
			.addLayer(() -> RenderType::cutoutMipped)
			.simpleItem()
			.register();
	@SuppressWarnings("removal")
	public static final BlockEntry<OxygenGeneratorBlock> OXYGEN_GENERATOR = REGISTRATE.block("oxygen_generator", OxygenGeneratorBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.properties(p -> p.isViewBlocking(NorthstarTechBlocks::never)
					.strength(8, 8).mapColor(MapColor.COLOR_GRAY))
			.properties(BlockBehaviour.Properties::noOcclusion)
			.transform(axeOrPickaxe())
			.blockstate(BlockStateGen.horizontalBlockProvider(true))
			.transform(BlockStressDefaults.setImpact(16.0))
			.addLayer(() -> RenderType::cutoutMipped)
			.simpleItem()
			.register();
	@SuppressWarnings("removal")
	public static final BlockEntry<ElectrolysisMachineBlock> ELECTROLYSIS_MACHINE = REGISTRATE.block("electrolysis_machine", ElectrolysisMachineBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.isViewBlocking(NorthstarTechBlocks::never))
			.properties(p -> p.sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GREEN))
			.transform(pickaxeOnly())
			.addLayer(() -> RenderType::cutoutMipped)
			.item()
			.transform(customItemModel("_", "block"))
			.register();
	public static final BlockEntry<OxygenDetectorBlock> OXYGEN_DETECTOR = REGISTRATE.block("oxygen_detector", OxygenDetectorBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.properties(p -> p.sound(SoundType.NETHERITE_BLOCK).noOcclusion().mapColor(MapColor.COLOR_GREEN))
			.transform(pickaxeOnly())
			.simpleItem()
			.lang("Oxygen Detector")
			.register();

	public static final BlockEntry<SpaceDoorBlock> IRON_SPACE_DOOR =
			REGISTRATE.block("iron_space_door", p -> new SpaceDoorBlock(p, false))
					.transform(BuilderTransformers.slidingDoor("iron_space"))
					.properties(p -> p
							.sound(SoundType.NETHERITE_BLOCK)
							.mapColor(MapColor.COLOR_GRAY)
							.noOcclusion())
					.register();

	static {
		REGISTRATE.setCreativeTab(NorthstarCreativeModeTab.NORTHSTAR_BLOCKS);
	}


	@SuppressWarnings("removal")
	public static final BlockEntry<ExtinguishedTorchBlock> EXTINGUISHED_TORCH = REGISTRATE.block("extinguished_torch", ExtinguishedTorchBlock::new)
			.properties(p -> p.sound(SoundType.WOOD).mapColor(MapColor.COLOR_BROWN).noCollission().instabreak())
			.addLayer(() -> RenderType::cutoutMipped)
			.register();
	@SuppressWarnings("removal")
	public static final BlockEntry<ExtinguishedTorchWallBlock> EXTINGUISHED_TORCH_WALL = REGISTRATE.block("extinguished_torch_wall", ExtinguishedTorchWallBlock::new)
			.properties(p -> p.sound(SoundType.WOOD).mapColor(MapColor.COLOR_BROWN).noCollission().instabreak())
			.addLayer(() -> RenderType::cutoutMipped)
			.register();
	@SuppressWarnings("removal")
	public static final BlockEntry<ExtinguishedLanternBlock> EXTINGUISHED_LANTERN = REGISTRATE.block("extinguished_lantern", ExtinguishedLanternBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.properties(p -> p.sound(SoundType.LANTERN).mapColor(MapColor.COLOR_GRAY))
			.addLayer(() -> RenderType::cutoutMipped)
			.simpleItem()
			.register();
	@SuppressWarnings("removal")
	public static final BlockEntry<GlowstoneTorchBlock> GLOWSTONE_TORCH = REGISTRATE.block("glowstone_torch", GlowstoneTorchBlock::new)
			.properties(p -> p.lightLevel(value -> 15).sound(SoundType.METAL).mapColor(MapColor.COLOR_YELLOW).noCollission().instabreak())
			.addLayer(() -> RenderType::cutoutMipped)
			.register();
	@SuppressWarnings("removal")
	public static final BlockEntry<GlowstoneTorchWallBlock> GLOWSTONE_TORCH_WALL = REGISTRATE.block("glowstone_torch_wall", GlowstoneTorchWallBlock::new)
			.properties(p -> p.lightLevel(value -> 15).sound(SoundType.METAL).mapColor(MapColor.COLOR_YELLOW).noCollission().instabreak())
			.addLayer(() -> RenderType::cutoutMipped)
			.register();
	@SuppressWarnings("removal")
	public static final BlockEntry<LanternBlock> GLOWSTONE_LANTERN = REGISTRATE.block("glowstone_lantern", LanternBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.properties(p -> p.lightLevel(value -> 15).sound(SoundType.LANTERN).mapColor(MapColor.COLOR_GRAY))
			.addLayer(() -> RenderType::cutoutMipped)
			.simpleItem()
			.register();
	public static void register() {}

	private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
		return false;
	}
}