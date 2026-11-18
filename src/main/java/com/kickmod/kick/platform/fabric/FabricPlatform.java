package com.kickmod.kick.platform.fabric;

//? fabric {

import com.kickmod.kick.platform.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatform implements Platform {

	@Override
	public ModLoader loader() {
		return ModLoader.FABRIC;
	}

	@Override
	public boolean isClient() {
		return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
	}
}
//?}
