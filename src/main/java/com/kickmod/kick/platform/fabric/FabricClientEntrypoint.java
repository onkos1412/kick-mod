package com.kickmod.kick.platform.fabric;

//? fabric {

import com.kickmod.kick.client.KickClient;
import net.fabricmc.api.ClientModInitializer;

public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		KickClient.init(new FabricClientPlatform());
	}
}
//?}
