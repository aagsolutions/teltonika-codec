plugins {
    kotlin("jvm") version "2.0.21"
}

group = "eu.aagsolutions.telematics"
version = "1.0-SNAPSHOT"

val slf4jVersion = "2.0.16"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}