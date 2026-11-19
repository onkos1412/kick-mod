package com.kickmod.kick.client;

import com.kickmod.kick.Kick;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Screenshot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.BackupConfirmScreen;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.phys.AABB;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Development-only end-to-end test, enabled with {@code -Dkick.selftest=true} (never in normal play).
 * Runs in a singleplayer world: presses the real kick key and checks damage, knockback, animation,
 * client and server cooldowns and reach, logging "[kick-selftest] PASS/FAIL ..." lines, then quits.
 */
public final class KickSelfTest {

	public static final boolean ENABLED = Boolean.getBoolean("kick.selftest");

	private static int ticks;
	private static int waitingTicks;
	private static boolean backupSkipped;
	private static int nextStepAt = 100; // let the world settle first
	private static int step;
	private static boolean sawOwnAnimation;
	/** Frames in which the player model applied a kick pose (incremented by HumanoidModelMixin). */
	public static int legFramesAnimated;
	/** Frames in which the first-person leg was drawn (incremented by FirstPersonLegRenderer). */
	public static int firstPersonLegFrames;

	private KickSelfTest() {}

	private static void clearChat(Minecraft mc) {
		//? if <26.2 {
		/*mc.gui.getChat().clearMessages(false);
		*///?}
	}

	private static void setHideGui(Minecraft mc, boolean hide) {
		//? if <26.2 {
		/*mc.options.hideGui = hide;
		*///?}
	}

	private static void screenshot(Minecraft mc) {
		//? if >=26.2 {
		Screenshot.grab(mc, false);
		//?} else {
		/*Screenshot.grab(mc.gameDirectory, mc.getMainRenderTarget(), message -> {});
		*///?}
		log("screenshot taken mid-kick");
	}

