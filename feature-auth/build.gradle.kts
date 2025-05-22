plugins {
    id("android-feature-convention")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "ru.mareanexx.feature_auth"
}

dependencies {
    implementation(project(":core:common"))
    ksp(libs.hilt.compiler)
}