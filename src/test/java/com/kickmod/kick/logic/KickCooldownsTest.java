package com.kickmod.kick.logic;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KickCooldownsTest {

	private final UUID a = UUID.randomUUID();
	private final UUID b = UUID.randomUUID();

	@Test
	void firstUseAllowed() {
		assertTrue(new KickCooldowns().tryUse(a, 5, 20));
	}

	@Test
	void blocksUntilCooldownElapses() {
		KickCooldowns c = new KickCooldowns();
		assertTrue(c.tryUse(a, 100, 20));
		assertFalse(c.tryUse(a, 119, 20));
		assertTrue(c.tryUse(a, 120, 20));
	}

	@Test
	void deniedAttemptDoesNotResetCooldown() {
		KickCooldowns c = new KickCooldowns();
		assertTrue(c.tryUse(a, 100, 20));
		assertFalse(c.tryUse(a, 110, 20));
		assertTrue(c.tryUse(a, 120, 20));
	}

	@Test
	void playersIndependent() {
		KickCooldowns c = new KickCooldowns();
		assertTrue(c.tryUse(a, 100, 20));
		assertTrue(c.tryUse(b, 101, 20));
	}

	@Test
	void zeroCooldownAlwaysAllowed() {
		KickCooldowns c = new KickCooldowns();
		assertTrue(c.tryUse(a, 100, 0));
		assertTrue(c.tryUse(a, 100, 0));
	}

	@Test
	void forgetClearsCooldown() {
		KickCooldowns c = new KickCooldowns();
		assertTrue(c.tryUse(a, 100, 20));
		c.forget(a);
		assertTrue(c.tryUse(a, 101, 20));
	}

	@Test
	void clockGoingBackwardsDoesNotLockOut() {
		// the game time used for cooldowns can jump back (e.g. a different level's clock)
		KickCooldowns c = new KickCooldowns();
		assertTrue(c.tryUse(a, 5000, 20));
		assertTrue(c.tryUse(a, 10, 20));
	}
}
