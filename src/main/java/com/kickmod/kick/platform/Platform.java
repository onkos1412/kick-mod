package com.kickmod.kick.platform;

import java.nio.file.Path;

/** The few things common code needs from the mod loader. */
public interface Platform {
	ModLoader loader();

	boolean isClient();

	/** The game's config directory (where kick.json lives). */
	Path configDir();

	enum ModLoader {
		FABRIC, NEOFORGE, FORGE
	}
}
