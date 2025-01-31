import com.google.protobuf.gradle.id

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization") version "2.0.21"
    id("androidx.navigation.safeargs.kotlin")
    id ("com.google.protobuf") version "0.9.4"
}

android {
    namespace = "com.example.anotherdesperatetry"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.anotherdesperatetry"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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


    buildFeatures {
        viewBinding {
            enable = true
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

    implementation("com.squareup.moshi:moshi:1.15.2")
    implementation ("com.squareup.moshi:moshi-kotlin:1.15.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")



    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.5.0")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")


    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-core:1.8.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation(libs.androidx.viewbinding)

    val fragment_version = "1.8.5"
    val nav_version = "2.8.5"

    val paging_version = "3.3.5"

    implementation("androidx.paging:paging-runtime:$paging_version")

    api("androidx.navigation:navigation-fragment-ktx:$nav_version")

    // Java language implementation
    implementation("androidx.fragment:fragment:$fragment_version")
    // Kotlin

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.fragment:fragment-ktx:$fragment_version")
    implementation ("com.google.android.gms:play-services-ads:23.6.0")
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")
    implementation("androidx.viewpager2:viewpager2:1.1.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    val version_datastore = "1.1.2"
    val proto_buf = "3.24.0"
    implementation  ("androidx.datastore:datastore:$version_datastore")
    implementation ("com.google.protobuf:protobuf-kotlin:$proto_buf")
}


protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.24.1"
    }


    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option ("lite")
                }

                id("kotlin") {
                    option("lite")
                }
            }
        }
    }
}