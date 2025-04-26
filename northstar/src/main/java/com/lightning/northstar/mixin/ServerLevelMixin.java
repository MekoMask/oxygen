package com.lightning.northstar.mixin;

import java.util.List;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//import com.lightning.northstar.world.dimension.NorthstarDimensions;
import com.lightning.northstar.world.dimension.NorthstarPlanets;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {


    @Inject(method = "tickChunk", at = @At("HEAD"))
    public void tickChunk(LevelChunk pChunk, int pRandomTickSpeed, CallbackInfo info) {
        ServerLevel level = (ServerLevel)(Object) this;
        if(level != null) {
            //if(level.dimension() == NorthstarDimensions.MARS_DIM_KEY) {
                //   		level.setRainLevel(15);
            //}
            //if(level.dimension() == NorthstarDimensions.VENUS_DIM_KEY) {
                //  		level.setRainLevel(15);
//                ChunkPos chunkpos = pChunk.getPos();
//                boolean flag = level.isRaining();
//                int i = chunkpos.getMinBlockX();
//                int j = chunkpos.getMinBlockZ();
//                ProfilerFiller profilerfiller = level.getProfiler();
//                profilerfiller.push("thunder");
//                if (flag && level.random.nextInt(15000) == 0) {
//
//                    // THUNDER TIME YEEHAW
//                    BlockPos blockpos = this.findLightningTargetAround(level.getBlockRandomPos(i, 0, j, 15));
//                    LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
//                    lightningbolt.moveTo(Vec3.atBottomCenterOf(blockpos));
//                    level.addFreshEntity(lightningbolt);
//                }
            }
        }

}