package com.lightning.northstar.sound;

import com.lightning.northstar.Northstar;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.geckolib.GeckoLib;

public class NorthstarSounds {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Northstar.MOD_ID);
	protected RegistryObject<SoundEvent> event;

//	public static final RegistryObject<SoundEvent> MARTIAN_DUST_STORM = registerSound("martian_dust_storm");
//	public static final RegistryObject<SoundEvent> MARTIAN_DUST_STORM_ABOVE = registerSound("martian_dust_storm_above");


//	public static final RegistryObject<SoundEvent> LASER_AMBIENT = registerSound("laser_ambient");
//	public static final RegistryObject<SoundEvent> LASER_BURN = registerSound("laser_burn");
//	public static final RegistryObject<SoundEvent> ROCKET_TAKEOFF = registerSound("rocket_takeoff");
//	public static final RegistryObject<SoundEvent> ROCKET_BLAST = registerSound("rocket_blast");
//	public static final RegistryObject<SoundEvent> ROCKET_LANDING = registerSound("rocket_landing");
	public static final RegistryObject<SoundEvent> AIRFLOW = registerSound("airflow");

	public static void register(IEventBus eventbus) {
		SOUNDS.register(eventbus);
	}

	public static RegistryObject<SoundEvent> registerSound(String name){
		ResourceLocation sound_loc = new ResourceLocation(Northstar.MOD_ID, name);
		return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(sound_loc));
	}



}