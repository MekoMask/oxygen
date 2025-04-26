package com.lightning.northstar.block.entity;

import static com.lightning.northstar.Northstar.REGISTRATE;

import com.lightning.northstar.Northstar;
import com.lightning.northstar.block.NorthstarBlocks;
import com.lightning.northstar.block.NorthstarTechBlocks;
import com.lightning.northstar.block.tech.electrolysis_machine.ElectrolysisMachineBlockEntity;
import com.lightning.northstar.block.tech.electrolysis_machine.ElectrolysisMachineInstance;
import com.lightning.northstar.block.tech.electrolysis_machine.ElectrolysisMachineRenderer;
import com.lightning.northstar.block.tech.oxygen_concentrator.OxygenConcentratorBlockEntity;
import com.lightning.northstar.block.tech.oxygen_concentrator.OxygenConcentratorInstance;
import com.lightning.northstar.block.tech.oxygen_concentrator.OxygenConcentratorRenderer;
import com.lightning.northstar.block.tech.oxygen_detector.OxygenDetectorBlockEntity;
import com.lightning.northstar.block.tech.oxygen_filler.OxygenFillerBlockEntity;
import com.lightning.northstar.block.tech.oxygen_filler.OxygenFillerRenderer;
import com.lightning.northstar.block.tech.oxygen_generator.OxygenGeneratorBlockEntity;
import com.lightning.northstar.block.tech.oxygen_generator.OxygenGeneratorInstance;
import com.lightning.northstar.block.tech.oxygen_generator.OxygenGeneratorRenderer;
import com.lightning.northstar.block.tech.temperature_regulator.TemperatureRegulatorBlockEntity;
import com.lightning.northstar.block.tech.temperature_regulator.TemperatureRegulatorInstance;
import com.lightning.northstar.block.tech.temperature_regulator.TemperatureRegulatorRenderer;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.slidingDoor.SlidingDoorBlockEntity;
import com.simibubi.create.content.decoration.slidingDoor.SlidingDoorRenderer;
import com.simibubi.create.content.kinetics.base.ShaftInstance;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NorthstarBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Northstar.MOD_ID);
	
    public static final BlockEntityEntry<TemperatureRegulatorBlockEntity> TEMPERATURE_REGULATOR_BLOCK_ENTITY = REGISTRATE
 			.blockEntity("temperature_regulator", TemperatureRegulatorBlockEntity::new)
 			.instance(() -> TemperatureRegulatorInstance::new, false)
 			.validBlocks(NorthstarTechBlocks.TEMPERATURE_REGULATOR)
 			.renderer(() -> TemperatureRegulatorRenderer::new)
 			.register();
    public static final BlockEntityEntry<OxygenGeneratorBlockEntity> OXYGEN_GENERATOR = REGISTRATE
 			.blockEntity("oxygen_generator", OxygenGeneratorBlockEntity::new)
 			.instance(() -> OxygenGeneratorInstance::new, false)
 			.validBlocks(NorthstarTechBlocks.OXYGEN_GENERATOR)
 			.renderer(() -> OxygenGeneratorRenderer::new)
 			.register();
   public static final BlockEntityEntry<OxygenConcentratorBlockEntity> OXYGEN_CONCENTRATOR = REGISTRATE
			.blockEntity("oxygen_concentrator", OxygenConcentratorBlockEntity::new)
			.instance(() -> OxygenConcentratorInstance::new, false)
			.validBlocks(NorthstarTechBlocks.OXYGEN_CONCENTRATOR)
			.renderer(() -> OxygenConcentratorRenderer::new)
			.register();
   public static final BlockEntityEntry<OxygenFillerBlockEntity> OXYGEN_FILLER = REGISTRATE
			.blockEntity("oxygen_filler", OxygenFillerBlockEntity::new)
			.validBlocks(NorthstarTechBlocks.OXYGEN_FILLER)
			.renderer(() -> OxygenFillerRenderer::new)
			.register();
   public static final BlockEntityEntry<ElectrolysisMachineBlockEntity> ELECTROLYSIS_MACHINE = REGISTRATE
			.blockEntity("electrolysis_machine", ElectrolysisMachineBlockEntity::new)
 			.instance(() -> ElectrolysisMachineInstance::new, false)
			.validBlocks(NorthstarTechBlocks.ELECTROLYSIS_MACHINE)
			.renderer(() -> ElectrolysisMachineRenderer::new)
			.register();
   
   public static final BlockEntityEntry<OxygenDetectorBlockEntity> OXYGEN_DETECTOR = REGISTRATE
			.blockEntity("oxygen_detector", OxygenDetectorBlockEntity::new)
			.validBlocks(NorthstarTechBlocks.OXYGEN_DETECTOR)
			.register();

   public static final BlockEntityEntry<SlidingDoorBlockEntity> SPACE_DOORS =
			REGISTRATE.blockEntity("space_doors", SlidingDoorBlockEntity::new)
				.renderer(() -> SlidingDoorRenderer::new)
				.validBlocks(NorthstarTechBlocks.IRON_SPACE_DOOR)
				.register();
    
//   public static final BlockEntityEntry<IceBoxBlockEntity> ICE_BOX = REGISTRATE
//			.blockEntity("ice_box", IceBoxBlockEntity::new)
//			.validBlocks(NorthstarTechBlocks.ICE_BOX)
//			.renderer(() -> IceBoxRenderer::new)
//			.register();

//	public static final BlockEntityEntry<BracketedKineticBlockEntity> BRACKETED_KINETIC = REGISTRATE
//			.blockEntity("simple_kinetic", BracketedKineticBlockEntity::new)
//			.instance(() -> BracketedKineticBlockEntityInstance::new, false)
//			.validBlocks(NorthstarTechBlocks.IRON_COGWHEEL)
//			.renderer(() -> BracketedKineticBlockEntityRenderer::new)
//			.register();

//   public static final RegistryObject<BlockEntityType<JetEngineBlockEntity>> JET_ENGINE = BLOCK_ENTITIES.register("jet_engine",
//   		() -> BlockEntityType.Builder.of(JetEngineBlockEntity::new, NorthstarBlocks.JET_ENGINE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
