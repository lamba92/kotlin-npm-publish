import com.github.gradle.node.npm.task.NpmTask
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrLink
import org.jetbrains.kotlin.gradle.targets.js.npm.PublicPackageJsonTask

plugins {
    kotlin("multiplatform") version "1.9.21"
    id("com.github.node-gradle.node") version "7.0.1"
    id("kotlin-npm-publish")
}

group = "com.github.lamba92"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

node {
    version = "20.11.0"
    download = true
}

npmPublishing {
    registries {
        npmjs {
            authToken = "123423423"
        }
    }
}

kotlin {
    js {
        nodejs()
        browser()
        binaries.library()
    }
    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.js.ExperimentalJsExport")
            }
        }
    }
}

tasks {
    withType<NpmTask> {
        doFirst {
        }
    }
}