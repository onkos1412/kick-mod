package com.kickmod.kick.platform.forge;

//? forge {

/*import com.kickmod.kick.Kick;
import com.kickmod.kick.client.KickClient;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;
//? if <= 1.21.5 {
/^import net.minecraftforge.eventbus.api.SubscribeEvent;
^///?} else {
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
//?}

/^* Client game-bus events. ^/
@Mod.EventBusSubscriber(modid = Kick.MOD_ID, value = Dist.CLIENT)
public class ForgeClientGameEvents {

	//? if >=1.21.6 {
	// EventBus 7: this event has its own bus and must not sit on the mod bus.
	@SubscribeEvent
	public static void onRegisterKeys(final net.minecraftforge.client.event.RegisterKeyMappingsEvent event) {
		event.register(com.kickmod.kick.client.KickKeys.KICK);
	}
	//?}

	//? if >=1.20.5 {
	@SubscribeEvent
	public static void onClientTick(final TickEvent.ClientTickEvent.Post event) {
		KickClient.onClientTick(Minecraft.getInstance());
	}
	//?} else {
	/^@SubscribeEvent
	public static void onClientTick(final TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) KickClient.onClientTick(Minecraft.getInstance());
	}
	^///?}
}
*///?}
