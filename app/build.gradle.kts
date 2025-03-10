plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "org.tejas.plantbiodictionary"
    compileSdk = 35

    defaultConfig {
        applicationId = "org.tejas.plantbiodictionary"
        minSdk = 21
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    // Core Android libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    dependencies {
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("com.android.volley:volley:1.2.1")
        implementation("com.squareup.okhttp3:okhttp:4.9.3")
    }

    // Firebase for image storage (optional)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.imagepicker)

        // Image handling
    implementation("com.github.dhaval2404:imagepicker:2.1")
    implementation(libs.monitor)


    // Testing libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
