package com.kickmod.kick.client;

import com.kickmod.kick.Kick;

/** Client-side entry point. */
public final class KickClient {

	private static ClientPlatform platform;

	private KickClient() {}

	public static void init(ClientPlatform clientPlatform) {
		platform = clientPlatform;
		Kick.initClient();
	}

	public static ClientPlatform platform() {
		return platform;
	}
}
