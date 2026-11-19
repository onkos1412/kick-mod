package com.kickmod.kick.mixin.client;

//? if >=1.21.2 {

import com.kickmod.kick.client.KickAnimations;
import com.kickmod.kick.client.KickRenderState;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Copies a player's kick progress into its render state (render states are reused, so always write it). */
@Mixin(LivingEntityRenderer.class)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
public abstract class LivingEntityRendererMixin {

	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V",
		at = @At("TAIL"))
	private void kick$extractKick(LivingEntity entity, LivingEntityRenderState state, float partialTick, CallbackInfo ci) {
		float progress = entity instanceof Player ? KickAnimations.progress(entity.getId(), partialTick) : 0f;
		((KickRenderState) state).kick$setProgress(progress);
	}
}
//?}
