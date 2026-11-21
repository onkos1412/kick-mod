package com.kickmod.kick.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

/** Applies {@link KickShake} to the camera, right after vanilla's hurt tilt. */
public final class KickCameraShake {

	private KickCameraShake() {}

	public static void apply(PoseStack pose) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null) return;
		// Respect the player's comfort settings: no view bobbing means no shake, and the
		// damage-tilt slider (0..1, accessibility) scales it.
		if (!mc.options.bobView().get()) return;
		float strength = mc.options.damageTiltStrength().get().floatValue();
		float[] offset = KickShake.offsetDegrees(KickAnimations.ticksSinceLocalKick(), strength);
		if (offset[0] == 0f && offset[1] == 0f) return;

		float pitch = (float) Math.toRadians(offset[0]); // positive = view dips down
		float roll = (float) Math.toRadians(offset[1]);
		PoseStack.Pose last = pose.last();
		last.pose().rotateZ(roll).rotateX(pitch);
		last.normal().rotateZ(roll).rotateX(pitch);
		if (KickSelfTest.ENABLED) KickSelfTest.shakeFrames++;
	}
}
