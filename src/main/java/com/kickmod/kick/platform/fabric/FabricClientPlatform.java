package com.kickmod.kick.platform.fabric;

//? fabric {

import com.kickmod.kick.client.ClientPlatform;
import com.kickmod.kick.net.KickRequest;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
//? if <1.20.5 {
/*import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
*///?}

public class FabricClientPlatform implements ClientPlatform {

	@Override
	public boolean canSendKickRequest() {
		//? if >=1.20.5 {
		return ClientPlayNetworking.canSend(KickRequest.TYPE);
		//?} else {
		/*return ClientPlayNetworking.canSend(KickRequest.ID);
		*///?}
	}

	@Override
	public void sendKickRequest(int targetId) {
		//? if >=1.20.5 {
		ClientPlayNetworking.send(new KickRequest(targetId));
		//?} else {
		/*FriendlyByteBuf buf = PacketByteBufs.create();
		new KickRequest(targetId).write(buf);
		ClientPlayNetworking.send(KickRequest.ID, buf);
		*///?}
	}
}
//?}
