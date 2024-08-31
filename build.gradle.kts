import org.gradle.api.JavaVersion.VERSION_11
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("jvm") version "2.0.20"
    id("com.google.cloud.tools.jib") version "2.3.0"
}

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

jib {
    container.mainClass = "com.khuldoon.KhuldoonKt"
    to.image = "docker_repo_user/docker_image_user"
}

repositories {
    mavenCentral()
}

tasks {
    withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            allWarningsAsErrors = false
            jvmTarget.set(JVM_11)
            freeCompilerArgs.add("-Xjvm-default=all")
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    java {
        sourceCompatibility = VERSION_11
        targetCompatibility = VERSION_11
    }
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:5.29.0.0"))
    implementation(platform("org.http4k:http4k-connect-bom:5.22.1.0"))
    implementation("org.http4k:http4k-client-okhttp")
    implementation("org.http4k:http4k-config")
    implementation("org.http4k:http4k-contract")
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-failsafe")
    implementation("org.http4k:http4k-format-jackson")
    implementation("org.http4k:http4k-format-kotlinx-serialization")
    implementation("org.http4k:http4k-metrics-micrometer")
    implementation("org.http4k:http4k-opentelemetry")
    implementation("org.http4k:http4k-server-netty")
    testImplementation("org.http4k:http4k-testing-approval")
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.http4k:http4k-testing-kotest")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.0")
}

