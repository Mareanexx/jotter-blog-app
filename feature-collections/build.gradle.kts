plugins {
    id("android-feature-convention")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "ru.mareanexx.feature_collections"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    ksp(libs.hilt.compiler)
}