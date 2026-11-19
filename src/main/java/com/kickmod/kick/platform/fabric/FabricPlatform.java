package com.kickmod.kick.platform.fabric;

//? fabric {

import com.kickmod.kick.platform.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.nio.file.Path;

public class FabricPlatform implements Platform {

	@Override
	public ModLoader loader() {
		return ModLoader.FABRIC;
	}

	@Override
	public boolean isClient() {
		return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
	}

	@Override
	public Path configDir() {
		return FabricLoader.getInstance().getConfigDir();
	}

	@Override
	public boolean allowPlayerAttack(Player attacker, Entity target) {
		// Same contract as vanilla's attack path: anything but PASS stops the attack.
		InteractionResult result = AttackEntityCallback.EVENT.invoker()
			.interact(attacker, attacker.level(), InteractionHand.MAIN_HAND, target, null);
		return result == InteractionResult.PASS;
	}
}
//?}
