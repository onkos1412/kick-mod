package com.kickmod.kick.platform.neoforge;

//? neoforge {

/*import com.kickmod.kick.Kick;
import com.kickmod.kick.KickHandler;
import com.kickmod.kick.net.KickRequest;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;

@Mod(Kick.MOD_ID)
public class NeoforgeEntrypoint {

	public NeoforgeEntrypoint(IEventBus modBus) {
		Kick.init();
		modBus.addListener(NeoforgeEntrypoint::registerPayloads);
	}

	private static void registerPayloads(RegisterPayloadHandlersEvent event) {
		// optional(): players without the mod can still join, and the mod can join servers without it.
		event.registrar("1").optional().executesOn(HandlerThread.MAIN).playToServer(KickRequest.TYPE, KickRequest.STREAM_CODEC,
			(payload, context) -> {
				if (context.player() instanceof ServerPlayer player) KickHandler.onRequest(player, payload.targetId());
			});
	}
}
*///?}
