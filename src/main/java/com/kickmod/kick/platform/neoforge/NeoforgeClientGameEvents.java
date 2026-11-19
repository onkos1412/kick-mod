package com.kickmod.kick.platform.neoforge;

//? neoforge {

/*import com.kickmod.kick.Kick;
import com.kickmod.kick.client.KickClient;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

/^* Client game-bus events. ^/
@EventBusSubscriber(modid = Kick.MOD_ID, value = Dist.CLIENT)
public class NeoforgeClientGameEvents {
	@SubscribeEvent
	public static void onClientTick(final ClientTickEvent.Post event) {
		KickClient.onClientTick(Minecraft.getInstance());
	}
}
*///?}
