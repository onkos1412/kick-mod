package com.kickmod.kick.mixin.client;

//? if >=1.21.2 {

import com.kickmod.kick.client.KickRenderState;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/** Gives every living render state a slot for the kick progress. */
@Mixin(LivingEntityRenderState.class)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
public abstract class LivingEntityRenderStateMixin implements KickRenderState {

	@Unique
	private float kick$progress;

	@Override
	public float kick$getProgress() {
		return kick$progress;
	}

	@Override
	public void kick$setProgress(float progress) {
		kick$progress = progress;
	}
}
//?}
