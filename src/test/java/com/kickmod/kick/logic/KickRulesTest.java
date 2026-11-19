package com.kickmod.kick.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KickRulesTest {

	private static final KickConfig CFG = KickConfig.defaults();

	/** A valid kick: living mob target, 2 blocks away, visible, PvP on. */
	private static KickAttempt valid() {
		return new KickAttempt(true, false, true, false, true, true, false, true, 2.0, true);
	}

	private static KickAttempt withDistance(double d) {
		return new KickAttempt(true, false, true, false, true, true, false, true, d, true);
	}

	private static KickAttempt playerTarget(boolean pvp) {
		return new KickAttempt(true, false, true, false, true, true, true, pvp, 2.0, true);
	}

	@Test
	void acceptsValidMobKick() {
		assertEquals(KickDenial.NONE, KickRules.check(valid(), CFG));
	}

	@Test
	void rejectsTooFar() {
		assertEquals(KickDenial.TOO_FAR, KickRules.check(withDistance(4.5), CFG));
		assertEquals(KickDenial.NONE, KickRules.check(withDistance(3.9), CFG));
	}

	@Test
	void rejectsNoLineOfSight() {
		KickAttempt a = new KickAttempt(true, false, true, false, true, true, false, true, 2.0, false);
		assertEquals(KickDenial.NO_LINE_OF_SIGHT, KickRules.check(a, CFG));
	}

	@Test
	void rejectsMissingTarget() {
		KickAttempt a = new KickAttempt(true, false, false, false, false, false, false, true, 0.0, false);
		assertEquals(KickDenial.NO_TARGET, KickRules.check(a, CFG));
	}

	@Test
	void rejectsSelf() {
		KickAttempt a = new KickAttempt(true, false, true, true, true, true, true, true, 0.0, true);
		assertEquals(KickDenial.SELF, KickRules.check(a, CFG));
	}

	@Test
	void rejectsNonLivingTarget() {
		KickAttempt a = new KickAttempt(true, false, true, false, true, false, false, true, 2.0, true);
		assertEquals(KickDenial.NOT_LIVING, KickRules.check(a, CFG));
	}

	@Test
	void rejectsDeadTarget() {
		KickAttempt a = new KickAttempt(true, false, true, false, false, true, false, true, 2.0, true);
		assertEquals(KickDenial.TARGET_DEAD, KickRules.check(a, CFG));
	}

	@Test
	void rejectsSpectatorKicker() {
		KickAttempt a = new KickAttempt(true, true, true, false, true, true, false, true, 2.0, true);
		assertEquals(KickDenial.KICKER_INVALID, KickRules.check(a, CFG));
	}

	@Test
	void rejectsDeadKicker() {
		KickAttempt a = new KickAttempt(false, false, true, false, true, true, false, true, 2.0, true);
		assertEquals(KickDenial.KICKER_INVALID, KickRules.check(a, CFG));
	}

	@Test
	void rejectsPlayerWhenPvpOff() {
		assertEquals(KickDenial.PVP_DISABLED, KickRules.check(playerTarget(false), CFG));
		assertEquals(KickDenial.NONE, KickRules.check(playerTarget(true), CFG));
	}

	@Test
	void rejectsPlayerWhenDisabledInConfig() {
		KickConfig noPlayers = new KickConfig(2.0, 1.5, 0.4, 20, 3.0, false);
		assertEquals(KickDenial.PVP_DISABLED, KickRules.check(playerTarget(true), noPlayers));
	}

	@Test
	void mobKickIgnoresPvp() {
		KickAttempt a = new KickAttempt(true, false, true, false, true, true, false, false, 2.0, true);
		assertEquals(KickDenial.NONE, KickRules.check(a, CFG));
	}

	@Test
	void protectionModVetoBlocksAnOtherwiseValidKick() {
		// e.g. a land-claim mod cancelling the attack event for a villager in someone's claim
		assertEquals(KickDenial.PROTECTED, KickRules.check(valid(), CFG, false));
		assertEquals(KickDenial.NONE, KickRules.check(valid(), CFG, true));
	}

	@Test
	void ruleDenialsAreReportedBeforeProtection() {
		// the attack event is only asked about kicks that would otherwise land
		assertEquals(KickDenial.TOO_FAR, KickRules.check(withDistance(9), CFG, false));
		assertEquals(KickDenial.PVP_DISABLED, KickRules.check(playerTarget(false), CFG, false));
	}

	@Test
	void distanceToBoxIsZeroInside() {
		assertEquals(0.0, KickRules.distanceToBox(0, 1, 0, -0.5, 0, -0.5, 0.5, 2, 0.5), 1e-9);
	}

	@Test
	void distanceToBoxUsesNearestPoint() {
		// eye 2 blocks north of a 1x2x1 mob centred on the origin: nearest face is 1.5 away
		assertEquals(1.5, KickRules.distanceToBox(0, 1.6, -2, -0.5, 0, -0.5, 0.5, 2, 0.5), 1e-9);
		// diagonal: 3-4-5 triangle from the box corner
		assertEquals(5.0, KickRules.distanceToBox(3.5, 1, 4.5, -0.5, 0, -0.5, 0.5, 2, 0.5), 1e-9);
	}

	@Test
	void knockbackFacingSouth() {
		double[] v = KickRules.knockback(0f, CFG);
		assertEquals(0.0, v[0], 1e-9);
		assertEquals(0.4, v[1], 1e-9);
		assertEquals(0.6, v[2], 1e-9);
	}

	@Test
	void knockbackFacingEast() {
		// yaw -90 means facing +X (east) in Minecraft
		double[] v = KickRules.knockback(-90f, CFG);
		assertEquals(0.6, v[0], 1e-9);
		assertEquals(0.0, v[2], 1e-9);
	}
}
