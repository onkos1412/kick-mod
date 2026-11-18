package com.kickmod.kick.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KickConfigTest {

	@Test
	void defaultsMatchSpec() {
		KickConfig c = KickConfig.defaults();
		assertEquals(2.0, c.damage());
		assertEquals(1.5, c.knockbackMultiplier());
		assertEquals(0.4, c.knockbackUp());
		assertEquals(20, c.cooldownTicks());
		assertEquals(3.0, c.reach());
		assertTrue(c.allowKickPlayers());
	}

	@Test
	void missingFieldsUseDefaults() {
		KickConfig c = KickConfig.parse("{\"damage\":4}");
		assertEquals(4.0, c.damage());
		KickConfig d = KickConfig.defaults();
		assertEquals(d.knockbackMultiplier(), c.knockbackMultiplier());
		assertEquals(d.knockbackUp(), c.knockbackUp());
		assertEquals(d.cooldownTicks(), c.cooldownTicks());
		assertEquals(d.reach(), c.reach());
		assertEquals(d.allowKickPlayers(), c.allowKickPlayers());
	}

	@Test
	void malformedJsonGivesDefaults() {
		assertEquals(KickConfig.defaults(), KickConfig.parse("{oops"));
		assertEquals(KickConfig.defaults(), KickConfig.parse(""));
		assertEquals(KickConfig.defaults(), KickConfig.parse("[1,2]"));
		assertEquals(KickConfig.defaults(), KickConfig.parse(null));
	}

	@Test
	void wrongTypesUseDefaults() {
		KickConfig c = KickConfig.parse("{\"damage\":\"lots\",\"allowKickPlayers\":\"maybe\"}");
		assertEquals(2.0, c.damage());
		assertTrue(c.allowKickPlayers());
	}

	@Test
	void negativeValuesClamped() {
		KickConfig c = KickConfig.parse("{\"reach\":-5,\"cooldownTicks\":-1,\"damage\":-3,\"knockbackMultiplier\":-1,\"knockbackUp\":-1}");
		assertEquals(1.0, c.reach());
		assertEquals(0, c.cooldownTicks());
		assertEquals(0.0, c.damage());
		assertEquals(0.0, c.knockbackMultiplier());
		assertEquals(0.0, c.knockbackUp());
	}

	@Test
	void hugeValuesClamped() {
		KickConfig c = KickConfig.parse("{\"reach\":100,\"cooldownTicks\":99999,\"damage\":1e9,\"knockbackMultiplier\":50,\"knockbackUp\":9}");
		assertEquals(6.0, c.reach());
		assertEquals(1200, c.cooldownTicks());
		assertEquals(100.0, c.damage());
		assertEquals(10.0, c.knockbackMultiplier());
		assertEquals(2.0, c.knockbackUp());
	}

	@Test
	void roundTrip() {
		assertEquals(KickConfig.defaults(), KickConfig.parse(KickConfig.defaults().toJson()));
		KickConfig custom = new KickConfig(4.0, 2.0, 0.5, 40, 4.0, false);
		assertEquals(custom, KickConfig.parse(custom.toJson()));
	}
}
