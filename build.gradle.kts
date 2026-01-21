plugins {
    id("java")
    id("net.fabricmc.fabric-loom-remap") version "1.14-SNAPSHOT"
}

group = "com.laineypowell"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }
    maven("https://maven.ladysnake.org/releases")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    minecraft("com.mojang:minecraft:1.20.1")
    mappings(loom.officialMojangMappings())

    modImplementation("net.fabricmc:fabric-loader:0.18.4")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.92.7+1.20.1")
    modRuntimeOnly("curse.maven:classic-pipes-1351745:7344365")
    modRuntimeOnly("curse.maven:jei-238222:7391694")

    modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-base:5.2.3")
    modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-level:5.2.3")
}

tasks.test {
    useJUnitPlatform()
}