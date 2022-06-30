plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
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
            buildConfigField(
                "String",
                configuration.BuildConfigKeys.API_BASE_URL,
                "\"${configuration.DEVELOP.SERVER_PREFIX}\""
            )
            buildConfigField(
                "String",
                configuration.BuildConfigKeys.API_BASE_KEY,
                "\"${configuration.DEVELOP.API_BASE_KEY}\""
            )
        }
        create("prod") {
            dimension = "caa_newsapp"
            buildConfigField(
                "String",
                configuration.BuildConfigKeys.API_BASE_URL,
                "\"${configuration.PROD.SERVER_PREFIX}\""
            )
            buildConfigField(
                "String",
                configuration.BuildConfigKeys.API_BASE_KEY,
                "\"${configuration.PROD.API_BASE_KEY}\""
            )
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
    implementation(project(":domain"))
    implementation(project(":common"))
    implementation(Libraries.dagger)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerProcessor)
    testImplementation(Libraries.junitTestImp)
    testImplementation(Libraries.coroutineTestImp)
    testImplementation(Libraries.archCoreTestImp)
    testImplementation(Libraries.junitRunner)
    implementation(Libraries.mockitoTest)
    implementation(Libraries.kotlinTest)
    implementation(Libraries.coroutinesCore)
    implementation(Libraries.coroutinesKotlinx)
    implementation(Libraries.whartonTimber)
    implementation(Libraries.httpLogging)
    implementation(Libraries.roomKtx)
    implementation(Libraries.roomRuntime)
    implementation(Libraries.roomPaging)
    kapt(Libraries.kaptRoom)
    implementation(Libraries.dataStorePreference)
    implementation(Libraries.kotlinSerializationProperties)
    implementation(Libraries.kotlinSerializationJson)
    implementation(Libraries.okHttp)
    implementation(Libraries.retrofit)
    implementation(Libraries.kotlinSerializationConverter)
    implementation(Libraries.retrofitGson)
    implementation(Libraries.pagingCommonKtx)
    implementation(Libraries.pagingCommon)
    implementation(Libraries.pagingLibrary)
    androidTestImplementation(Libraries.coroutineTest)
    androidTestImplementation(Libraries.runnerAndroidTestImp)
    androidTestImplementation(Libraries.expressoCoreAnroidTestImp)
    androidTestImplementation(Libraries.junitRunner)
}
