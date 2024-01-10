package com.github.lamba92.gradle

fun main() {
    println(greet("Kotlin/JS"))
}

@JsExport
fun greet(name: String) = "Hello, $name!"
