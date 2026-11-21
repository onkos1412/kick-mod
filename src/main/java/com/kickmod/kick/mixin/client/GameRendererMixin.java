package com.kickmod.kick.mixin.client;

import com.kickmod.kick.client.KickCameraShake;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if >=26.1 {
import net.minecraft.client.renderer.state.level.CameraRenderState;
//?}

/** Shakes the kicker's camera slightly; bobHurt runs for both the world and the first-person hands.
 * HEAD, not TAIL: bobHurt returns early when the player is not hurt. */
@Mixin(GameRenderer.class)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
public abstract class GameRendererMixin {

	//? if >=26.1 {
	@Inject(method = "bobHurt", at = @At("HEAD"))
	private void kick$shake(CameraRenderState camera, PoseStack pose, CallbackInfo ci) {
		KickCameraShake.apply(pose);
	}
	//?} else {
	/*@Inject(method = "bobHurt", at = @At("HEAD"))
	private void kick$shake(PoseStack pose, float partialTick, CallbackInfo ci) {
		KickCameraShake.apply(pose);
	}
	*///?}
}
