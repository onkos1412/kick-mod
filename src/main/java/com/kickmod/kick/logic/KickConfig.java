package com.kickmod.kick.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

/**
 * Server-side kick settings. Pure Java (Gson only), so it is unit-tested without Minecraft.
 *
 * @param damage              damage in HP (2.0 = one heart); armor still reduces it
 * @param knockbackMultiplier multiplier on vanilla base knockback (0.4)
 * @param knockbackUp         vertical launch velocity
 * @param cooldownTicks       ticks between kicks (20 = 1 second)
 * @param reach               maximum kick distance in blocks
 * @param allowKickPlayers    whether players can be kicked at all (PvP is still checked)
 */
public record KickConfig(double damage, double knockbackMultiplier, double knockbackUp, int cooldownTicks, double reach,
		boolean allowKickPlayers) {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static KickConfig defaults() {
		return new KickConfig(2.0, 1.5, 0.4, 20, 3.0, true);
	}

	/** Never throws: malformed input gives defaults, bad fields fall back to their default, values are clamped. */
	public static KickConfig parse(String json) {
		KickConfig d = defaults();
		JsonObject o;
		try {
			JsonElement root = json == null ? null : JsonParser.parseString(json);
			if (root == null || !root.isJsonObject()) return d;
			o = root.getAsJsonObject();
		} catch (RuntimeException e) {
			return d;
		}
		return new KickConfig(
			clamp(num(o, "damage", d.damage()), 0, 100),
			clamp(num(o, "knockbackMultiplier", d.knockbackMultiplier()), 0, 10),
			clamp(num(o, "knockbackUp", d.knockbackUp()), 0, 2),
			(int) clamp(num(o, "cooldownTicks", d.cooldownTicks()), 0, 1200),
			clamp(num(o, "reach", d.reach()), 1, 6),
			bool(o, "allowKickPlayers", d.allowKickPlayers()));
	}

	public String toJson() {
		JsonObject o = new JsonObject();
		o.addProperty("damage", damage);
		o.addProperty("knockbackMultiplier", knockbackMultiplier);
		o.addProperty("knockbackUp", knockbackUp);
		o.addProperty("cooldownTicks", cooldownTicks);
		o.addProperty("reach", reach);
		o.addProperty("allowKickPlayers", allowKickPlayers);
		return GSON.toJson(o);
	}

	private static double num(JsonObject o, String key, double fallback) {
		JsonElement e = o.get(key);
		if (e instanceof JsonPrimitive p && p.isNumber()) {
			double v = p.getAsDouble();
			return Double.isFinite(v) ? v : fallback;
		}
		return fallback;
	}

	private static boolean bool(JsonObject o, String key, boolean fallback) {
		JsonElement e = o.get(key);
		return e instanceof JsonPrimitive p && p.isBoolean() ? p.getAsBoolean() : fallback;
	}

	private static double clamp(double v, double min, double max) {
		return Math.max(min, Math.min(max, v));
	}
}
