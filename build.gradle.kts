import net.researchgate.release.ReleaseExtension

plugins {
    kotlin("jvm") version "2.0.21"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.2"
    id("net.researchgate.release") version "3.0.2"
    id("maven-publish")
    id("signing")
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

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    repositories {
        maven {
            name = "CentralMaven"
            url = uri("https://central.sonatype.com/publishing/deployments/")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                groupId = "eu.aagsolutions.telematics"
                name = "teltonika-codec"
                url = "https://github.com/atdi/teltonika-codec"
                packaging = "jar"
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                scm {
                    url.set("https://github.com/atdi/teltonika-codec")
                    connection.set("scm:git:git://github.com/atdi/teltonika-codec.git")
                    developerConnection.set("scm:git:ssh://github.com/atdi/teltonika-codec.git")
                }
                developers {
                    developer {
                        id.set("atdi")
                        name.set("Aurel Avramescu")
                        email.set("aurel.avramescu@gmail.com")
                        organization.set("https://www.aagsolutions.eu")
                    }
                }
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
        System.getenv("GPG_PRIVATE_KEY"),
        System.getenv("GPG_PRIVATE_KEY_PASSWORD"),
    )
    sign(publishing.publications.getByName("mavenJava"))
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
