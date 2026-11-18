package com.kickmod.kick.logic;

/** Facts about one kick request, gathered from the world by the server. */
public record KickAttempt(boolean kickerAlive, boolean kickerSpectator, boolean targetExists, boolean targetIsSelf,
		boolean targetAlive, boolean targetIsLiving, boolean targetIsPlayer, boolean pvpAllowed, double distance,
		boolean lineOfSight) {
}
