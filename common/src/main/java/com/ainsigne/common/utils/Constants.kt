package com.ainsigne.common.utils

import timber.log.Timber

/**
 * Target minutes
 */
const val TARGET_MINUTES = 2

const val PING_SERVER_URL = "https://google.com"

const val SOCKET_TIME_OUT_EXCEPTION = "Request timed out while trying to connect. Please ensure you have a strong signal and try again."

// Unknown network exception message
const val FAILED_CONNECT_EXCEPTION = "Failed to connect to"

const val UNKNOWN_NETWORK_EXCEPTION = "An unexpected error has occurred. Please check your network connection and try again."
// Server connection exception message
const val CONNECT_EXCEPTION = "Could not connect to the server. Please check your internet connection and try again."
// Unknown host exception message
const val UNKNOWN_HOST_EXCEPTION = "Couldn't connect to the server at the moment. Please try again in a few minutes."
// General exception message
const val GENERAL_EXCEPTION = "General Error, Please Contact Customer Support"

// Max retry refresh limit
const val MAX_RETRY_LIMIT = 3

// Default delay
const val DEFAULT_DELAY: Long = 2000

/**
 * US country code
 */
const val US = "us"

/**
 * Canada country code
 */
const val CANADA = "ca"

fun placeholder(): () -> Unit {
    return { Timber.d(" Placeholder ") }
}
