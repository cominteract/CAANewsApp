package com.ainsigne.data.utils

import com.ainsigne.common.utils.CONNECT_EXCEPTION
import com.ainsigne.common.utils.GENERAL_EXCEPTION
import com.ainsigne.common.utils.SOCKET_TIME_OUT_EXCEPTION
import com.ainsigne.common.utils.UNKNOWN_HOST_EXCEPTION
import com.ainsigne.common.utils.UNKNOWN_NETWORK_EXCEPTION
import com.ainsigne.common.utils.network.NetworkStatus
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @return [NetworkStatus] allows api calls to return network status errors when
 * exception is caught and network status success when response is successful
 */
suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): NetworkStatus<T> {

    try {
        val response = call.invoke()
        val responseBody = response.body()
        val errorBody = response.errorBody()
        if (response.isSuccessful) {
            if (responseBody != null) {
                return NetworkStatus.Success(responseBody)
            }
        }
        val errorString = errorBody?.string()
        return NetworkStatus.Error(errorString, code = response.code())
    } catch (e: Exception) {
        Timber.d(" Error In Exception ${e.message} ")
        return when (e) {
            is ConnectException -> {
                NetworkStatus.Error(CONNECT_EXCEPTION)
            }
            is UnknownHostException -> {
                NetworkStatus.Error(UNKNOWN_HOST_EXCEPTION)
            }
            is SocketTimeoutException -> {
                NetworkStatus.Error(SOCKET_TIME_OUT_EXCEPTION)
            }
            is HttpException -> {
                NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION)
            }
            else -> {
                NetworkStatus.Error(GENERAL_EXCEPTION)
            }
        }
    }
}

