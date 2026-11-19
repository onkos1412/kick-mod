package com.kickmod.kick.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Per-player kick cooldowns, tracked by the server. Not thread-safe: use from the server thread only. */
public final class KickCooldowns {

	/** Above this many entries, expired ones are dropped (players who left are never forgotten explicitly). */
	private static final int PRUNE_THRESHOLD = 64;

	private final Map<UUID, Long> lastKick = new HashMap<>();

	/** Returns true (and records the kick) if the player's cooldown has elapsed at {@code gameTick}. */
	public boolean tryUse(UUID player, long gameTick, int cooldownTicks) {
		Long last = lastKick.get(player);
		if (!expired(last, gameTick, cooldownTicks)) return false;
		if (lastKick.size() >= PRUNE_THRESHOLD) {
			lastKick.values().removeIf(t -> expired(t, gameTick, cooldownTicks));
		}
		lastKick.put(player, gameTick);
		return true;
	}

	/** A clock that went backwards (last > now) counts as expired, so it never locks a player out. */
	private static boolean expired(Long last, long now, int cooldownTicks) {
		return last == null || now < last || now - last >= cooldownTicks;
	}

	int size() {
		return lastKick.size();
	}

	public void forget(UUID player) {
		lastKick.remove(player);
	}
}
