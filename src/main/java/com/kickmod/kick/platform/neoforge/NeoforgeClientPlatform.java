package com.kickmod.kick.platform.neoforge;

//? neoforge {

/*import com.kickmod.kick.client.ClientPlatform;
import com.kickmod.kick.net.KickRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
//? if >1.21.4 {
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
//?} else {
/^import net.neoforged.neoforge.network.PacketDistributor;
^///?}

public class NeoforgeClientPlatform implements ClientPlatform {

	@Override
	public boolean canSendKickRequest() {
		ClientPacketListener connection = Minecraft.getInstance().getConnection();
		return connection != null && connection.hasChannel(KickRequest.TYPE);
	}

	@Override
	public void sendKickRequest(int targetId) {
		//? if >1.21.4 {
		ClientPacketDistributor.sendToServer(new KickRequest(targetId));
		//?} else {
		/^PacketDistributor.sendToServer(new KickRequest(targetId));
		^///?}
	}
}
*///?}
