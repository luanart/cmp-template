import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.kmp.koin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.buildkonfig)
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

buildkonfig {
    packageName = "com.core.data"

    defaultConfigs {
        buildConfigField(BOOLEAN, "isDebug", "true")
        buildConfigField(STRING, "baseUrl", findProperty("base.url.debug").toString())
    }

    defaultConfigs("release") {
        buildConfigField(BOOLEAN, "isDebug", "false")
        buildConfigField(STRING, "baseUrl", findProperty("base.url.release").toString())
    }
}