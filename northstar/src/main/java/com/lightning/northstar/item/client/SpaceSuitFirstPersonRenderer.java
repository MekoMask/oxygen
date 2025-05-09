package com.lightning.northstar.item.client;

import com.lightning.northstar.NorthstarTags;
import com.lightning.northstar.client.NorthstarEntityResources;
import com.lightning.northstar.item.NorthstarItems;
import com.lightning.northstar.item.armor.IronSpaceSuitArmorItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class SpaceSuitFirstPersonRenderer{
	
	private static boolean rendererActive = false;
	private static ResourceLocation activeHand = NorthstarEntityResources.IRONSPACESUIT_ARMOR_ARM;
	
	public static void clientTick() {
		Minecraft mc = Minecraft.getInstance(); 
		rendererActive =
			mc.player != null && IronSpaceSuitArmorItem.hasChestplateOn(mc.player) && mc.player.getInventory().getArmor(2).is(NorthstarTags.NorthstarItemTags.OXYGEN_SOURCES.tag) || mc.player.getInventory().getArmor(2).getItem() == NorthstarItems.BROKEN_IRON_SPACE_SUIT_CHESTPIECE.get();
		if(mc.player != null && mc.player.getInventory().getArmor(2).getItem() == NorthstarItems.IRON_SPACE_SUIT_CHESTPIECE.get()) {
			activeHand = NorthstarEntityResources.IRONSPACESUIT_ARMOR_ARM;
		}
		if(mc.player != null && mc.player.getInventory().getArmor(2).getItem() == NorthstarItems.BROKEN_IRON_SPACE_SUIT_CHESTPIECE.get()) {
			activeHand = NorthstarEntityResources.BROKEN_IRONSPACESUIT_ARMOR_ARM;
		}
	}
    
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onRenderPlayerHand(RenderArmEvent event) {
		if (!rendererActive)
			return;

		Minecraft mc = Minecraft.getInstance();
		LocalPlayer player = mc.player;
		MultiBufferSource buffer = event.getMultiBufferSource();
		if (!(mc.getEntityRenderDispatcher()
			.getRenderer(player) instanceof PlayerRenderer pr))
			return;

		PlayerModel<AbstractClientPlayer> model = pr.getModel();
		model.attackTime = 0.0F;
		model.crouching = false;
		model.swimAmount = 0.0F;
		model.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		ModelPart armPart = event.getArm() == HumanoidArm.LEFT ? model.leftSleeve : model.rightSleeve;
		armPart.xRot = 0.0F;
		armPart.render(event.getPoseStack(), buffer.getBuffer(RenderType.entitySolid(activeHand)),
			LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
		event.setCanceled(true);
	}
	
}
