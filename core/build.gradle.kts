plugins {
    id("android-core-convention")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.compose)
}

dependencies {
    ksp(libs.hilt.compiler)
}