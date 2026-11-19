package com.kickmod.kick;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Ids of everything the mod adds to the game. Nothing here needs loader-specific registration:
 * the damage type is data-driven (data/kick/damage_type/kick.json) and the sound event is played
 * as a direct holder, so clients without the mod are not refused by the server.
 */
public final class KickRegistry {

	public static final ResourceKey<DamageType> DAMAGE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, id("kick"));

	/** Defined in assets/kick/sounds.json. */
	public static final SoundEvent KICK_SOUND = SoundEvent.createVariableRangeEvent(id("entity.kick"));

	private KickRegistry() {}

	public static Identifier id(String path) {
		//? if >=1.21 {
		return Identifier.fromNamespaceAndPath(Kick.MOD_ID, path);
		//?} else {
		/*return new Identifier(Kick.MOD_ID, path);
		*///?}
	}

	/** A kick damage source caused by {@code kicker}; armor applies (the type is not in bypasses_armor). */
	public static DamageSource source(Level level, Player kicker) {
		Holder<DamageType> type = level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DAMAGE_TYPE);
		return new DamageSource(type, kicker);
	}
}
