package com.lightning.northstar.mixin;

import com.lightning.northstar.world.OxygenStuff;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class OxygenAndTempEntityMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    public void manageOxygenAndTemperature(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!(entity instanceof Player player) || player.isCreative())
            return;

        boolean hasAir = OxygenStuff.checkForAir(entity);
        if (!hasAir) {
            OxygenStuff.depleteOxy(OxygenStuff.getOxy(entity));
        }
    }
}