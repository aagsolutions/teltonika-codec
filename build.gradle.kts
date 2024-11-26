import net.researchgate.release.ReleaseExtension

plugins {
    kotlin("jvm") version "2.0.21"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.2"
    id("net.researchgate.release") version "3.0.2"
}

group = "eu.aagsolutions.telematics"

val slf4jVersion = "2.0.16"

repositories {
    mavenCentral()
}

configure<ReleaseExtension> {
    ignoredSnapshotDependencies.set(listOf("net.researchgate:gradle-release"))
    with(git) {
        requireBranch.set("main")
    }
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
