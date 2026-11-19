package com.kickmod.kick.platform.neoforge;

//? neoforge {

/*import com.kickmod.kick.Kick;
import com.kickmod.kick.KickHandler;
import com.kickmod.kick.net.KickRequest;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
//? if >=1.20.5 {
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
//?} else {
/^import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
^///?}

@Mod(Kick.MOD_ID)
public class NeoforgeEntrypoint {

	public NeoforgeEntrypoint(IEventBus modBus) {
		Kick.init();
		modBus.addListener(NeoforgeEntrypoint::registerPayloads);
	}

	// optional(): players without the mod can still join, and the mod can join servers without it.
	//? if >=1.20.5 {
	private static void registerPayloads(RegisterPayloadHandlersEvent event) {
		event.registrar("1").optional().executesOn(HandlerThread.MAIN).playToServer(KickRequest.TYPE, KickRequest.STREAM_CODEC,
			(payload, context) -> {
				if (context.player() instanceof ServerPlayer player) KickHandler.onRequest(player, payload.targetId());
			});
	}
	//?} else {
	/^private static void registerPayloads(RegisterPayloadHandlerEvent event) {
		event.registrar(Kick.MOD_ID).optional().play(KickRequest.ID, KickRequest::read,
			// NeoForge 1.20.4 calls this on the network thread: hop to the server thread.
			(payload, context) -> context.workHandler().execute(() -> context.player().ifPresent(player -> {
				if (player instanceof ServerPlayer serverPlayer) KickHandler.onRequest(serverPlayer, payload.targetId());
			})));
	}
	^///?}
}
*///?}
