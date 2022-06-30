package com.ainsigne.common.utils.network

/**
 * Network statuses to identify states for repo related processes
 * @param data [T] data type to return when successful
 * @param errorMessage [String] error message to return when failed
 */
sealed class NetworkStatus<T>(
    val data: T? = null,
    val errorMessage: String? = null,
    val code: Int? = null
) {
    /**
     * Network state when fetch or process is successful
     * @param data [T] data type to return when successful
     */
    class Success<T>(data: T?) : NetworkStatus<T>(data)

    /**
     * Network state when fetch or process is successful
     * @param data [T] data type to return when successful
     */
    class LocalSuccess<T>(data: T?) : NetworkStatus<T>(data)
    /**
     * Network state when failed
     * @param errorMessage [String] error message to return when failed
     */
    class Error<T>(errorMessage: String?, data: T? = null, code: Int? = 350) : NetworkStatus<T>(data, errorMessage, code)
    /**
     * Network state when loading
     * @param data [T] data type to return when successful
     */
    class Loading<T>(data: T? = null) : NetworkStatus<T>(data)
}
