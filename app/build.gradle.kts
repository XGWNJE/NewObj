plugins {
    alias(libs.plugins.androidApplication) // Using alias from libs.versions.toml
    alias(libs.plugins.jetbrainsKotlinAndroid) // Using alias from libs.versions.toml
    alias(libs.plugins.jetbrainsKotlinCompose) // APPLY THE PLUGIN HERE
}

android {
    namespace = "com.xgwnje.sentinel" // Your application's package name
    compileSdk = libs.versions.compileSdkNum.get().toInt() // Get from TOML

    defaultConfig {
        applicationId = "com.xgwnje.sentinel"
        minSdk = libs.versions.minSdkNum.get().toInt()     // Get from TOML
        targetSdk = libs.versions.targetSdkNum.get().toInt() // Get from TOML
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Configure as needed
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Or JavaVersion.VERSION_17 if you prefer
        targetCompatibility = JavaVersion.VERSION_1_8 // Or JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "1.8" // Or "17"
        // For Compose compiler stability with Kotlin 2.0.0, specific arguments might be needed
        // if you encounter issues, but often not necessary with the latest Compose compiler.
        // freeCompilerArgs += listOf(
        //     "-P",
        //     "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true"
        // )
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get() // Get from TOML
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // CameraX (用于摄像头功能)
    val cameraxVersion = "1.3.4" // 使用一个稳定的 CameraX 版本
    implementation("androidx.camera:camera-core:${cameraxVersion}")
    implementation("androidx.camera:camera-camera2:${cameraxVersion}")
    implementation("androidx.camera:camera-lifecycle:${cameraxVersion}")
    implementation("androidx.camera:camera-view:${cameraxVersion}")

    // Accompanist Permissions (用于在 Compose 中更方便地处理权限)
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose Bill of Materials (BOM) - manages versions for other Compose libraries
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.iconsCore)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom)) // For Compose testing BOM
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling) // For Compose tooling (preview, inspection)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.google.android.material)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.datastore.preferences)

}