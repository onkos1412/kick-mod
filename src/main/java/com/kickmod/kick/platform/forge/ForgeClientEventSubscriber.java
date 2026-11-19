package com.kickmod.kick.platform.forge;

//? forge {

/*import com.kickmod.kick.Kick;
import com.kickmod.kick.client.KickClient;
import com.kickmod.kick.client.KickKeys;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
//? if <= 1.21.5 {
/^import net.minecraftforge.eventbus.api.SubscribeEvent;
^///?} else {
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
//?}

/^* Client mod-bus events: setup and key registration. ^/
@Mod.EventBusSubscriber(modid = Kick.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeClientEventSubscriber {

	@SubscribeEvent
	public static void onClientSetup(final FMLClientSetupEvent event) {
		KickClient.init(new ForgeClientPlatform());
	}

	//? if < 1.21.6 {
	/^@SubscribeEvent
	public static void onRegisterKeys(final RegisterKeyMappingsEvent event) {
		event.register(KickKeys.KICK);
	}
	^///?}
	// From Forge 1.21.6 (EventBus 7) RegisterKeyMappingsEvent is no longer a mod-bus event: see ForgeClientGameEvents.
}
*///?}
