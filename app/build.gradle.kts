

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("io.gitlab.arturbosch.detekt")
    id("com.diffplug.spotless")
    id("com.google.firebase.crashlytics")
    id("org.jetbrains.dokka")
}

apply(from = "../tools/ktlint.gradle.kts")

// TODO : In case force update needs to be tested app gradle version and version code can be changed
android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.ainsigne.caa_newsapp"
        targetSdk = 31
        minSdk = 24
        versionCode = 24
        versionName = "1.0"
        testInstrumentationRunner = "com.ainsigne.HiltRunnerTest"
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions.add("caa_newsapp")
    productFlavors {
        create("develop") {
            dimension = "caa_newsapp"
            applicationId = "com.ainsigne.caa_newsapp"
            versionNameSuffix = ""
        }
        create("prod") {
            dimension = "caa_newsapp"
            applicationId = "com.ainsigne.caa_newsapp"
            versionNameSuffix = "_prod"
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    testOptions.unitTests.isIncludeAndroidResources = true
}

detekt {
    config = files("../quality/detekt_app_config.yml")
    // Define the detekt configuration(s) you want to use. Defaults to the default detekt configuration.
    buildUponDefaultConfig = false
    input =
        files( // The directories where detekt looks for source files. Defaults to `files("src/main/java", "src/main/kotlin")`.
            "src/main/kotlin",
            "src/main/java"
        )
    parallel = false
    debug = false
    // Adds debug output during task execution. `false` by default.
    ignoreFailures = false
    // If set to `true` the build does not fail when the maxIssues count was reached. Defaults to `false`.
    reports {
        xml {
            enabled = true
            // Enable/Disable XML report (default: true)
            destination = file("reports/detekt.xml")
            // Path where XML report will be stored (default: `build/reports/detekt/detekt.xml`)
        }
        html {
            enabled = true
            // Enable/Disable HTML report (default: true)
            destination = file("reports/detekt.html")
            // Path where HTML report will be stored (default: `build/reports/detekt/detekt.html`)
        }
    }
}

spotless {
    kotlin {
        target("**/*.kt")
        ktlint("0.42.1")
    }
    kotlinGradle {
        target("*.gradle.kts", "additionalScripts/*.gradle.kts")
        ktlint("0.42.1")
    }
}

dependencies {
    implementation(project(":ui"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":core"))
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":features:home"))
    implementation(project(":features:splash"))
    implementation(Libraries.appCompat)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.materialDesign)
    implementation(Libraries.androidLegacySupport)
    implementation(Libraries.fragmentKtx)
    implementation(Libraries.fragment)
    implementation(Libraries.activity)
    implementation(Libraries.airbnb)
    implementation(Libraries.appInspectorRuntime)
    implementation(Libraries.navFragment)
    implementation(Libraries.navUi)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitGson)
    implementation(Libraries.pagingLibrary)
    implementation(Libraries.dagger)
    implementation(Libraries.appCompat)
    implementation(Libraries.materialDesign)
    implementation(Libraries.constraintLayout)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerProcessor)
    implementation(Libraries.roomKtx)
    implementation(Libraries.roomRuntime)
    implementation(Libraries.roomPaging)
    kapt(Libraries.kaptRoom)
    implementation(Libraries.coreKtx)
    implementation(Libraries.coroutinesCore)
    implementation(Libraries.coroutinesKotlinx)
    implementation(Libraries.liveData)
    implementation(Libraries.appInspectorRuntime)
    implementation(Libraries.whartonTimber)
    implementation(Libraries.httpLogging)
    implementation(Libraries.viewModelKtx)
    implementation(Libraries.lifecycleRuntime)
    implementation(Libraries.imageZoomLib)
    implementation(Libraries.fireBaseMessaging)
    implementation(Libraries.firebaseAuth)
    implementation(platform(Libraries.firebaseBom))
    implementation(Libraries.firebaseCrashlytics)
    implementation(Libraries.firebaseAnalytics)
    testImplementation(Libraries.junitTestImp)
    testImplementation(Libraries.coroutineTestImp)
    testImplementation(Libraries.archCoreTestImp)
    testImplementation(Libraries.junitRunner)
    testImplementation(Libraries.roboElectric)
    testImplementation(Libraries.coroutineTest)
    testImplementation(Libraries.mockitoTest)
    testImplementation(Libraries.kotlinTest)
    androidTestImplementation(Libraries.coroutineTest)
    androidTestImplementation(Libraries.runnerAndroidTestImp)
    androidTestImplementation(Libraries.expressoCoreAnroidTestImp)
    androidTestImplementation(Libraries.junitRunner)
}
