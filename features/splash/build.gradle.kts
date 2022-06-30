plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("io.gitlab.arturbosch.detekt")
    id("com.diffplug.spotless")
    id("org.jetbrains.dokka")
    id("androidx.navigation.safeargs.kotlin")
}

apply(from = "${rootProject.projectDir}/common.gradle")

detekt {
    config = files("../../quality/detekt_app_config.yml")
    // Define the detekt configuration(s) you want to use. Defaults to the default detekt configuration.
    buildUponDefaultConfig = false
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

android {
    flavorDimensions.add("caa_newsapp")
    productFlavors {
        create("develop") {
            dimension = "caa_newsapp"
        }
        create("prod") {
            dimension = "caa_newsapp"
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}
kapt {
    arguments {
        arg("dagger.validateTransitiveComponentDependencies", "DISABLED")
    }
}
dependencies {
    implementation(project(":core"))
    implementation(project(":ui"))
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(Libraries.whartonTimber)
    implementation(Libraries.appCompat)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.materialDesign)
    implementation(Libraries.androidLegacySupport)
    implementation(Libraries.fragmentKtx)
    implementation(Libraries.dagger)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerProcessor)
    testImplementation(Libraries.junitTestImp)
    testImplementation(Libraries.coroutineTestImp)
    testImplementation(Libraries.archCoreTestImp)
    testImplementation(Libraries.junitRunner)
    implementation(Libraries.mockitoTest)
    implementation(Libraries.kotlinTest)
    implementation(Libraries.httpLogging)
    implementation(Libraries.kotlinSerializationProperties)
    implementation(Libraries.kotlinSerializationJson)
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation(Libraries.okHttp)
    implementation(Libraries.retrofit)
    implementation(Libraries.kotlinSerializationConverter)
    implementation(Libraries.retrofitGson)
    implementation(Libraries.coroutinesCore)
    implementation(Libraries.coroutinesKotlinx)
    implementation(Libraries.viewModelKtx)
    implementation(Libraries.lifecycleRuntime)
    implementation(Libraries.coreKtx)
    implementation(Libraries.liveData)
    implementation(Libraries.navUi)
    implementation(Libraries.navFragment)
    androidTestImplementation(Libraries.runnerAndroidTestImp)
    androidTestImplementation(Libraries.expressoCoreAnroidTestImp)
    androidTestImplementation(Libraries.junitRunner)
}
