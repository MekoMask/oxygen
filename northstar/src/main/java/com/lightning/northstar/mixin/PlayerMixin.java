package com.lightning.northstar.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
	private void attack(Entity pTarget, CallbackInfo info) {
        float f = (float) ((Player)(Object)this).getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1;
        if (pTarget instanceof LivingEntity) {
            f1 = EnchantmentHelper.getDamageBonus(((Player)(Object)this).getMainHandItem(), ((LivingEntity)pTarget).getMobType());
         } else {
            f1 = EnchantmentHelper.getDamageBonus(((Player)(Object)this).getMainHandItem(), MobType.UNDEFINED);
         }
    	float f2 = ((Player)(Object)this).getAttackStrengthScale(0.5F);
        f *= 0.2F + f2 * f2 * 0.8F;
        f1 *= f2;

	}
	
}
