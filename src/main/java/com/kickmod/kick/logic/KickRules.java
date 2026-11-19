package com.kickmod.kick.logic;

/** Server-authoritative kick rules. No Minecraft imports. */
public final class KickRules {

	/** Extra reach allowed on top of the config value, to absorb ping. */
	public static final double REACH_TOLERANCE = 1.0;
	/** Vanilla base knockback strength. */
	public static final double BASE_KNOCKBACK = 0.4;

	private KickRules() {}

	public static KickDenial check(KickAttempt a, KickConfig c) {
		if (!a.kickerAlive() || a.kickerSpectator()) return KickDenial.KICKER_INVALID;
		if (!a.targetExists()) return KickDenial.NO_TARGET;
		if (a.targetIsSelf()) return KickDenial.SELF;
		if (!a.targetIsLiving()) return KickDenial.NOT_LIVING;
		if (!a.targetAlive()) return KickDenial.TARGET_DEAD;
		if (a.targetIsPlayer() && (!c.allowKickPlayers() || !a.pvpAllowed())) return KickDenial.PVP_DISABLED;
		if (a.distance() > c.reach() + REACH_TOLERANCE) return KickDenial.TOO_FAR;
		if (!a.lineOfSight()) return KickDenial.NO_LINE_OF_SIGHT;
		return KickDenial.NONE;
	}

	/** Distance from a point (the kicker's eyes) to the nearest point of an axis-aligned box (the target's hitbox). */
	public static double distanceToBox(double px, double py, double pz,
			double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		double dx = Math.max(0, Math.max(minX - px, px - maxX));
		double dy = Math.max(0, Math.max(minY - py, py - maxY));
		double dz = Math.max(0, Math.max(minZ - pz, pz - maxZ));
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	/** Knockback velocity {x, y, z} in the direction the kicker faces (Minecraft yaw convention). */
	public static double[] knockback(float yawDegrees, KickConfig c) {
		double rad = Math.toRadians(yawDegrees);
		double strength = BASE_KNOCKBACK * c.knockbackMultiplier();
		return new double[] { -Math.sin(rad) * strength, c.knockbackUp(), Math.cos(rad) * strength };
	}
}
