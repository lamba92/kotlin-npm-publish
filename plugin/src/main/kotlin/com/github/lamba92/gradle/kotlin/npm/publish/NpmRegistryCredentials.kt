package com.github.lamba92.gradle.kotlin.npm.publish

import org.gradle.api.Named
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.NamedDomainObjectContainerScope
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.container
import org.gradle.kotlin.dsl.property

open class NpmRegistry(private val objects: ObjectFactory, private val name: String) : Named {

    override fun getName() = name

    val url = objects.property<String>()
    internal val credentials = objects.property<Credentials>()

    class Credentials(objects: ObjectFactory) {
        val authToken = objects.property<String>()
    }

    fun credentials(action: Credentials.() -> Unit) {
        val credentials = Credentials(objects)
        credentials.action()
        this.credentials = credentials
    }
}

open class NpmPublishingExtension(project: Project) {
    val registries = NpmRegistryContainer(project.container { NpmRegistry(project.objects, it) })
    fun registries(action: NpmRegistryContainer.() -> Unit) = registries.action()
}

class NpmRegistryContainer(delegate: NamedDomainObjectContainer<NpmRegistry>) :
    NamedDomainObjectContainer<NpmRegistry> by delegate {

    fun npmjs(action: NpmRegistry.Credentials.() -> Unit) {
        register("npmjs") {
            url = "https://registry.npmjs.org/"
            credentials(action)
        }
    }
}