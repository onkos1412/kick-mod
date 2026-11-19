package com.kickmod.kick.logic;

/** Why a kick was refused; {@link #NONE} means it lands. */
public enum KickDenial {
	NONE, KICKER_INVALID, NO_TARGET, SELF, TARGET_DEAD, NOT_LIVING, TOO_FAR, NO_LINE_OF_SIGHT, PVP_DISABLED,
	/** The loader's attack event was cancelled (land claims, PvP toggles and other protection mods). */
	PROTECTED
}
