plugins {
	alias(libs.plugins.stonecutter)
	alias(libs.plugins.dotenv)
	alias(libs.plugins.mod.publish.plugin)
	alias(libs.plugins.fabric.loom).apply(false)
	alias(libs.plugins.fabric.loom.remap).apply(false)
	alias(libs.plugins.neoforged.moddev).apply(false)
	alias(libs.plugins.minecraftforge.gradle).apply(false)
	alias(libs.plugins.minecraftforge.jarjar).apply(false)
	alias(libs.plugins.jsonlang.postprocess).apply(false)
	alias(libs.plugins.kotlin.jvm).apply(false)
	alias(libs.plugins.devtools.ksp).apply(false)
	alias(libs.plugins.fletching.table).apply(false)
	alias(libs.plugins.legacyforge.moddev).apply(false)
}

stonecutter active file(".sc_active_version")

val cleanOutput by tasks.registering(Delete::class) {
	val version = property("mod.version_prefix").toString() + property("mod.version") + property("mod.version_suffix")
	delete(fileTree(rootProject.layout.projectDirectory.dir("output/$version")))
}

allprojects {
	// Plugins add KikuGie's Maven ahead of the others; when it is down, Gradle gives up on every dependency.
	repositories.withType<MavenArtifactRepository>().matching { it.name.startsWith("KikuGie") }.configureEach {
		content { includeGroupByRegex("dev[.]kikugie.*") }
	}
	tasks.withType<Delete>().configureEach {
		if (name == "clean") dependsOn(cleanOutput)
	}
}

tasks.register("runActiveClient") {
	group = "stonecutter"
	description = "Run client of the active Stonecutter version (always up-to-date)"
	dependsOn(stonecutter.current!!.project + ":processResources")
	dependsOn(stonecutter.current!!.project + ":classes")
	finalizedBy(stonecutter.current!!.project + ":runClient")
}

stonecutter parameters {
	constants.match(node.metadata.project.substringAfterLast('-'), "fabric", "neoforge", "forge")
	swaps["mod_version"] = "\"" + property("mod.version") + "\";"
	swaps["mod_id"] = "\"" + property("mod.id") + "\";"
	swaps["mod_name"] = "\"" + property("mod.name") + "\";"
	swaps["minecraft"] = "\"" + node.metadata.version + "\";"
}
