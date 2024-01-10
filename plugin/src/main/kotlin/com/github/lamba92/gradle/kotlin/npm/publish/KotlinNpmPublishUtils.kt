package com.github.lamba92.gradle.kotlin.npm.publish

import org.gradle.api.plugins.PluginContainer

fun PluginContainer.withId(vararg ids: String, apply: () -> Unit) {
    val action = ids.fold(apply) { acc, id ->
        { withId(id) { acc() } }
    }
    action()
}


val kotlinJsIrCompileRTaskNameRegex =
    Regex("compileProductionLibraryKotlin([A-Z]\\w+)")
