package com.kickmod.kick.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KickAnimationStateTest {

	private static final float D = KickAnimationState.DURATION_TICKS;

	@Test
	void idleEntityHasNoProgress() {
		assertEquals(0f, new KickAnimationState().progress(7, 100f), 1e-6);
	}

	@Test
	void progressRunsOverTheDuration() {
		KickAnimationState s = new KickAnimationState();
		s.start(7, 100f);
		assertEquals(0f, s.progress(7, 100f), 1e-6);
		assertEquals(0.5f, s.progress(7, 100f + D / 2), 1e-6);
		assertEquals(0f, s.progress(7, 100f + D), 1e-6); // finished
	}

	@Test
	void serverEchoRightAfterALocalStartDoesNotRestart() {
		KickAnimationState s = new KickAnimationState();
		s.start(7, 100f);       // key pressed: animate at once
		s.start(7, 101.5f);     // server event arrives a tick later
		assertEquals(0.5f, s.progress(7, 100f + D / 2), 1e-6);
	}

	@Test
	void laterKickRestarts() {
		KickAnimationState s = new KickAnimationState();
		s.start(7, 100f);
		s.start(7, 120f);
		assertEquals(0.5f, s.progress(7, 120f + D / 2), 1e-6);
	}

	@Test
	void entitiesAnimateIndependently() {
		KickAnimationState s = new KickAnimationState();
		s.start(1, 100f);
		s.start(2, 102f);
		assertEquals(0.5f, s.progress(1, 100f + D / 2), 1e-6);
		assertEquals(0.5f, s.progress(2, 102f + D / 2), 1e-6);
	}

	@Test
	void finishedAnimationsAreForgotten() {
		// entities that left view are never queried again; old entries must not pile up
		KickAnimationState s = new KickAnimationState();
		for (int i = 0; i < 100; i++) s.start(i, 0f);
		s.start(500, 1000f);
		assertEquals(1, s.size());
	}

	@Test
	void clockGoingBackwardsStopsTheAnimation() {
		// e.g. a new world/dimension with a different game time
		KickAnimationState s = new KickAnimationState();
		s.start(7, 5000f);
		assertEquals(0f, s.progress(7, 10f), 1e-6);
	}
}
