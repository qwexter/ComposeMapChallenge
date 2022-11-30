plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "xyz.qwexter.mapscocase"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "xyz.qwexter.mapscocase"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
//        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.deps.androidx.core.ktx)
    implementation(libs.deps.androidx.lifecycle.ktx)

    implementation(libs.deps.material)

    implementation(libs.deps.androidx.activity.compose)

    implementation(platform(libs.deps.compose.bom))
    implementation(libs.deps.compose.ui)
    implementation(libs.deps.compose.ui.tooling.preview)
    implementation(libs.deps.compose.ui.test.manifest)
    implementation(libs.deps.compose.ui.material)

    implementation(libs.deps.maps.ktx.std)
    implementation(libs.deps.maps.utils)
    implementation(libs.deps.maps.compose)
    implementation(libs.deps.maps.playservice)

    testImplementation(libs.deps.test.junit)

    androidTestImplementation(platform(libs.deps.compose.bom))
    androidTestImplementation(libs.deps.androidx.test.espresso.core)
    androidTestImplementation(libs.deps.androidx.ext.junit)
    androidTestImplementation(libs.deps.androidx.compose.ui.ui.test.junit4)

    debugImplementation(libs.deps.test.compose.ui.ui.tooling)
    debugImplementation(libs.deps.compose.ui.test.manifest)
}