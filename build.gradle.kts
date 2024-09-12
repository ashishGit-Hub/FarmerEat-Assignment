buildscript {
    dependencies {
        classpath(libs.google.services)
//        classpath(libs.androidx.navigation.navigation.safe.args.gradle.plugin)
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    // Dagger 3
    alias(libs.plugins.daggerHiltAndroid) apply false
    alias(libs.plugins.jetbrainsKotlinKapt) apply false
}