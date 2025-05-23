plugins {
    id("android-app-convention")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":feature-auth"))
    implementation(project(":feature-settings"))

    ksp(libs.androidx.room.compiler)
    ksp(libs.hilt.compiler)
}