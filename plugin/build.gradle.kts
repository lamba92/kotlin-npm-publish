plugins {
    kotlin("jvm") version "1.9.21"
    `kotlin-dsl`
}

group = "com.github.lamba92"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    api("com.github.node-gradle:gradle-node-plugin:7.0.1")
    api(kotlin("gradle-plugin"))
}

