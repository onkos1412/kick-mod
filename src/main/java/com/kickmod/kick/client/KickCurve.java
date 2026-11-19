package com.kickmod.kick.client;

/** The shape of the kick: the leg swings forward fast, then comes back. Pure math, unit-tested. */
public final class KickCurve {

	/** Leg pitch at full extension, in radians (negative = forward in Minecraft's model space). */
	public static final float PEAK_PITCH = -1.4f;
	/** Fraction of the animation spent swinging forward. */
	public static final float PEAK_AT = 0.35f;

	private KickCurve() {}

	/** Leg pitch in radians for animation progress 0..1 (anything outside is "at rest"). */
	public static float legPitch(float progress) {
		if (progress <= 0f || progress >= 1f) return 0f;
		if (progress <= PEAK_AT) {
			float t = progress / PEAK_AT;
			return PEAK_PITCH * (1 - (1 - t) * (1 - t)); // ease out: snap forward
		}
		float t = (progress - PEAK_AT) / (1 - PEAK_AT);
		return PEAK_PITCH * (1 - t * t); // ease in: drift back, then drop
	}
}
