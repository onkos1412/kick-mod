package com.kickmod.kick.client;

import com.kickmod.kick.Kick;
import com.kickmod.kick.logic.KickConfig;
import com.kickmod.kick.logic.KickCooldowns;
import com.kickmod.kick.net.KickRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.EntityHitResult;

/** Client-side entry point: turns key presses into kick requests. */
public final class KickClient {

	/** Client-side cooldown only keeps the key from spamming packets; the server enforces the real one. */
	private static final KickCooldowns LOCAL_COOLDOWN = new KickCooldowns();

	private static ClientPlatform platform;

	/** Kick requests sent by the key (read by the dev self-test). */
	static int requestsSent;

	private KickClient() {}

	public static void init(ClientPlatform clientPlatform) {
		platform = clientPlatform;
		Kick.initClient();
	}

	public static ClientPlatform platform() {
		return platform;
	}

	/** Called once per client tick (end of tick) by every loader. */
	public static void onClientTick(Minecraft mc) {
		while (KickKeys.KICK.consumeClick()) {
			tryKick(mc);
		}
		if (KickSelfTest.ENABLED) KickSelfTest.tick(mc);
	}

	private static void tryKick(Minecraft mc) {
		LocalPlayer player = mc.player;
		if (player == null || mc.level == null || screen(mc) != null || player.isSpectator()) return;
		// Server without the mod: do nothing, quietly.
		if (platform == null || !platform.canSendKickRequest()) return;
		if (!LOCAL_COOLDOWN.tryUse(player.getUUID(), mc.level.getGameTime(), KickConfig.defaults().cooldownTicks())) return;

		int target = mc.hitResult instanceof EntityHitResult hit ? hit.getEntity().getId() : KickRequest.AIR;
		KickAnimations.startLocal();
		platform.sendKickRequest(target);
		requestsSent++;
	}

	static Screen screen(Minecraft mc) {
		//? if >=26.2 {
		return mc.gui.screen();
		//?} else {
		/*return mc.screen;
		*///?}
	}
}
