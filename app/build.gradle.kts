import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(27)

    defaultConfig {
        applicationId = "fr.jeantuffier.tweetics"
        minSdkVersion(21)
        targetSdkVersion(27)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/java", "src/main/kotlin")
        getByName("test").java.srcDirs("src/test/java", "src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/java", "src/androidTest/kotlin")
    }

}

tasks.withType(Test::class) {
    useJUnitPlatform()
}

dependencies {
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
    implementation("com.android.support:appcompat-v7:27.1.1")

    implementation("com.android.support.constraint:constraint-layout:1.1.3")
    implementation("com.google.code.gson:gson:2.8.2")

    implementation("com.android.support:recyclerview-v7:27.1.1")
    implementation("com.android.support:animated-vector-drawable:27.1.1")
    implementation("com.android.support:exifinterface:27.1.1")
    implementation("com.android.support:support-media-compat:27.1.1")
    implementation("com.android.support:support-v4:27.1.1")
    implementation("com.android.support:design:27.1.1")

    //Room
    implementation("android.arch.persistence.room:runtime:1.1.1")
    implementation("android.arch.persistence.room:rxjava2:1.1.1")
    kapt("android.arch.persistence.room:compiler:1.1.1")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.4.0")
    implementation("com.squareup.retrofit2:converter-gson:2.4.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.4.0")

    implementation("com.google.code.findbugs:jsr305:3.0.2")

    //RxJava
    implementation("io.reactivex.rxjava2:rxjava:2.1.16")
    implementation("io.reactivex.rxjava2:rxandroid:2.0.2")

    //Picasso
    implementation("com.squareup.picasso:picasso:2.71828")

    //Koin
    implementation("org.koin:koin-android:0.9.3")

    //Test
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.1.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.1.1")

    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
    androidTestImplementation("android.arch.core:core-testing:1.1.1")

    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.1.10")
    testImplementation("io.mockk:mockk:1.8.7")
}
