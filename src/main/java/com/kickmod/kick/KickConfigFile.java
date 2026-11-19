package com.kickmod.kick;

import com.google.gson.JsonParser;
import com.kickmod.kick.logic.KickConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/** Loads {@code config/kick.json} once at startup; writes defaults when the file is missing. */
public final class KickConfigFile {

	public static final String FILE_NAME = "kick.json";

	private static volatile KickConfig current = KickConfig.defaults();

	private KickConfigFile() {}

	public static KickConfig get() {
		return current;
	}

	public static void load(Path configDir) {
		Path file = configDir.resolve(FILE_NAME);
		try {
			if (Files.exists(file)) {
				String text = Files.readString(file, StandardCharsets.UTF_8);
				if (!isJsonObject(text)) {
					Kick.LOGGER.warn("Kick! config {} is not valid JSON, using defaults", file);
				}
				current = KickConfig.parse(text);
			} else {
				current = KickConfig.defaults();
				Files.createDirectories(configDir);
				Files.writeString(file, current.toJson(), StandardCharsets.UTF_8);
			}
		} catch (IOException e) {
			Kick.LOGGER.warn("Kick! could not read or write {}, using defaults", file, e);
			current = KickConfig.defaults();
		}
		Kick.LOGGER.info("Kick! config: {}", current);
	}

	private static boolean isJsonObject(String text) {
		try {
			return JsonParser.parseString(text).isJsonObject();
		} catch (RuntimeException e) {
			return false;
		}
	}
}
