package com.kickmod.kick;

import com.kickmod.kick.platform.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//? fabric {
import com.kickmod.kick.platform.fabric.FabricPlatform;
//?} neoforge {
/*import com.kickmod.kick.platform.neoforge.NeoforgePlatform;
 *///?} forge {
/*import com.kickmod.kick.platform.forge.ForgePlatform;
*///?}

public final class Kick {

	public static final String MOD_ID = "kick";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	private static final Platform PLATFORM = createPlatformInstance();

	private Kick() {}

	/** Common entry point, called by every loader's main entrypoint. */
	public static void init() {
		LOGGER.info("Kick! loaded on {}", PLATFORM.loader());
	}

	/** Client entry point, called by every loader's client setup. */
	public static void initClient() {
		LOGGER.info("Kick! client initialised");
	}

	public static Platform platform() {
		return PLATFORM;
	}

	private static Platform createPlatformInstance() {
		//? fabric {
		return new FabricPlatform();
		//?} neoforge {
		/*return new NeoforgePlatform();
		 *///?} forge {
		/*return new ForgePlatform();
		*///?}
	}
}
