import extension.getBundle
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("base.kmp.library")
                apply("base.kmp.compose")
                apply("base.kmp.koin")
            }

            with(extensions) {
                configure<KotlinMultiplatformExtension> {
                    sourceSets {
                        commonMain.dependencies {
                            implementation(project(":resources"))
                            implementation(project(":navigation"))
                            implementation(project(":core:data"))
                            implementation(project(":core:common"))
                            implementation(project(":core:presentation"))
                            implementation(getBundle("lifecycle"))
                            implementation(getBundle("koin-compose"))
                        }
                    }
                }
            }
        }
    }
}