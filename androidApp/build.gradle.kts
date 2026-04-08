plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.app"

    defaultConfig {
        applicationId = findProperty("app.id").toString()
        versionCode = findProperty("version.code").toString().toInt()
        versionName = findProperty("version.name").toString()
    }
    buildTypes {
        val name = findProperty("app.name").toString()

        getByName("debug") {
            resValue("string", "display_name", "$name Debug")
        }
        getByName("release") {
            resValue("string", "display_name", name)
        }
    }
}

dependencies {
    implementation(libs.koin.android)
}