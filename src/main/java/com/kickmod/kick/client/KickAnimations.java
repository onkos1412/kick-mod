package com.kickmod.kick.client;

import com.kickmod.kick.Kick;

/** Kick animation state per entity (filled in by the animation task). */
public final class KickAnimations {

	private KickAnimations() {}

	/** A player (by entity id) kicked; called on the client thread when the server's kick event arrives. */
	public static void start(int entityId) {
		Kick.LOGGER.debug("Kick! animation event for entity {}", entityId);
	}
}
