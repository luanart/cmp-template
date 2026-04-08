import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import task.generateIosConfig

plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.kmp.compose)
    alias(libs.plugins.convention.kmp.koin)
}

kotlin {
    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            freeCompilerArgs += "-Xbinary=bundleId=${findProperty("app.id")}"

            export(project(":firebase:analytics"))
        }
    }

    android {
        compileSdk {
            version = release(findProperty("android.targetSdk").toString().toInt())
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            api(projects.firebase.analytics)
            implementation(projects.resources)
            implementation(projects.navigation)
            implementation(projects.core.data)
            implementation(projects.core.common)
            implementation(projects.core.presentation)

            // features
            implementation(projects.features.auth)
            implementation(projects.features.home)

            // dependencies
            implementation(libs.navigation.event)
            implementation(libs.bundles.lifecycle)
            implementation(libs.bundles.koin.compose)
        }
    }

    afterEvaluate {
        generateIosConfig()
    }
}