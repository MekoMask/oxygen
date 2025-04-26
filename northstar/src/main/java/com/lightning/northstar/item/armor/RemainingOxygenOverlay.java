package com.lightning.northstar.item.armor;

import com.lightning.northstar.world.OxygenStuff;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.foundation.utility.Components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class RemainingOxygenOverlay implements IGuiOverlay {
	public static final RemainingOxygenOverlay INSTANCE = new RemainingOxygenOverlay();

	@Override
	public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int width, int height) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.options.hideGui || mc.gameMode.getPlayerMode() == GameType.SPECTATOR)
			return;

		LocalPlayer player = mc.player;
		if (player == null || player.isCreative())
			return;

		PoseStack poseStack = graphics.pose();
		poseStack.pushPose();

		ItemStack oxygenTank = OxygenStuff.getOxy(player);
		if (oxygenTank.isEmpty())
			return;

		int oxygenLevel = oxygenTank.getOrCreateTag().getInt("Oxygen");
		poseStack.translate(width / 2 + 90, height - 53, 0);

		Component text = Components.literal(oxygenLevel + "mb");
		GuiGameElement.of(oxygenTank).at(0, 0).render(graphics);
		int color = oxygenLevel < 60 ? Color.mixColors(0xFF_FF0000, 0xFF_FFFFFF, Math.max(oxygenLevel / 60f, .25f)) : 0xFF_FFFFFF;
		graphics.drawString(mc.font, text, 16, 5, color);

		poseStack.popPose();
	}
}