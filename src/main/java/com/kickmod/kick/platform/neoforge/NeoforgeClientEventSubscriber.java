package com.kickmod.kick.platform.neoforge;

//? neoforge {

/*import com.kickmod.kick.Kick;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

//? if <= 1.21.2 {
/^@EventBusSubscriber(modid = Kick.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
^///?} else {
@EventBusSubscriber(modid = Kick.MOD_ID, value = Dist.CLIENT)
//?}
public class NeoforgeClientEventSubscriber {
	@SubscribeEvent
	public static void onClientSetup(final FMLClientSetupEvent event) {
		Kick.initClient();
	}
}
*///?}
