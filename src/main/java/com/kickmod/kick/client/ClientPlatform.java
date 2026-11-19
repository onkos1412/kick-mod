package com.kickmod.kick.client;

/** Loader-specific client networking. Implemented per loader, installed by the loader's client setup. */
public interface ClientPlatform {
	/** True if the connected server has the mod (accepts kick requests). */
	boolean canSendKickRequest();

	void sendKickRequest(int targetId);
}
