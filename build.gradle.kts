import org.jetbrains.intellij.tasks.RunIdeTask
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

group = "org.jetbrains.research.groups.ml_methods"
version = "0.1"

plugins {
    id("org.jetbrains.intellij") version "0.4.13" apply true
    kotlin("jvm") version "1.3.71" apply true
}

repositories {
    mavenCentral()
    jcenter()
}

intellij {
    pluginName = "code-completion-benchmark-toolkit"
    version = "2020.1"
    downloadSources = true
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin", "kotlin-reflect", "1.3.71")
}

tasks {
    withType<KotlinJvmCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            languageVersion = "1.3"
            apiVersion = "1.3"
        }
    }

    withType<RunIdeTask> {
        enabled = true
    }
}
