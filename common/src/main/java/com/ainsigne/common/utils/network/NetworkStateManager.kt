package com.ainsigne.common.utils.network

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * @see https://github.com/ranitraj/ActiveNetworkStateObserver
 * Singleton Manager class to maintain current Network-Status throughout application
 */
class NetworkStateManager private constructor() {

    private var previousConnected = true

    private var networkConnected: NetworkConnected? = null
    /**
     * Updates the active network status live-data
     */
    fun setNetworkConnectivityStatus(connectivityStatus: Boolean) {
        activeNetworkStatusMLD.postValue(connectivityStatus)
    }

    /**
     * Setup the observer and network connected interface
     * @param networkConnectedSource [NetworkConnected] callback for identifying internet connection
     * to be implemented app wide
     * @param lifecycleOwner [LifecycleOwner] current owner of the observer
     */
    fun setupNetworkConnected(
        networkConnectedSource: NetworkConnected,
        lifecycleOwner: LifecycleOwner,
        isShowingNetworkIssue: Boolean = true
    ) {
        this.networkConnected = networkConnectedSource
        networkConnectivityStatus.observe(lifecycleOwner) { isConnected ->
            when {
                isShowingNetworkIssue -> {
                    when {
                        isConnected -> {
                            if (!previousConnected) {
                                networkConnected?.onInternetFound()
                            }
                        }
                        else -> {
                            networkConnected?.onInternetLost()
                        }
                    }
                    previousConnected = isConnected
                }
            }
        }
    }

    /**
     * Returns the current network status
     */
    val networkConnectivityStatus: LiveData<Boolean>
        get() {
            return activeNetworkStatusMLD
        }

    companion object {
        private var INSTANCE: NetworkStateManager? = null
        private val activeNetworkStatusMLD = MutableLiveData<Boolean>()

        @get:Synchronized
        val instance: NetworkStateManager?
            get() {
                if (INSTANCE == null) {
                    INSTANCE = NetworkStateManager()
                }
                return INSTANCE
            }
    }
}
