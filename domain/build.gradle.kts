plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("io.gitlab.arturbosch.detekt")
    id("com.diffplug.spotless")
    id("org.jetbrains.dokka")
}

apply(from = "${rootProject.projectDir}/common.gradle")

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
}

detekt {
    config = files("../quality/detekt_app_config.yml")
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

dependencies {
    implementation(Libraries.coroutinesCore)
    implementation(Libraries.coroutinesKotlinx)
    implementation(project(":common"))
    testImplementation(Libraries.junitTestImp)
    testImplementation(Libraries.coroutineTestImp)
    testImplementation(Libraries.archCoreTestImp)
    testImplementation(Libraries.junitRunner)
    implementation(Libraries.dagger)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerProcessor)
    implementation(Libraries.roomKtx)
    implementation(Libraries.roomRuntime)
    implementation(Libraries.roomPaging)
    kapt(Libraries.kaptRoom)
    implementation(Libraries.whartonTimber)
    implementation(Libraries.httpLogging)
    implementation(Libraries.kotlinSerializationProperties)
    implementation(Libraries.kotlinSerializationJson)
    implementation(Libraries.okHttp)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitGson)
    implementation(Libraries.kotlinSerializationConverter)
    implementation(Libraries.pagingCommonKtx)
    implementation(Libraries.pagingCommon)
    implementation(Libraries.pagingLibrary)
    androidTestImplementation(Libraries.coroutineTest)
    androidTestImplementation(Libraries.runnerAndroidTestImp)
    androidTestImplementation(Libraries.expressoCoreAnroidTestImp)
    androidTestImplementation(Libraries.junitRunner)
}
