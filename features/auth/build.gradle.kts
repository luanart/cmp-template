plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.kmp.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.image.loader)
        }
        commonTest.dependencies {
            implementation(libs.bundles.test)
        }
    }
}