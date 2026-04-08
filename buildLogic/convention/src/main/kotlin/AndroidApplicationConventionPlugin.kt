import com.android.build.api.dsl.ApplicationExtension
import extension.configureAndroid
import extension.getBomLibrary
import extension.getLibrary
import extension.getPluginId
import extension.implementation
import extension.setDefaultJvmTarget
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension


@Suppress("unused")
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(getPluginId(alias = "android-application"))
                apply(getPluginId(alias = "compose-compiler"))
            }

            extensions.configure<ApplicationExtension> {
                configureAndroid(this)
            }

            extensions.configure<KotlinAndroidProjectExtension> {
                compilerOptions {
                    setDefaultJvmTarget()
                }
            }

            dependencies {
                implementation(project(":composeApp"))
                implementation(getBomLibrary("firebase-bom"))
                implementation(getLibrary("firebase-crashlytics"))
                implementation(getLibrary("androidx-activity"))
            }
        }
    }
}