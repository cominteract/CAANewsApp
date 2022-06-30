import Versions.airbnbVersion
import Versions.appCompatVersion
import Versions.archCoreTestVersion
import Versions.chuckerVersion
import Versions.constraintVersion
import Versions.coroutineCore
import Versions.coroutineTestVersion
import Versions.daggerVersion
import Versions.dataStoreVersion
import Versions.expressoCoreVersion
import Versions.facebookVersion
import Versions.firebaseBomVersion
import Versions.firebaseMessaging
import Versions.glideVersion
import Versions.hiltAndroidXVersion
import Versions.hiltDaggerTestVersion
import Versions.hiltVersion
import Versions.hiltViewModelVersion
import Versions.httpLoggingVersion
import Versions.imageZoom
import Versions.junitRunnerVersion
import Versions.junitVersion
import Versions.kotlinSerializationConverterVersion
import Versions.kotlinSerializationJsonVersion
import Versions.kotlinSerializationPropertiesVersion
import Versions.legacySupportVersion
import Versions.lifecycleRunTimeKtxVersion
import Versions.liveDataVersion
import Versions.markwonVersion
import Versions.materialVersion
import Versions.navigationVersion
import Versions.okHttpVersion
import Versions.pagingVersion
import Versions.phoneNumberKitVersion
import Versions.pusherBeamsVersion
import Versions.retrofitVersion
import Versions.roboElectricVersion
import Versions.roomVersion
import Versions.runnerVersion
import Versions.timberVersion
import Versions.viewModelKtxVersion
import Versions.workVersion
import Versions.easyPermissionVersion
import Versions.googleLocationVersion
import Versions.googleMapsVersion
import Versions.kotlinVersion
import Versions.mockitoVersion
import Versions.pusherClientVersion
import Versions.pusherSocketVersion

object Versions {
    val versionName = "1.0.0" // X.Y.Z; X = Major, Y = minor, Z = Patch level
    private val versionCodeBase = 1 // XYYZZM; M = Module (tv, mobile)
    val versionCodeMobile = versionCodeBase + 3

    const val COMPILE_SDK = 31
    const val TARGET_SDK = 30
    const val MIN_SDK = 24
    const val APPLICATION_ID = "com.ainsigne.caa_newsapp"

    const val ANALYTICS = ""
    const val CRASHLYTICS = "17.2.2"

    const val ANDROID_GRADLE_PLUGIN = "7.1.2"
    const val FIREBASE_CRASHLYTICS = "2.3.0"
    const val GOOGLE_SERVICES = "4.3.3"
    const val KOTLIN = "1.5.10"
    const val NAVIGATION = "2.3.5"
    const val legacySupportVersion = "1.0.0"

    // unit test version
    const val junitVersion = "4.13.2"
    const val runnerVersion = "1.0.2"
    const val expressoCoreVersion = "3.0.2"
    const val coroutineTestVersion = "1.5.0"
    const val archCoreTestVersion = "2.1.0"
    const val hiltDaggerTestVersion = "2.28-alpha"
    const val junitRunnerVersion = "1.4.0"
    const val roboElectricVersion = "4.6.1"
    const val mockitoVersion = "3.12.4"
    // hilt dagger
    const val hiltVersion = "2.38.1"
    const val hiltViewModelVersion = "1.0.0-alpha03"
    const val hiltAndroidXVersion = "1.0.0"
    const val hiltTestVersion = "2.28-alpha"

    // firebase
    const val firebaseBomVersion = "29.0.0"


    // utilities
    const val timberVersion = "4.7.1"
    const val materialVersion = "1.4.0"
    const val constraintVersion = "2.1.0"
    const val appCompatVersion = "1.3.1"
    const val roomVersion = "2.4.0"
    const val retrofitVersion = "2.9.0"
    const val httpLoggingVersion = "4.9.0"
    const val okHttpVersion = "4.9.0"
    const val kotlinVersion = "1.4.2"
    const val kotlinSerializationPropertiesVersion = "1.3.0"
    const val kotlinSerializationJsonVersion = "1.3.0"
    const val kotlinSerializationConverterVersion = "0.8.0"
    const val coroutineCore = "1.5.2"
    const val navigationVersion = "2.4.1"
    const val annotationVersion = "28.0.0"
    const val viewModelKtxVersion = "2.3.0"
    const val lifecycleRunTimeKtxVersion = "2.3.1"
    const val workVersion = "2.7.0-alpha05"
    const val liveDataVersion = "2.3.1"
    const val dataStoreVersion = "1.0.0"
    const val airbnbVersion = "4.1.0"
    const val pagingVersion = "3.1.1"
    const val chuckerVersion = "3.5.2"
    const val pusherBeamsVersion = "1.7.0"
    const val pusherClientVersion = "1.8.0"
    const val pusherSocketVersion = "0.5.0"
    const val firebaseMessaging = "20.2.3"
    const val firebaseAuth = "21.0.1"
    const val facebookVersion = "12.0.0"
    const val imageZoom = "2.3.0"
    const val glideVersion = "4.12.0"
    const val markwonVersion = "4.6.2"
    const val phoneNumberKitVersion = "8.12.18.1"
    const val googleMapsVersion = "18.0.2"
    const val googleLocationVersion = "19.0.1"
    const val easyPermissionVersion = "1.0.0"
    const val daggerVersion = "2.41"

}

