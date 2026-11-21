package com.kickmod.kick.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KickShakeTest {

	@Test
	void stillBeforeTheFootLands() {
		float[] o = KickShake.offsetDegrees(1.5f, 1f);
		assertEquals(0f, o[0], 1e-6);
		assertEquals(0f, o[1], 1e-6);
	}

	@Test
	void impactDipsTheViewDownAndTiltsIt() {
		float[] o = KickShake.offsetDegrees(KickShake.IMPACT_TICKS, 1f);
		assertEquals(KickShake.MAX_PITCH_DEGREES, o[0], 1e-6);
		assertEquals(KickShake.MAX_ROLL_DEGREES, o[1], 1e-6);
	}

	@Test
	void staysSubtle() {
		for (float t = 0f; t < 20f; t += 0.05f) {
			float[] o = KickShake.offsetDegrees(t, 1f);
			assertTrue(Math.abs(o[0]) <= 1.5f + 1e-6, "pitch too strong at " + t);
			assertTrue(Math.abs(o[1]) <= 1.0f + 1e-6, "roll too strong at " + t);
		}
	}

	@Test
	void settlesWithinHalfASecond() {
		float[] late = KickShake.offsetDegrees(9.5f, 1f);
		assertTrue(Math.abs(late[0]) < 0.1f && Math.abs(late[1]) < 0.1f, "still shaking at 9.5 ticks");
		float[] done = KickShake.offsetDegrees(KickShake.DURATION_TICKS, 1f);
		assertEquals(0f, done[0], 1e-6);
		assertEquals(0f, done[1], 1e-6);
	}

	@Test
	void scalesWithTheDamageTiltSetting() {
		float[] half = KickShake.offsetDegrees(KickShake.IMPACT_TICKS, 0.5f);
		assertEquals(KickShake.MAX_PITCH_DEGREES / 2, half[0], 1e-6);
		float[] off = KickShake.offsetDegrees(KickShake.IMPACT_TICKS, 0f);
		assertEquals(0f, off[0], 1e-6);
		assertEquals(0f, off[1], 1e-6);
	}

	@Test
	void noKickNoShake() {
		float[] o = KickShake.offsetDegrees(-1f, 1f);
		assertEquals(0f, o[0], 1e-6);
		assertEquals(0f, o[1], 1e-6);
	}
}
