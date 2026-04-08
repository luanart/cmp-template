plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.kmp.koin)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            androidMain.dependencies {
                implementation(libs.ktor.okhttp)
                implementation(libs.google.tink.android)
            }
            commonMain.dependencies {
                implementation(projects.core.common)
                implementation(libs.bundles.ktor)
                implementation(libs.bundles.datastore)
            }
            iosMain.dependencies {
                implementation(libs.ktor.darwin)
            }
        }
    }
}