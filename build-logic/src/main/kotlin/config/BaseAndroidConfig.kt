package config

import com.android.build.gradle.BaseExtension

fun BaseExtension.baseAndroidConfig() {
    setCompileSdkVersion(BuildConfigConsts.COMPILE_SDK)

    compileOptions {
        sourceCompatibility = BuildConfigConsts.COMPILE_JDK_VERSION
        targetCompatibility = BuildConfigConsts.COMPILE_JDK_VERSION
    }

    kotlinOptions {
        jvmTarget = BuildConfigConsts.KOTLIN_JVM_TARGET
    }
}

fun BaseExtension.appDefaultConfig() {
    namespace = BuildConfigConsts.APPLICATION_ID

    defaultConfig {
        applicationId = BuildConfigConsts.APPLICATION_ID
        minSdk = BuildConfigConsts.MIN_SDK
        targetSdk = BuildConfigConsts.TARGET_SDK

        testInstrumentationRunner = BuildConfigConsts.TEST_INSTRUMENTATION_RUNNER
    }
}

fun BaseExtension.featureDefaultConfig() {
    defaultConfig {
        minSdk = BuildConfigConsts.MIN_SDK
        targetSdk = BuildConfigConsts.TARGET_SDK

        testInstrumentationRunner = BuildConfigConsts.TEST_INSTRUMENTATION_RUNNER
    }
}