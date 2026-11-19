package com.kickmod.kick.platform.fabric;

//? fabric {

import com.kickmod.kick.Kick;
import com.kickmod.kick.KickHandler;
import com.kickmod.kick.net.KickRequest;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
//? if >=1.20.5 {
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
//?}

public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		Kick.init();
		registerNetworking();
	}

	private static void registerNetworking() {
		//? if >=26.1 {
		PayloadTypeRegistry.serverboundPlay().register(KickRequest.TYPE, KickRequest.STREAM_CODEC);
		//?} else if >=1.20.5 {
		/*PayloadTypeRegistry.playC2S().register(KickRequest.TYPE, KickRequest.STREAM_CODEC);
		*///?}
		//? if >=1.20.5 {
		// Fabric runs play payload handlers on the server thread.
		ServerPlayNetworking.registerGlobalReceiver(KickRequest.TYPE,
			(payload, context) -> KickHandler.onRequest(context.player(), payload.targetId()));
		//?} else {
		/*ServerPlayNetworking.registerGlobalReceiver(KickRequest.ID, (server, player, handler, buf, responseSender) -> {
			KickRequest request = KickRequest.read(buf);
			server.execute(() -> KickHandler.onRequest(player, request.targetId()));
		});
		*///?}
	}
}
//?}
