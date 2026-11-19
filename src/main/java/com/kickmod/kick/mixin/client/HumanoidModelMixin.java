package com.kickmod.kick.mixin.client;

import com.kickmod.kick.client.KickCurve;
import com.kickmod.kick.client.KickSelfTest;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if >=1.21.2 {
import com.kickmod.kick.client.KickRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
//?} else {
/*import com.kickmod.kick.client.KickAnimations;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
*///?}

/** Swings the right leg of a kicking player (third person, and what other players see). */
@Mixin(HumanoidModel.class)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
public abstract class HumanoidModelMixin {

	@Shadow
	@Final
	public ModelPart rightLeg;

	//? if >=1.21.2 {
	@Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At("TAIL"))
	private void kick$swingLeg(HumanoidRenderState state, CallbackInfo ci) {
		float progress = ((KickRenderState) state).kick$getProgress();
		if (progress > 0f) {
			rightLeg.xRot = KickCurve.legPitch(progress);
			if (KickSelfTest.ENABLED) KickSelfTest.legFramesAnimated++;
		}
	}
	//?} else {
	/*@Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
	private void kick$swingLeg(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch, CallbackInfo ci) {
		if (!(entity instanceof Player)) return;
		float partialTick = ageInTicks - entity.tickCount;
		// The first-person arm calls setupAnim(player, 0, 0, 0, 0, 0): not a world render, and its
		// "time" would read as a clock jump that wipes the kick. Only real renders have 0 <= partial < 1.
		if (partialTick < 0f || partialTick >= 1f) return;
		float progress = KickAnimations.progress(entity.getId(), partialTick);
		// PlayerModel copies the leg onto the pants layer after this returns.
		if (progress > 0f) {
			rightLeg.xRot = KickCurve.legPitch(progress);
			if (KickSelfTest.ENABLED) KickSelfTest.legFramesAnimated++;
		}
	}
	*///?}
}
