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

    implementation(Libraries.whartonTimber)
    implementation(Libraries.appCompat)
    implementation(Libraries.materialDesign)
    testImplementation(Libraries.junitTestImp)
    testImplementation(Libraries.coroutineTestImp)
    testImplementation(Libraries.archCoreTestImp)
    testImplementation(Libraries.junitRunner)
    testImplementation(Libraries.roboElectric)

    androidTestImplementation(Libraries.runnerAndroidTestImp)
    androidTestImplementation(Libraries.expressoCoreAnroidTestImp)
    androidTestImplementation(Libraries.junitRunner)
}
