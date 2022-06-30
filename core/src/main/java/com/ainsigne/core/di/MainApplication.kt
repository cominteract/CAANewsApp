package com.ainsigne.core.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.fragment.app.Fragment
import com.ainsigne.common.utils.network.NetworkMonitoringUtil
import com.ainsigne.core.BuildConfig
import com.ainsigne.core.di.components.CoreComponent
import com.ainsigne.core.di.components.DaggerCoreComponent
import timber.log.Timber

/**
 * Main app for caa news app
 */
class MainApplication : Application() {

    var networkMonitoringUtil: NetworkMonitoringUtil? = null

    /**
     * Activates Timber for logging
     * Activates network monitoring for real time internet connection detection
     */
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        val mNetworkRequest: NetworkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
        val mConnectivityManager: ConnectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkMonitoringUtil = NetworkMonitoringUtil(
            mNetworkRequest,
            mConnectivityManager
        )
        networkMonitoringUtil?.checkNetworkState()
        networkMonitoringUtil?.registerNetworkCallbackEvents()
    }

    /**
     * Creates core component lazily
     */
    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(this)
    }

    companion object {
        /**
         * Core component reusable factory
         * @param context [Context] input needed to create the core component factory
         * @return [CoreComponent] component used to inject within the app
         */
        @JvmStatic
        fun coreComponent(context: Context) =
            (context.applicationContext as MainApplication).coreComponent
    }
}
// Used to inject within activities
fun Activity.coreComponent() = MainApplication.coreComponent(this)
// Used to inject within fragments
fun Fragment.coreComponent() = MainApplication.coreComponent(requireContext())
