pluginManagement {
	repositories {
		mavenLocal()
		mavenCentral()
		gradlePluginPortal()
		maven("https://maven.fabricmc.net/") { name = "Fabric" }
		maven("https://maven.neoforged.net/releases/") { name = "NeoForged" }
		maven("https://maven.minecraftforge.net/") { name = "MinecraftForge" }
		maven("https://repo.spongepowered.org/repository/maven-public/") { name = "Sponge" }
		maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
		maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
		exclusiveContent {
			forRepository { maven("https://api.modrinth.com/maven") { name = "Modrinth" } }
			filter { includeGroup("maven.modrinth") }
		}
	}
	includeBuild("build-logic")
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
	id("dev.kikugie.stonecutter") version "0.9"
}

rootProject.name = "kick"

// Build matrix. Each node = one jar: "<minecraft>-<loader>".
// Set KICK_NODES (env var or .env file), e.g. KICK_NODES=1.21-fabric,26.3-neoforge, to work on a subset.
val allNodes = listOf(
	"26.3" to listOf("fabric", "neoforge", "forge"),
	"26.2" to listOf("fabric", "neoforge", "forge"),
	"26.1" to listOf("fabric", "neoforge", "forge"),
	"1.21.11" to listOf("fabric", "neoforge", "forge"),
	"1.21.4" to listOf("fabric", "neoforge", "forge"),
	"1.21" to listOf("fabric", "neoforge", "forge"),
	"1.20" to listOf("fabric", "forge"),
)

val settingsRootDir = rootDir

fun env(variable: String): String? {
	System.getenv(variable)?.let { return it }
	val envFile = java.io.File(settingsRootDir, ".env")
	if (envFile.exists()) {
		val props = java.util.Properties()
		envFile.inputStream().use { props.load(it) }
		props.getProperty(variable)?.let { return it }
	}
	return null
}

val onlyNodes = env("KICK_NODES")?.split(',')?.map { it.trim() }?.filter { it.isNotEmpty() }?.toSet()

stonecutter {
	create(rootProject) {
		for ((version, loaders) in allNodes) {
			for (loader in loaders) {
				val node = "$version-$loader"
				if (onlyNodes != null && node !in onlyNodes && node != "26.3-fabric") continue // vcs node is always required
				val buildscriptName = if (version.startsWith("1.") && loader == "fabric") "build.fabric-legacy.gradle.kts" else "build.$loader.gradle.kts"
				version(node, version).buildscript = buildscriptName
			}
		}
		vcsVersion = "26.3-fabric"
	}
}
