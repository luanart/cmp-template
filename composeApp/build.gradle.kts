import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import task.generateIosConfig

plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.kmp.compose)
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
            implementation(projects.core.common)
            implementation(projects.core.data)
            implementation(projects.core.presentation)

            // dependencies
            implementation(libs.koin.compose)
            implementation(libs.navigation.event)

            // features
            implementation(projects.features.auth)
            implementation(projects.features.home)
        }
    }

    afterEvaluate {
        generateIosConfig()
    }
}