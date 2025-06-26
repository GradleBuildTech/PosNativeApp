plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id(BuildPlugins.kspId)
    id(BuildPlugins.daggerHiltPlugin)
}

val configProperties = BuildConfig.projectConfigurations(project)

android {
    namespace = Android.applicationId
    compileSdk = Android.compileSdk

    defaultConfig {
        minSdk = Android.minSdk
        configProperties.forEach { key, value ->
            buildConfigField("String", key.toString(), "\"${value}\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
    }

}

dependencies {
    //🎉Module
    implementation(project(":core"))

    // 🧱 Core AndroidX & Lifecycle
    implementation(Dependencies.androidxCoreKtx)
    implementation(Dependencies.androidxAppCompat)

    // 📦 Jetpack Compose UI
    implementation(Dependencies.material)

    // 🧪 Instrumented Testing (UI test, Espresso, etc.)
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.androidxJunit)
    androidTestImplementation(Dependencies.espressoCore)


    // 🛜 Networking
    implementation(Dependencies.retrofitCore)
    implementation(Dependencies.retrofitMoshiConverter)
    implementation(Dependencies.retrofitGsonConverter)

    implementation(Dependencies.okhttpLoggingInterceptor)


    // 🙈 Adaptors
    ksp(Dependencies.moshiCodegen)
    implementation(Dependencies.moshiKotlin)
    implementation(Dependencies.moshi)
    implementation(Dependencies.moshiAdapters)

    // 🎉 Hilt
    ksp(Dependencies.hiltCompiler)
    implementation(Dependencies.hiltCore)
}