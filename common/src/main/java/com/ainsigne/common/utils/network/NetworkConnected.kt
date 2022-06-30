package com.ainsigne.common.utils.network

/**
 * App wide interface for fragments that identifies network connection state
 */
interface NetworkConnected {
    /**
     * onInternetLost when internet connection is lost
     */
    fun onInternetLost()
    /**
     * onInternetFound when internet connection is found
     */
    fun onInternetFound()

    /**
     * showNetworkIssue toggle to indicate network issue
     */
    fun showNetworkIssue(): Boolean
}
