package com.kickmod.kick;

import net.minecraft.server.level.ServerPlayer;

/** Server-side kick handling. Called on the server thread. */
public final class KickHandler {

	private KickHandler() {}

	public static void onRequest(ServerPlayer kicker, int targetId) {
		Kick.LOGGER.info("Kick! request from {} for entity {}", kicker.getName().getString(), targetId);
	}
}
