package com.kickmod.kick.platform;

/** The few things common code needs from the mod loader. */
public interface Platform {
	ModLoader loader();

	boolean isClient();

	enum ModLoader {
		FABRIC, NEOFORGE, FORGE
	}
}
