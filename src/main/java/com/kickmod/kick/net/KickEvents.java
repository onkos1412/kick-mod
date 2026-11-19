package com.kickmod.kick.net;

/**
 * Server to client: "this player kicked" is sent as a vanilla entity event
 * (Level#broadcastEntityEvent), so no extra channel is needed and clients without
 * the mod simply ignore the unknown id. Vanilla ids end at 72 (26.3).
 */
public final class KickEvents {
	public static final byte KICK_ANIMATION = 113;

	private KickEvents() {}
}
