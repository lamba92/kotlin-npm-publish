@file:Suppress("UnstableApiUsage")

includeBuild("plugin")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "kotlin-npm-publish-test"
