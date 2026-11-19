package com.kickmod.kick.platform.neoforge;

//? neoforge {

/*import com.kickmod.kick.Kick;
import com.kickmod.kick.client.KickClient;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
//? if >=1.20.5 {
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
//?} else {
/^import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;
^///?}

/^* Client game-bus events. ^/
//? if >=1.20.5 {
@EventBusSubscriber(modid = Kick.MOD_ID, value = Dist.CLIENT)
public class NeoforgeClientGameEvents {
	@SubscribeEvent
	public static void onClientTick(final ClientTickEvent.Post event) {
		KickClient.onClientTick(Minecraft.getInstance());
	}
}
//?} else {
/^@Mod.EventBusSubscriber(modid = Kick.MOD_ID, value = Dist.CLIENT)
public class NeoforgeClientGameEvents {
	@SubscribeEvent
	public static void onClientTick(final TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) KickClient.onClientTick(Minecraft.getInstance());
	}
}
^///?}
*///?}
