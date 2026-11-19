package com.kickmod.kick.platform.forge;

//? forge {

/*import com.kickmod.kick.client.ClientPlatform;
import com.kickmod.kick.net.KickRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
//? if >=1.20.5 {
import net.minecraftforge.network.PacketDistributor;
//?}

public class ForgeClientPlatform implements ClientPlatform {

	@Override
	public boolean canSendKickRequest() {
		ClientPacketListener connection = Minecraft.getInstance().getConnection();
		return connection != null && ForgeNetwork.CHANNEL.isRemotePresent(connection.getConnection());
	}

	@Override
	public void sendKickRequest(int targetId) {
		//? if >=1.20.5 {
		ForgeNetwork.CHANNEL.send(new KickRequest(targetId), PacketDistributor.SERVER.noArg());
		//?} else {
		/^ForgeNetwork.CHANNEL.sendToServer(new KickRequest(targetId));
		^///?}
	}
}
*///?}
