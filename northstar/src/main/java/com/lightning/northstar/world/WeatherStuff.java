package com.lightning.northstar.world;

import java.util.HashMap;
import java.util.Map.Entry;

import com.lightning.northstar.Northstar;
import com.mojang.datafixers.util.Pair;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;


@EventBusSubscriber(modid = Northstar.MOD_ID, bus = Bus.FORGE)
public class WeatherStuff {
	
	public enum WeatherCondition {
		CLEAR,
		RAINY,
		STORMY
	}


	public static HashMap<Pair<Pair<Direction, Direction>, Pair<WeatherCondition, Integer>>,ResourceKey<Level>> managedPlanets = new HashMap<Pair<Pair<Direction, Direction>, Pair<WeatherCondition, Integer>>, ResourceKey<Level>>();
	
//	@SubscribeEvent
//	public static void onWorldTick(TickEvent.LevelTickEvent event){
//		for(Entry<Pair<Pair<Direction, Direction>, Pair<WeatherCondition, Integer>>, ResourceKey<Level>> entries:	managedPlanets.entrySet()) {
//			for(Pair<Pair<Direction, Direction>, Pair<WeatherCondition, Integer>> entries2 : entries.getKey()) {
//				
//			}
//		}
//	}

	public Pair<Direction,Direction> getWindDirection(ResourceKey<Level> lev){
		if(managedPlanets.containsValue(lev)) {
			for(Entry<Pair<Pair<Direction, Direction>, Pair<WeatherCondition, Integer>>, ResourceKey<Level>> entries:	managedPlanets.entrySet()) {
				if(entries.getValue() == lev) {
					return entries.getKey().getFirst();
				}
				
			}
		}
		return null;
	}
	
	public WeatherCondition getWeatherConditions(ResourceKey<Level> lev) {
		if(managedPlanets.containsValue(lev)) {
			for(Entry<Pair<Pair<Direction, Direction>, Pair<WeatherCondition, Integer>>, ResourceKey<Level>> entries:	managedPlanets.entrySet()) {
				if(entries.getValue() == lev) {
					return entries.getKey().getSecond().getFirst();
				}
				
			}
		}
		return WeatherCondition.CLEAR;
	}

}
