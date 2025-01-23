plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.example.homework16"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.homework16"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding {
            enable = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation (libs.moshi.kotlin.codegen)
    implementation(libs.moshi)
    implementation (libs.moshi.kotlin)
    implementation (libs.glide)



    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation (libs.retrofit2.kotlin.coroutines.adapter)


    implementation(libs.kotlinx.serialization.json)
    implementation (libs.kotlinx.serialization.core)

    val fragment_version = "1.8.5"
    val nav_version = "2.8.5"

    api(libs.androidx.navigation.fragment.ktx)

    // Java language implementation
    implementation(libs.androidx.fragment)
    // Kotlin

    implementation(libs.retrofit)
    implementation (libs.converter.gson)
    implementation(libs.androidx.fragment.ktx)
    implementation (libs.play.services.ads)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.viewpager2)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}