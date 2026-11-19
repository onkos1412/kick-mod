package com.kickmod.kick.platform.neoforge;

//? neoforge {

/*import com.kickmod.kick.client.ClientPlatform;
import com.kickmod.kick.net.KickRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
//? if >=1.21.9 {
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
//?} else if >=1.20.5 {
/^import net.neoforged.neoforge.network.PacketDistributor;
^///?} else {
/^import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.registration.NetworkRegistry;
^///?}

public class NeoforgeClientPlatform implements ClientPlatform {

	@Override
	public boolean canSendKickRequest() {
		ClientPacketListener connection = Minecraft.getInstance().getConnection();
		//? if >=1.20.5 {
		return connection != null && connection.hasChannel(KickRequest.TYPE);
		//?} else {
		/^return connection != null && NetworkRegistry.getInstance().isConnected(connection, KickRequest.ID);
		^///?}
	}

	@Override
	public void sendKickRequest(int targetId) {
		//? if >=1.21.9 {
		ClientPacketDistributor.sendToServer(new KickRequest(targetId));
		//?} else if >=1.20.5 {
		/^PacketDistributor.sendToServer(new KickRequest(targetId));
		^///?} else {
		/^PacketDistributor.SERVER.noArg().send(new KickRequest(targetId));
		^///?}
	}
}
*///?}
