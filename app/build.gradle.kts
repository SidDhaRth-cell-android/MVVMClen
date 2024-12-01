plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
  id("kotlin-kapt")
  id("com.google.dagger.hilt.android")
  id("kotlin-parcelize")
}

android {
  namespace = "com.flutteroid.airteltask"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.flutteroid.airteltask"
    minSdk = 24
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  dataBinding {
    enable = true
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


  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.constraintlayout)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)


  // Retrofit for network calls
  implementation(libs.retrofit)
  implementation(libs.converter.gson)

  // OkHttp for networking
  implementation(libs.okhttp)
  implementation(libs.logging.interceptor)

  // Room for local database
  implementation(libs.androidx.room.runtime)
  implementation(libs.androidx.room.ktx)
  kapt (libs.androidx.room.compiler)

  // Dagger-Hilt for Dependency Injection
  implementation(libs.hilt.android)
  kapt(libs.hilt.compiler)

  // Coroutine and Flow support
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.androidx.lifecycle.viewmodel.ktx)

  implementation(libs.androidx.core.splashscreen)

  implementation(libs.glide)

  implementation(libs.converter.moshi)
  implementation(libs.moshi)
  implementation(libs.moshi.kotlin)


}

kapt {
  correctErrorTypes = true
}