package com.kickmod.kick.client;

/** Added to LivingEntityRenderState (1.21.2+) by a mixin: carries the kick progress from entity to model. */
public interface KickRenderState {
	float kick$getProgress();

	void kick$setProgress(float progress);
}
