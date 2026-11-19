package com.kickmod.kick.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
//? if >=1.21.9 {
import com.kickmod.kick.KickRegistry;
//?}

/** The kick key (default V), shown under its own "Kick!" category in Controls. */
public final class KickKeys {

	//? if >=1.21.9 {
	public static final KeyMapping.Category CATEGORY = category();
	public static final KeyMapping KICK = new KeyMapping("key.kick.kick", InputConstants.KEY_V, CATEGORY);

	private static KeyMapping.Category category() {
		//? if neoforge {
		/*// NeoForge registers categories through RegisterKeyMappingsEvent#registerCategory.
		return new KeyMapping.Category(KickRegistry.id("kick"));
		*///?} else {
		return KeyMapping.Category.register(KickRegistry.id("kick"));
		//?}
	}
	//?} else {
	/*public static final KeyMapping KICK = new KeyMapping("key.kick.kick", InputConstants.KEY_V, "key.categories.kick");
	*///?}

	private KickKeys() {}
}
