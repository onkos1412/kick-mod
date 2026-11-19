package com.kickmod.kick.platform.neoforge;

//? neoforge {

/*import com.kickmod.kick.platform.Platform;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.CommonHooks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.nio.file.Path;

public class NeoforgePlatform implements Platform {

	@Override
	public ModLoader loader() {
		return ModLoader.NEOFORGE;
	}

	@Override
	public boolean isClient() {
		//? if <= 1.21.6 {
		/^return FMLEnvironment.dist.isClient();
		^///?} else {
		return FMLEnvironment.getDist().isClient();
		//?}
	}

	@Override
	public Path configDir() {
		return FMLPaths.CONFIGDIR.get();
	}

	@Override
	public boolean allowPlayerAttack(Player attacker, Entity target) {
		return CommonHooks.onPlayerAttackTarget(attacker, target); // false when AttackEntityEvent was cancelled
	}
}
*///?}
