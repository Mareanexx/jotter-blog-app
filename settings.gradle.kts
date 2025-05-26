pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "JotterBlogApp"
include(
    ":app",
    ":core",
    ":core:data",
    ":core:common",
    ":feature-auth",
    ":feature-settings",
    ":feature-collections"
)