package com.kickmod.kick.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Per-player kick cooldowns, tracked by the server. Not thread-safe: use from the server thread only. */
public final class KickCooldowns {

	private final Map<UUID, Long> lastKick = new HashMap<>();

	/** Returns true (and records the kick) if the player's cooldown has elapsed at {@code gameTick}. */
	public boolean tryUse(UUID player, long gameTick, int cooldownTicks) {
		Long last = lastKick.get(player);
		// A clock that went backwards (last > now) never locks a player out.
		if (last != null && gameTick >= last && gameTick - last < cooldownTicks) return false;
		lastKick.put(player, gameTick);
		return true;
	}

	public void forget(UUID player) {
		lastKick.remove(player);
	}
}
