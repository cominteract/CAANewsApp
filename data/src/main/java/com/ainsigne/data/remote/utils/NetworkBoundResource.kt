package com.ainsigne.data.remote.utils


import com.ainsigne.common.utils.CONNECT_EXCEPTION
import com.ainsigne.common.utils.FAILED_CONNECT_EXCEPTION
import com.ainsigne.common.utils.GENERAL_EXCEPTION
import com.ainsigne.common.utils.extension.EMPTY
import com.ainsigne.common.utils.network.NetworkStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

/**
 * network bound resource for repository related processes
 * @param query [Flow] [Pair] [ResultType] [Int] emits data
 * once saved. If already saved before fetching. this will emit
 * @param fetch [RequestType] emits data fetched from remote source
 * @param saveFetchResult [RequestType] after fetched in network
 * save fetch result allows data to be saved locally
 * @param clearData allows data to be cleared (Optional)
 * @param onFetchFailed allows to emit fetch failed
 * @param shouldClear [Boolean] toggle whether clearData will be
 * process to remove current cache for [ResultType]
 * @param shouldFetch [Pair] [Boolean] [String] toggle whether network request
 * to fetch need to be processed instead of relying on local data only
 */
@ExperimentalCoroutinesApi
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<Pair<ResultType, Int>>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline clearData: suspend () -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit },
    crossinline shouldFetch: (ResultType) -> Pair<Boolean, String> = { Pair(true, EMPTY) },
    crossinline shouldClear: (RequestType, ResultType) -> Boolean = { _: RequestType, _: ResultType -> false }
) = flow<NetworkStatus<ResultType>> {
    val dbData = query()

    emit(NetworkStatus.Loading(null))
    if (shouldFetch(dbData.first().first).first) {
        try {
            if (dbData.first().second > 0) {
                emitAll(
                    dbData.map { result ->
                        NetworkStatus.LocalSuccess(result.first)
                    }
                )
            }
            val fetchData = fetch()
            if (fetchData is NetworkStatus.Error<*> && dbData.first().second < 1) {
                Timber.d(" Error In Fetch ${fetchData.errorMessage} ${fetchData.code}")
                val queryMap = dbData.map { NetworkStatus.Error(fetchData.errorMessage, it.first, fetchData.code) }
                emitAll(queryMap)
            } else {
                if (fetchData !is NetworkStatus.Error<*>) {
                    Timber.d(" Getting Data From Fetch ")
                    if (shouldClear(fetchData, dbData.first().first)) {
                        clearData()
                    }
                    saveFetchResult(fetchData)
                    emitAll(
                        dbData.map { result ->
                            NetworkStatus.Success(result.first)
                        }
                    )
                } else {
                    Timber.d(
                        " Getting Data From Cache Since Error Encountered " +
                            "${fetchData.errorMessage} " +
                            "${shouldFetch(dbData.first().first).first} " +
                            "${shouldFetch(dbData.first().first).second}"
                    )
                }
            }
        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            Timber.d(" Error In Network Exception ${throwable.message} ")
            val message = throwable.message?.let {
                it
            } ?: run {
                EMPTY
            }
            if (message.contains(FAILED_CONNECT_EXCEPTION)) {
                emitAll(dbData.map { NetworkStatus.Error(CONNECT_EXCEPTION) })
            } else {
                emitAll(dbData.map { NetworkStatus.Error(GENERAL_EXCEPTION) })
            }
        }
    } else {
        Timber.d(
            " Getting Data From Cache " +
                "${shouldFetch(dbData.first().first).first} " +
                "${shouldFetch(dbData.first().first).second}"
        )
        emitAll(dbData.map { result -> NetworkStatus.Success(result.first) })
    }
}
