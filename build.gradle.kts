plugins {
    id("java")
    id("net.fabricmc.fabric-loom-remap") version "1.14-SNAPSHOT"
}

group = "com.laineypowell"
version = "1.21.1-1.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }
    maven("https://maven.ladysnake.org/releases")
    maven("https://maven.terraformersmc.com/")
    maven("https://mod-buildcraft.com/maven")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    minecraft("com.mojang:minecraft:1.21.1")
    mappings(loom.officialMojangMappings())

    modImplementation("net.fabricmc:fabric-loader:0.18.4")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.116.8+1.21.1")

    modRuntimeOnly("alexiil.mc.mod:simplepipes-all:0.13.2")
    modRuntimeOnly("alexiil.mc.mod:simplepipes-base:0.13.2")

    modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-base:6.1.3")
    modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-level:6.1.3")
    // modImplementation("dev.emi:trinkets:3.7.1")
}

tasks.test {
    useJUnitPlatform()
}

fun JavaPluginExtension.javaVersion(version: JavaVersion, languageVersion: JavaLanguageVersion) {
    sourceCompatibility = version
    targetCompatibility = version
    toolchain.languageVersion.set(languageVersion)
}

java {
    javaVersion(JavaVersion.VERSION_21, JavaLanguageVersion.of(21))
}

tasks.processResources {
    inputs.property("version", project.version)

    filesMatching("fabric.mod.json") {
        expand(mapOf("version" to inputs.properties["version"]))
    }
}