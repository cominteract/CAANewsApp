package com.ainsigne.common.utils.network

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest
import com.ainsigne.common.utils.PING_SERVER_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.net.Socket

/**
 * Network observer utility
 * @see https://github.com/ranitraj/ActiveNetworkStateObserver
 */
class NetworkMonitoringUtil(
    private val mNetworkRequest: NetworkRequest,
    private val mConnectivityManager: ConnectivityManager,
) :
    NetworkCallback() {
    private val mNetworkStateManager: NetworkStateManager? = NetworkStateManager.instance

    /**
     * onAvailable NetworkCallback for identifying if network is available
     * @param network [Network] current network being made available
     */
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        detectInternet()
    }

    /**
     * Detects of current network is actually connected to internet
     */
    private fun detectInternet() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val socket = Socket(PING_SERVER_URL, 80)
                socket.getOutputStream()
                mNetworkStateManager?.setNetworkConnectivityStatus(true)
            } catch (e: IOException) {
                mNetworkStateManager?.setNetworkConnectivityStatus(false)
            }
        }
    }

    /**
     * onLost NetworkCallback for identifying if network is lost
     * @param network [Network] current network being lost
     */
    override fun onLost(network: Network) {
        super.onLost(network)
        Timber.d("onLost() called: with: Lost network connection")
        CoroutineScope(Dispatchers.IO).launch {
            mNetworkStateManager?.setNetworkConnectivityStatus(false)
        }
    }

    /**
     * Registers the Network-Request callback
     * (Note: Register only once to prevent duplicate callbacks)
     */
    fun registerNetworkCallbackEvents() {
        mConnectivityManager.registerNetworkCallback(mNetworkRequest, this)
    }

    /**
     * Check current Network state
     */
    fun checkNetworkState() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val networkInfo = mConnectivityManager.activeNetworkInfo
                mNetworkStateManager?.setNetworkConnectivityStatus(
                    networkInfo != null &&
                        networkInfo.isConnected
                )
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }
}
