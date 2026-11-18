package com.kickmod.kick.platform.fabric;

//? fabric {

import com.kickmod.kick.Kick;
import net.fabricmc.api.ClientModInitializer;

public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		Kick.initClient();
	}
}
//?}
