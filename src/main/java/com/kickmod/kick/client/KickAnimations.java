package com.kickmod.kick.client;

import net.minecraft.client.Minecraft;

/** Client-side kick animations: who is kicking right now, and how far along. Client thread only. */
public final class KickAnimations {

	private static final KickAnimationState STATE = new KickAnimationState();

	private KickAnimations() {}

	/** The server says this player kicked (also echoes our own kicks; the state ignores the echo). */
	public static void start(int entityId) {
		STATE.start(entityId, now(0f));
		if (KickSelfTest.ENABLED) KickSelfTest.onAnimation(entityId);
	}

	/** The local player pressed the kick key: animate at once, without waiting for the server. */
	public static void startLocal() {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player != null) STATE.start(mc.player.getId(), now(0f));
		localKickNanos = System.nanoTime();
	}

	/** Real time of the local player's last kick key press (drives the camera shake), or -1. */
	private static long localKickNanos = -1L;

	/** Ticks (50 ms each, real time) since the local player last kicked; -1 if never. */
	public static float ticksSinceLocalKick() {
		if (localKickNanos < 0) return -1f;
		return (System.nanoTime() - localKickNanos) / 50_000_000f;
	}

	/** 0..1 while the entity is mid-kick, 0 otherwise. */
	public static float progress(int entityId, float partialTick) {
		return STATE.progress(entityId, now(partialTick));
	}

	private static float now(float partialTick) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.level == null) return 0f;
		// Keep the number small so float precision stays far below a tick (wrap-around just ends an animation).
		return (float) (mc.level.getGameTime() % 100_000L) + partialTick;
	}
}
