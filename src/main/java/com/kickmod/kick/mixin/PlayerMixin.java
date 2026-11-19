package com.kickmod.kick.mixin;

import com.kickmod.kick.client.KickAnimations;
import com.kickmod.kick.net.KickEvents;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Receives the server's "this player kicked" entity event on the client. */
@Mixin(Player.class)
public abstract class PlayerMixin {

	@Inject(method = "handleEntityEvent", at = @At("HEAD"), cancellable = true)
	private void kick$onEntityEvent(byte id, CallbackInfo ci) {
		if (id == KickEvents.KICK_ANIMATION) {
			KickAnimations.start(((Player) (Object) this).getId());
			ci.cancel();
		}
	}
}
