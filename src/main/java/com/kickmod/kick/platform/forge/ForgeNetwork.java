package com.kickmod.kick.platform.forge;

//? forge {

/*import com.kickmod.kick.KickHandler;
import com.kickmod.kick.KickRegistry;
import com.kickmod.kick.net.KickRequest;
import net.minecraft.server.level.ServerPlayer;
//? if >=1.20.5 {
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;
//?} else {
/^import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
^///?}

/^* The kick channel. Optional on both sides so peers without the mod can still connect. ^/
public final class ForgeNetwork {

	public static SimpleChannel CHANNEL;

	private ForgeNetwork() {}

	public static void register() {
		//? if >=1.20.5 {
		CHANNEL = ChannelBuilder.named(KickRegistry.id("main")).networkProtocolVersion(1).optional().simpleChannel()
			.messageBuilder(KickRequest.class, NetworkDirection.PLAY_TO_SERVER)
			.encoder(KickRequest::write)
			.decoder(KickRequest::read)
			.consumerMainThread((msg, context) -> {
				ServerPlayer player = context.getSender();
				if (player != null) KickHandler.onRequest(player, msg.targetId());
			})
			.add();
		//?} else {
		/^CHANNEL = NetworkRegistry.ChannelBuilder.named(KickRegistry.id("main"))
			.networkProtocolVersion(() -> "1")
			.clientAcceptedVersions(NetworkRegistry.acceptMissingOr("1"))
			.serverAcceptedVersions(NetworkRegistry.acceptMissingOr("1"))
			.simpleChannel();
		CHANNEL.messageBuilder(KickRequest.class, 0, NetworkDirection.PLAY_TO_SERVER)
			.encoder(KickRequest::write)
			.decoder(KickRequest::read)
			.consumerMainThread((msg, context) -> {
				ServerPlayer player = context.get().getSender();
				if (player != null) KickHandler.onRequest(player, msg.targetId());
			})
			.add();
		^///?}
	}
}
*///?}
