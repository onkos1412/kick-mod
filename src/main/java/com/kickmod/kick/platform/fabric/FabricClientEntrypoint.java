package com.kickmod.kick.platform.fabric;

//? fabric {

import com.kickmod.kick.client.KickClient;
import com.kickmod.kick.client.KickKeys;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//? if >=26.1 {
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
//?} else {
/*import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
*///?}

public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		KickClient.init(new FabricClientPlatform());
		//? if >=26.1 {
		KeyMappingHelper.registerKeyMapping(KickKeys.KICK);
		//?} else {
		/*KeyBindingHelper.registerKeyBinding(KickKeys.KICK);
		*///?}
		ClientTickEvents.END_CLIENT_TICK.register(KickClient::onClientTick);
	}
}
//?}
