package com.kickmod.kick;

import com.kickmod.kick.logic.KickAttempt;
import com.kickmod.kick.logic.KickConfig;
import com.kickmod.kick.logic.KickCooldowns;
import com.kickmod.kick.logic.KickDenial;
import com.kickmod.kick.logic.KickRules;
import com.kickmod.kick.net.KickEvents;
import com.kickmod.kick.net.KickRequest;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** Server-side kick handling. Called on the server thread; the server alone decides what a kick does. */
public final class KickHandler {

	private static final KickCooldowns COOLDOWNS = new KickCooldowns();

	private KickHandler() {}

	public static void onRequest(ServerPlayer kicker, int targetId) {
		KickConfig cfg = KickConfigFile.get();
		ServerLevel level = (ServerLevel) kicker.level();
		if (!kicker.isAlive() || kicker.isSpectator()) return;
		if (!COOLDOWNS.tryUse(kicker.getUUID(), level.getGameTime(), cfg.cooldownTicks())) return;

		// Everyone nearby (and the kicker) sees the leg swing, hit or miss.
		level.broadcastEntityEvent(kicker, KickEvents.KICK_ANIMATION);

		Entity target = targetId == KickRequest.AIR ? null : level.getEntity(targetId);
		KickDenial denial = KickRules.check(attempt(kicker, target), cfg);
		if (denial != KickDenial.NONE || !(target instanceof LivingEntity living)) {
			if (denial != KickDenial.NO_TARGET) Kick.LOGGER.debug("Kick! by {} denied: {}", kicker.getName().getString(), denial);
			whoosh(level, kicker);
			return;
		}
		land(level, kicker, living, cfg);
	}

	private static KickAttempt attempt(ServerPlayer kicker, Entity target) {
		if (target == null) {
			return new KickAttempt(kicker.isAlive(), kicker.isSpectator(), false, false, false, false, false, false, 0, false);
		}
		Vec3 eye = kicker.getEyePosition();
		AABB box = target.getBoundingBox();
		double distance = KickRules.distanceToBox(eye.x, eye.y, eye.z, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
		boolean living = target instanceof LivingEntity;
		// canHarmPlayer covers both the server PvP setting and team friendly-fire rules.
		boolean pvpAllowed = !(target instanceof Player other) || kicker.canHarmPlayer(other);
		return new KickAttempt(kicker.isAlive(), kicker.isSpectator(), true, target == kicker, target.isAlive(), living,
			target instanceof Player, pvpAllowed, distance, living && kicker.hasLineOfSight(target));
	}

	private static void land(ServerLevel level, ServerPlayer kicker, LivingEntity target, KickConfig cfg) {
		//? if >=1.21.2 {
		boolean hurt = target.hurtServer(level, KickRegistry.source(level, kicker), (float) cfg.damage());
		//?} else {
		/*boolean hurt = target.hurt(KickRegistry.source(level, kicker), (float) cfg.damage());
		*///?}
		if (!hurt) {
			// e.g. still invulnerable from the previous hit, or creative mode
			whoosh(level, kicker);
			return;
		}

		// Replace vanilla's small hit knockback with the big, funny one.
		double keep = Math.max(0, Math.min(1, 1 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
		double[] kb = KickRules.knockback(kicker.getYRot(), cfg);
		target.setDeltaMovement(kb[0] * keep, kb[1] * keep, kb[2] * keep);
		//? if >=26.3 {
		target.syncVelocity = true;
		//?} else {
		/*target.hurtMarked = true;
		*///?}

		float pitch = 0.9f + kicker.getRandom().nextFloat() * 0.2f;
		level.playSound(null, target.getX(), target.getY(), target.getZ(), KickRegistry.KICK_SOUND, SoundSource.PLAYERS, 1.0f, pitch);
		level.sendParticles(ParticleTypes.POOF, target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(),
			8, 0.25, 0.25, 0.25, 0.02);
	}

	private static void whoosh(ServerLevel level, ServerPlayer kicker) {
		level.playSound(null, kicker.getX(), kicker.getY(), kicker.getZ(), SoundEvents.PLAYER_ATTACK_NODAMAGE, SoundSource.PLAYERS, 1.0f, 1.2f);
	}
}
