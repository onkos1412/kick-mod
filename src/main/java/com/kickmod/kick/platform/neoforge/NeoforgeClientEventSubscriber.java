package com.kickmod.kick.platform.neoforge;

//? neoforge {

/*import com.kickmod.kick.Kick;
import com.kickmod.kick.client.KickClient;
import com.kickmod.kick.client.KickKeys;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

/^* Client mod-bus events: setup and key registration. ^/
//? if <= 1.21.2 {
/^@EventBusSubscriber(modid = Kick.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
^///?} else {
@EventBusSubscriber(modid = Kick.MOD_ID, value = Dist.CLIENT)
//?}
public class NeoforgeClientEventSubscriber {
	@SubscribeEvent
	public static void onClientSetup(final FMLClientSetupEvent event) {
		KickClient.init(new NeoforgeClientPlatform());
	}

	@SubscribeEvent
	public static void onRegisterKeys(final RegisterKeyMappingsEvent event) {
		//? if >=1.21.9 {
		event.registerCategory(KickKeys.CATEGORY);
		//?}
		event.register(KickKeys.KICK);
	}
}
*///?}
