package com.kickmod.kick.platform.neoforge;

//? neoforge {

/*import com.kickmod.kick.platform.Platform;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;

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
}
*///?}
