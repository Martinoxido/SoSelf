plugins {
    id("com.android.application")
}

android {
    namespace = "com.mgajardo.SoSelf"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mgajardo.SoSelf"
        minSdk = 24
        targetSdk = 34
        versionCode = 2
        versionName = "1.1"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("org.osmdroid:osmdroid-android:6.1.8")
    implementation("org.osmdroid:osmdroid-wms:6.1.11")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.gms:play-services-location:17.1.0")
    implementation ("org.osmdroid:osmdroid-android:6.1.10")

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("org.osmdroid:osmdroid-wms:6.1.11")
    
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}