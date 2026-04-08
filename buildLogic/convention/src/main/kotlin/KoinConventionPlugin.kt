import extension.getBundle
import extension.getPluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(getPluginId(alias = "koin-compiler"))
            }

            with(extensions) {
                configure<KotlinMultiplatformExtension> {
                    sourceSets {
                        commonMain.dependencies {
                            implementation(getBundle("koin"))
                        }
                    }
                }
            }
        }
    }
}