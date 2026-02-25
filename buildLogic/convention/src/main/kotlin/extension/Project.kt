package extension

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import java.util.Properties

internal fun Project.getProperty(name: String, default: String = "") : String {
    return providers.gradleProperty(name).getOrElse(default)
}

internal val Project.catalogs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.getPluginId(alias: String): String {
    return catalogs.findPlugin(alias).get().get().pluginId
}

internal fun Project.getLibrary(alias: String): Provider<MinimalExternalModuleDependency> {
    return catalogs.findLibrary(alias).get()
}

internal fun Project.getBomLibrary(alias: String): Provider<MinimalExternalModuleDependency> {
    return project.dependencies.platform(getLibrary(alias))
}

internal fun Project.getBundle(alias: String): Provider<ExternalModuleDependencyBundle> {
    return catalogs.findBundle(alias).get()
}

internal fun Project.getDynamicNameSpace(): String {
    return path
        .replace(oldValue = ":", newValue = ".")
        .replace(oldValue = "-", newValue = ".")
        .replace(regex = Regex(pattern = "([a-z])([A-Z])"), replacement = "$1.$2")
        .let { "com$it" }
}

private fun Project.properties(name: String): String {
    val properties = Properties().apply {
        load(project.rootProject.file("keystore.properties").reader())
    }
    return properties.getProperty(name, "")
}

internal fun Project.configureAndroid(extension: ApplicationExtension) {
    extension.apply {
        compileSdk = findProperty("android.targetSdk").toString().toInt()

        androidResources {
            val locales = findProperty("android.localeSupport").toString()
                .replace(" ", "")
            localeFilters.addAll(locales.split(","))
        }
        defaultConfig {
            minSdk = findProperty("android.minSdk").toString().toInt()
            targetSdk = getProperty("android.targetSdk").toInt()
        }
        signingConfigs {
            create("release") {
                keyAlias = properties("key.alias")
                keyPassword = properties("key.password")
                storeFile = file(properties("store.file"))
                storePassword = properties("store.password")
            }
        }
        buildTypes {
            getByName("debug") {
                applicationIdSuffix = ".debug"
            }
            getByName("release") {
                signingConfig = signingConfigs.getByName("release")
                isMinifyEnabled = true
                isShrinkResources = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        buildFeatures {
            resValues = true
        }
    }
}
