package com.kickmod.kick.client;

import java.util.HashMap;
import java.util.Map;

/**
 * Which entities are mid-kick, keyed by entity id, timed in client game ticks (with partial ticks).
 * Pure Java so it is unit-tested; used from the client thread only.
 */
public final class KickAnimationState {

	public static final float DURATION_TICKS = 6f;
	/** A second start within this window is the server echoing a kick we already animated locally. */
	private static final float RESTART_GUARD_TICKS = 4f;
	private static final int PRUNE_THRESHOLD = 64;

	private final Map<Integer, Float> starts = new HashMap<>();

	public void start(int entityId, float now) {
		Float started = starts.get(entityId);
		if (started != null && now >= started && now - started < RESTART_GUARD_TICKS) return;
		if (starts.size() >= PRUNE_THRESHOLD) {
			starts.values().removeIf(t -> now < t || now - t >= DURATION_TICKS);
		}
		starts.put(entityId, now);
	}

	/** 0..1 while the entity is kicking, 0 otherwise. */
	public float progress(int entityId, float now) {
		Float started = starts.get(entityId);
		if (started == null) return 0f;
		float p = (now - started) / DURATION_TICKS;
		if (p < 0f || p >= 1f) {
			starts.remove(entityId);
			return 0f;
		}
		return p;
	}

	int size() {
		return starts.size();
	}
}
