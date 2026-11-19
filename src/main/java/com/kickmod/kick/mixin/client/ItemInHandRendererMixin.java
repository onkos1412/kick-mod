package com.kickmod.kick.mixin.client;

//? if <26.3 {

/*import com.kickmod.kick.client.FirstPersonLegRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?} else {
/^import net.minecraft.client.renderer.MultiBufferSource;
^///?}

/^* Draws the kicking leg together with the first-person hands (up to 26.2). ^/
@Mixin(ItemInHandRenderer.class)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
public abstract class ItemInHandRendererMixin {

	//? if >=26.2 {
	@Inject(method = "submitHandsWithItems", at = @At("TAIL"))
	private void kick$renderLeg(float partialTick, PoseStack pose, SubmitNodeCollector out, LocalPlayer player, int light, CallbackInfo ci) {
		FirstPersonLegRenderer.render(pose, out, light, partialTick);
	}
	//?} else if >=1.21.9 {
	/^@Inject(method = "renderHandsWithItems", at = @At("TAIL"))
	private void kick$renderLeg(float partialTick, PoseStack pose, SubmitNodeCollector out, LocalPlayer player, int light, CallbackInfo ci) {
		FirstPersonLegRenderer.render(pose, out, light, partialTick);
	}
	^///?} else {
	/^// The hands buffer is flushed at the end of the method: draw the leg just before that.
	@Inject(method = "renderHandsWithItems",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endBatch()V"))
	private void kick$renderLeg(float partialTick, PoseStack pose, MultiBufferSource.BufferSource out, LocalPlayer player, int light, CallbackInfo ci) {
		FirstPersonLegRenderer.render(pose, out, light, partialTick);
	}
	^///?}
}
*///?}
