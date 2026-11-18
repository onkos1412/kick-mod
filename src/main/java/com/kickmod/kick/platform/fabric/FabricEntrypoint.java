package com.kickmod.kick.platform.fabric;

//? fabric {

import com.kickmod.kick.Kick;
import net.fabricmc.api.ModInitializer;

public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		Kick.init();
	}
}
//?}
