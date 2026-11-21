package com.kickmod.kick.client;

/**
 * The kicker's camera shake: when the foot lands the view dips down and tilts toward the kicking leg,
 * then settles in a quick damped wobble. Deliberately small. Pure math, unit-tested.
 */
public final class KickShake {

	/** The foot reaches full extension about two ticks after the key press. */
	public static final float IMPACT_TICKS = 2f;
	public static final float DURATION_TICKS = 12f;
	public static final float MAX_PITCH_DEGREES = 1.5f;
	public static final float MAX_ROLL_DEGREES = 1.0f;

	private static final float DECAY_TICKS = 2.2f;
	private static final float HALF_PERIOD_TICKS = 2.5f;

	private KickShake() {}

	/**
	 * Camera offset {pitchDegrees (positive = look down), rollDegrees} at {@code ticksSinceKick} after the
	 * key press; {@code strength} scales it (the player's damage-tilt setting, 0 turns it off).
	 */
	public static float[] offsetDegrees(float ticksSinceKick, float strength) {
		if (strength <= 0f || ticksSinceKick < IMPACT_TICKS || ticksSinceKick >= DURATION_TICKS) return new float[] { 0f, 0f };
		float u = ticksSinceKick - IMPACT_TICKS;
		float wobble = (float) (Math.exp(-u / DECAY_TICKS) * Math.cos(u * Math.PI / HALF_PERIOD_TICKS)) * strength;
		return new float[] { MAX_PITCH_DEGREES * wobble, MAX_ROLL_DEGREES * wobble };
	}
}