	static void onAnimation(int entityId) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player != null && entityId == mc.player.getId()) sawOwnAnimation = true;
	}

	static void tick(Minecraft mc) {
		if (mc.player == null || mc.level == null || mc.getSingleplayerServer() == null) {
			var screen = KickClient.screen(mc);
			// Forge asks to back up a world copied from a dedicated server: load it without a backup.
			if (screen instanceof BackupConfirmScreen && !backupSkipped) {
				backupSkipped = true; // once: proceeding twice opens the world twice
				skipBackup(screen);
			}
			if (++waitingTicks % 100 == 0) {
				log("waiting for the world, screen: " + (screen == null ? "none" : screen.getClass().getName()));
			}
			return;
		}
		if (++ticks < nextStepAt) return;
		switch (step++) {
			case 0 -> {
				run(mc, "difficulty peaceful");
				run(mc, "gamemode survival @a");
				run(mc, "kill @e[type=!player]");
				// stand at the origin facing south (+Z), looking 30 degrees down at a pig 2 blocks ahead
				run(mc, "tp @a 0.5 -60 0.5 0 30");
				run(mc, "summon pig 0.5 -60 2.5 {Tags:[\"kst\"]}");
				run(mc, "summon pig 0.5 -60 9.5 {Tags:[\"kst_far\"],NoAI:1b}");
				wait(20);
			}
			case 1 -> {
				log("crosshair on " + (mc.hitResult == null ? "nothing" : mc.hitResult.getType()));
				KeyMapping.click(KickKeys.KICK.getDefaultKey()); // a real key press through the binding
				wait(5);
			}
			case 2 -> {
				// tick T+5 after the first kick
				check(mc, "@e[tag=kst,nbt={Health:8.0f}]", "key kick took exactly 1 heart from the pig");
				log((sawOwnAnimation ? "PASS" : "FAIL") + " kick animation event reached the client");
				KeyMapping.click(KickKeys.KICK.getDefaultKey()); // pressed again at once
				wait(1);
			}
			case 3 -> {
				log((KickClient.requestsSent == 1 ? "PASS" : "FAIL") + " client cooldown kept the second key press from being sent");
				// T+7: bypass the client cooldown; the server must refuse (7 < 20 ticks). Invulnerability
				// would also block until T+10, so wait for T+14 to make this check discriminating.
				wait(7);
			}
			case 4 -> {
				// T+14: the flight is over; started at z=2.5, a plain hit moves a pig well under a block
				check(mc, "@e[tag=kst,x=-10,y=-70,z=3.6,dx=20,dy=30,dz=40]", "kicked pig flew south (knockback)");
				run(mc, "tp @e[tag=kst] 0.5 -60 2.5");
				send(nearestPig(mc, false)); // T+14
				wait(5);
			}
			case 5 -> {
				check(mc, "@e[tag=kst,nbt={Health:8.0f}]", "server cooldown refused a request sent after 14 ticks");
				wait(25);
			}
			case 6 -> {
				run(mc, "tp @e[tag=kst] 0.5 -60 2.5");
				send(nearestPig(mc, false));
				wait(10);
			}
			case 7 -> {
				check(mc, "@e[tag=kst,nbt={Health:6.0f}]", "kick after the cooldown landed again");
				wait(25);
			}
			case 8 -> {
				send(nearestPig(mc, true)); // far pig, 9 blocks away
				wait(10);
			}
			case 9 -> {
				check(mc, "@e[tag=kst_far,nbt={Health:10.0f}]", "server refused a kick from 9 blocks away");
				// third person, facing the camera, for the leg animation check and screenshot
				run(mc, "kill @e[type=!player]"); // pigs and the porkchops they dropped
				run(mc, "clear @a");
				mc.options.setCameraType(CameraType.THIRD_PERSON_FRONT);
				run(mc, "tp @a 0.5 -60 0.5 30 0");
				wait(30);
			}
			case 10 -> {
				legFramesAnimated = 0;
				setHideGui(mc, true); // chat would cover the player in the screenshot
				KeyMapping.click(KickKeys.KICK.getDefaultKey()); // air kick
				wait(3); // the key is read next tick; the swing peaks ~2 ticks later
			}
			case 11 -> {
				screenshot(mc);
				wait(8);
			}
			case 12 -> {
				log((legFramesAnimated > 0 ? "PASS" : "FAIL") + " player model swung the leg (" + legFramesAnimated + " frames)");
				mc.options.setCameraType(CameraType.FIRST_PERSON);
				setHideGui(mc, false); // with the GUI hidden the game skips first-person hands (and the leg)
				run(mc, "tp @a 0.5 -60 0.5 0 0"); // look straight ahead
				wait(25);
			}
			case 13 -> {
				firstPersonLegFrames = 0;
				clearChat(mc); // the test's own chat lines would cover the leg in the screenshot
				KeyMapping.click(KickKeys.KICK.getDefaultKey());
				wait(3);
			}
			case 14 -> {
				screenshot(mc);
				wait(8);
			}
			case 15 -> {
				log((firstPersonLegFrames > 0 ? "PASS" : "FAIL") + " first-person leg was drawn (" + firstPersonLegFrames + " frames)");
				setHideGui(mc, false);
				log("done");
				wait(20);
			}
			default -> mc.stop();
		}
	}

	/** The listener field was renamed across versions (listener / onProceed), so find it by type. */
	private static void skipBackup(Object screen) {
		try {
			for (Field f : BackupConfirmScreen.class.getDeclaredFields()) {
				if (f.getType() == BackupConfirmScreen.Listener.class) {
					f.setAccessible(true);
					((BackupConfirmScreen.Listener) f.get(screen)).proceed(false, false);
					log("skipped the world backup prompt");
					return;
				}
			}
		} catch (ReflectiveOperationException e) {
			log("FAIL could not skip the backup prompt: " + e);
		}
	}

	private static Entity nearestPig(Minecraft mc, boolean far) {
		AABB area = new AABB(-20, -70, -20, 20, -40, 20);
		List<Entity> pigs = mc.level.getEntities((Entity) null, area, e -> BuiltInRegistries.ENTITY_TYPE.getKey(e.getType()).getPath().equals("pig"));
		Entity best = null;
		for (Entity e : pigs) {
			if ((e.getZ() > 8) != far) continue;
			if (best == null || e.distanceToSqr(mc.player) < best.distanceToSqr(mc.player)) best = e;
		}
		return best;
	}

	private static void send(Entity pig) {
		if (pig == null) {
			log("FAIL test pig not found on the client");
			return;
		}
		KickClient.platform().sendKickRequest(pig.getId());
	}

	private static void check(Minecraft mc, String selector, String what) {
		run(mc, "execute if entity " + selector + " run say [kick-selftest] PASS " + what);
		run(mc, "execute unless entity " + selector + " run say [kick-selftest] FAIL " + what);
	}

	private static void run(Minecraft mc, String command) {
		IntegratedServer server = mc.getSingleplayerServer();
		server.execute(() -> server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), command));
	}

	private static void wait(int t) {
		nextStepAt = ticks + t;
	}

	private static void log(String message) {
		Kick.LOGGER.info("[kick-selftest] {}", message);
	}
}
