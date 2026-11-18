package com.kickmod.kick;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SmokeTest {
	@Test
	void modIdIsKick() {
		assertEquals("kick", Kick.MOD_ID);
	}
}
