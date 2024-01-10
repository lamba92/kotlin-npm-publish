import com.github.gradle.node.npm.task.NpmTask
import com.github.lamba92.gradle.kotlin.npm.publish.NpmPublishingExtension
import com.github.lamba92.gradle.kotlin.npm.publish.kotlinJsIrCompileRTaskNameRegex
import com.github.lamba92.gradle.kotlin.npm.publish.withId
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrLink
import org.jetbrains.kotlin.gradle.targets.js.npm.PublicPackageJsonTask
import java.util.*

plugins.withId("org.jetbrains.kotlin.multiplatform", "com.github.node-gradle.node") {

    val extension = project.extensions
        .create("npmPublishing", NpmPublishingExtension::class.java, project)

    tasks {
        withType<KotlinJsIrLink> {
            val sourceSetName = kotlinJsIrCompileRTaskNameRegex.find(name)
                ?.groups
                ?.get(1)
                ?.value

            if (sourceSetName == null) {
                logger.warn("Could not find source set name for task $name")
                return@withType
            }
            val sourceSetNameDecapitalized = sourceSetName.replaceFirstChar { it.lowercase(Locale.getDefault()) }
            val packageJsonTask = project.tasks
                .named<PublicPackageJsonTask>("${sourceSetNameDecapitalized}PublicPackageJson")

            val publicationDirectory = layout.buildDirectory
                .file("js/publications/${sourceSetNameDecapitalized}")
            val assemble = register<Sync>("assemble${sourceSetName}ForPublication") {
                group = "publishing"
                dependsOn(packageJsonTask, this@withType)
                from(packageJsonTask)
                from(this@withType) {
                    into("kotlin")
                }
                into(publicationDirectory)
            }
            extension.registries.all {
                register<NpmTask>("publish${sourceSetName}PublicationTo${name.capitalize()}") {
                    group = "publishing"
                    dependsOn(assemble)
                    args = listOf("publish", "--access", "public", "--registry", url.get())
                    workingDir = publicationDirectory
                    execOverrides {
                        environment["NPM_TOKEN"] = credentials.flatMap { it.authToken }.get()
                    }
                }
            }
        }
    }
}