object Libraries {
    //android support
    const val androidLegacySupport = "androidx.legacy:legacy-support-v4:$legacySupportVersion"
    const val materialDesign = "com.google.android.material:material:$materialVersion"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintVersion"
    const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelKtxVersion"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRunTimeKtxVersion"
    const val appInspectorRuntime = "androidx.work:work-runtime-ktx:$workVersion"
    const val coreKtx = "androidx.core:core-ktx:1.6.0"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$liveDataVersion"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:1.4.0-alpha03"
    const val fragment = "androidx.fragment:fragment-ktx:1.2.0"
    const val activity = "androidx.activity:activity-ktx:1.3.0"
    const val airbnb = "com.airbnb.android:lottie:$airbnbVersion"
    const val pagingLibrary = "androidx.paging:paging-runtime-ktx:$pagingVersion"
    const val pagingCommon = "androidx.paging:paging-common:$pagingVersion"
    const val pagingCommonKtx = "androidx.paging:paging-common-ktx:$pagingVersion"


    //Navigation
    const val navFragment = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
    const val navUi = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
    const val navRuntime = "androidx.navigation:navigation-runtime-ktx:$navigationVersion"
    //SwipeRefresh
    const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"



    //local storage
    const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
    const val roomKtx = "androidx.room:room-ktx:$roomVersion"
    const val kaptRoom = "androidx.room:room-compiler:$roomVersion"
    const val roomPaging = "androidx.room:room-paging:2.4.0-alpha04"

    //retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"


    //kotlin serialization
    const val kotlinSerializationConverter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$kotlinSerializationConverterVersion"
    const val kotlinSerializationJson =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationJsonVersion"
    const val kotlinSerializationProperties =
        "org.jetbrains.kotlinx:kotlinx-serialization-properties:$kotlinSerializationPropertiesVersion"

    //kotlin
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"

    //okHttp
    const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"

    //coroutine
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineCore"
    const val coroutinesKotlinx = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineCore"

    //logging
    const val whartonTimber = "com.jakewharton.timber:timber:$timberVersion"
    const val httpLogging = "com.squareup.okhttp3:logging-interceptor:$httpLoggingVersion"

    // unit test
    const val junitTestImp = "junit:junit:$junitVersion"
    const val runnerAndroidTestImp = "androidx.test:runner:$runnerVersion"

    const val expressoCoreAnroidTestImp = "androidx.test.espresso:espresso-core:$expressoCoreVersion"
    const val coroutineTestImp = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineTestVersion"
    const val archCoreTestImp = "androidx.arch.core:core-testing:$archCoreTestVersion"
    const val hiltDaggerTestImp = "com.google.dagger:hilt-android-testing:$hiltDaggerTestVersion"
    const val junitRunner = "androidx.test:runner:$junitRunnerVersion"
    const val kotlinTest = "org.jetbrains.kotlin:kotlin-test-junit:1.5.20"

    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineTestVersion"

    const val mockitoTest = "org.mockito:mockito-core:$mockitoVersion"

    const val dagger = "com.google.dagger:dagger:${daggerVersion}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${daggerVersion}"
    const val daggerProcessor =  "com.google.dagger:dagger-android-processor:${daggerVersion}"
    // dependency Injection
    const val hiltDagger = "com.google.dagger:hilt-android:$hiltVersion"
    const val hiltDaggerKaptCompiler = "com.google.dagger:hilt-compiler:$hiltVersion"

    // dependency injection viewModel
    const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:$hiltViewModelVersion"

    const val kaptAndroidXCompiler = "androidx.hilt:hilt-compiler:$hiltAndroidXVersion"
    const val roboElectric = "org.robolectric:robolectric:$roboElectricVersion"

    // datastore
    const val dataStorePreference = "androidx.datastore:datastore-preferences:${dataStoreVersion}"

    // chucker
    const val chuckerDebug = "com.github.chuckerteam.chucker:library:${chuckerVersion}"
    const val chuckerProd = "com.github.chuckerteam.chucker:library-no-op:${chuckerVersion}"

    // pusher
    const val pusherBeams = "com.pusher:push-notifications-android:${pusherBeamsVersion}"
    const val pusherClient = "com.pusher:pusher-java-client:${pusherClientVersion}"
    const val pusherSocket = "com.pusher:pusher-websocket-android:${pusherSocketVersion}"

    // image zooom
    const val imageZoomLib = "it.sephiroth.android.library.imagezoom:imagezoom:$imageZoom"

    const val firebaseBom = "com.google.firebase:firebase-bom:$firebaseBomVersion"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"

    // firebase
    const val fireBaseMessaging = "com.google.firebase:firebase-messaging:${firebaseMessaging}"
    const val firebaseAuth = "com.google.firebase:firebase-auth-ktx:${Versions.firebaseAuth}"

    // facebook
    const val facebook = "com.facebook.android:facebook-android-sdk:$facebookVersion"

    // glide
    const val glide = "com.github.bumptech.glide:glide:$glideVersion"
    const val glideCompiler = "com.github.bumptech.glide:compiler:$glideVersion"

    // markWon markdown
    const val markwonCore = "io.noties.markwon:core:$markwonVersion"
    const val markwonPicasso = "io.noties.markwon:image-picasso:$markwonVersion"
    const val markwonStrikeThrough = "io.noties.markwon:ext-strikethrough:$markwonVersion"
    const val markwonTables = "io.noties.markwon:ext-tables:$markwonVersion"
    const val markwonTaskList = "io.noties.markwon:ext-tasklist:$markwonVersion"
    const val markwonLinkify = "io.noties.markwon:linkify:$markwonVersion"

    // Phone number utility
    const val phoneNumberKit = "com.lionscribe.open.libphonenumber:libphonenumber:$phoneNumberKitVersion"

    // google
    const val googleMaps = "com.google.android.gms:play-services-maps:$googleMapsVersion"
    const val googleLocation = "com.google.android.gms:play-services-location:$googleLocationVersion"

    // easy permission
    const val easyPermission = "com.vmadalin:easypermissions-ktx:$easyPermissionVersion"


}