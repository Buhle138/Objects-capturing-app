plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.assignmentparttwo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.assignmentparttwo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    implementation("androidx.compose.ui:ui:1.6.7")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.7")

    implementation("com.google.maps.android:maps-compose:2.15.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-firestore:25.0.0")
    implementation("com.google.firebase:firebase-database:21.0.0")

    val nav_version = "2.7.4"

    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation("androidx.navigation:navigation-compose:2.7.4")
    //Compose view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    //Network call, connecting to the internet and collecting data from the internet
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")

    //Loading the image from the internet
    implementation("io.coil-kt:coil-compose:2.4.0")

    //Json things
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
    //Json to Kotlin Object mapping
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //Extra icons
    implementation("androidx.compose.material:material-icons-extended-android:1.6.6")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    val cameraxVersion = "1.3.0-rc01"
    //noinspection GradleDependency
    implementation("androidx.camera:camera-core:$cameraxVersion")
    //noinspection GradleDependency
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    //noinspection GradleDependency
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    //noinspection GradleDependency
    implementation("androidx.camera:camera-video:$cameraxVersion")

    //noinspection GradleDependency
    implementation("androidx.camera:camera-view:$cameraxVersion")
    //noinspection GradleDependency
    implementation("androidx.camera:camera-extensions:$cameraxVersion")
}