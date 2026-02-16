plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.kmp.compose)
    alias(libs.plugins.mokkery)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.navigation.event)
            implementation(libs.bundles.lifecycle)
            implementation(projects.resources)
            implementation(projects.navigation)
            implementation(projects.core.common)
            implementation(projects.firebase.analytics)
        }
        commonTest.dependencies {
            implementation(libs.bundles.test)
        }
    }
}