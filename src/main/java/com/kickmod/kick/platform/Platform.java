package com.kickmod.kick.platform;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.nio.file.Path;

/** The few things common code needs from the mod loader. */
public interface Platform {
	ModLoader loader();

	boolean isClient();

	/** The game's config directory (where kick.json lives). */
	Path configDir();

	/**
	 * Fires the loader's "player attacks entity" event, exactly as a melee hit would, so land-claim and
	 * PvP mods can veto the kick. Returns false if any listener cancelled it.
	 */
	boolean allowPlayerAttack(Player attacker, Entity target);

	enum ModLoader {
		FABRIC, NEOFORGE, FORGE
	}
}
