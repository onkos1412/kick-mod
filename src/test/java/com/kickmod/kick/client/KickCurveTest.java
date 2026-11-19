package com.kickmod.kick.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KickCurveTest {

	@Test
	void restsAtStartAndEnd() {
		assertEquals(0f, KickCurve.legPitch(0f), 1e-6);
		assertEquals(0f, KickCurve.legPitch(1f), 1e-6);
	}

	@Test
	void peaksForwardAtThirtyFivePercent() {
		assertEquals(-1.4f, KickCurve.legPitch(0.35f), 1e-6);
	}

	@Test
	void swingsForwardThenBack() {
		float prev = 0f;
		for (int i = 1; i <= 35; i++) {
			float p = KickCurve.legPitch(i / 100f);
			assertTrue(p < prev, "forward swing must keep going forward at " + i);
			prev = p;
		}
		for (int i = 36; i <= 100; i++) {
			float p = KickCurve.legPitch(i / 100f);
			assertTrue(p > prev, "return swing must keep coming back at " + i);
			prev = p;
		}
	}

	@Test
	void outOfRangeIsRest() {
		assertEquals(0f, KickCurve.legPitch(-0.5f), 1e-6);
		assertEquals(0f, KickCurve.legPitch(1.5f), 1e-6);
	}
}
