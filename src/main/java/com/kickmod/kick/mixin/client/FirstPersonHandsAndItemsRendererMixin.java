package com.kickmod.kick.mixin.client;

//? if >=26.3 {

import com.kickmod.kick.client.FirstPersonLegRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** 26.3+: first-person hands moved to their own renderer; draw the kicking leg with them. */
@Mixin(FirstPersonHandsAndItemsRenderer.class)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
public abstract class FirstPersonHandsAndItemsRendererMixin {

	@Inject(method = "submitHandsWithItems", at = @At("TAIL"))
	private void kick$renderLeg(float partialTick, PoseStack pose, SubmitNodeCollector out, PlayerRenderState player,
			FirstPersonHandsAndItemsRenderState hands, CallbackInfo ci) {
		FirstPersonLegRenderer.render(pose, out, player.avatarRenderState.lightCoords, partialTick);
	}
}
//?}
