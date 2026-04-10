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

private fun Project.loadKeystoreProperties(): Properties? {
    val file = rootProject.file("keystore.properties")
    if (!file.exists()) return null

    return Properties().apply {
        load(file.reader())
    }
}

internal fun Project.configureAndroid(extension: ApplicationExtension) {
    val keystoreProps = loadKeystoreProperties()

    extension.apply {
        compileSdk = findProperty("android.targetSdk").toString().toInt()

        androidResources {
            val locales = findProperty("app.locale").toString()
                .replace(" ", "")
                .split(",")
            localeFilters.addAll(locales)
        }
        defaultConfig {
            minSdk = findProperty("android.minSdk").toString().toInt()
            targetSdk = getProperty("android.targetSdk").toInt()
        }
        signingConfigs {
            if (keystoreProps != null) {
                create("release") {
                    keyAlias = keystoreProps.getProperty("key.alias")
                    keyPassword = keystoreProps.getProperty("key.password")
                    storeFile = file(keystoreProps.getProperty("store.file"))
                    storePassword = keystoreProps.getProperty("store.password")
                }
            }
        }
        buildTypes {
            getByName("debug") {
                applicationIdSuffix = ".debug"
            }
            getByName("release") {
                if (keystoreProps != null) {
                    signingConfig = signingConfigs.getByName("release")
                }

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
