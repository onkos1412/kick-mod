package com.kickmod.kick.platform.forge;

//? forge {

/*import com.kickmod.kick.platform.Platform;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class ForgePlatform implements Platform {

	@Override
	public ModLoader loader() {
		return ModLoader.FORGE;
	}

	@Override
	public boolean isClient() {
		return FMLEnvironment.dist.isClient();
	}

	@Override
	public Path configDir() {
		return FMLPaths.CONFIGDIR.get();
	}
}
*///?}
