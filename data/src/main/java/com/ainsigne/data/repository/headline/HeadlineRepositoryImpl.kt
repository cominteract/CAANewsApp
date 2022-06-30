package com.ainsigne.data.repository.headline

import com.ainsigne.common.utils.extension.compareExceedingMinutes
import com.ainsigne.common.utils.network.NetworkStatus
import com.ainsigne.data.local.datasource.headline.HeadlineLocalSource
import com.ainsigne.data.local.datasource.time.TimeLocalSource
import com.ainsigne.data.local.datasource.time.TimeSource
import com.ainsigne.data.local.datastore.CAANewsAppDataStore
import com.ainsigne.data.local.datastore.DataStoreKeys
import com.ainsigne.data.remote.datasource.HeadlineRemoteSource
import com.ainsigne.data.utils.getCode
import com.ainsigne.data.utils.getHeadlineRefresh
import com.ainsigne.data.utils.networkBoundResource
import com.ainsigne.domain.entities.ArticleDomainEntities
import com.ainsigne.domain.repository.HeadlineRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class HeadlineRepositoryImpl(
    private val localSource: HeadlineLocalSource,
    private val remoteSource: HeadlineRemoteSource,
    private val dataStore: CAANewsAppDataStore,
    private val timeLocalSource: TimeLocalSource
) : HeadlineRepository {

    override suspend fun forceRefreshHeadlines(): Flow<Boolean> {
        dataStore.write(DataStoreKeys.KEY_REFRESH_HEADLINE, timeLocalSource.getCurrentTime(TimeSource.FORCE_REFRESH))
        return flowOf(minutesExceeded)
    }

    override suspend fun getHeadlines(): Flow<NetworkStatus<List<ArticleDomainEntities.Article>>> {
        return networkBoundResource(
            fetch = {
                val countryCode = dataStore.getCode()
                remoteSource.retrieveTopHeadlines(countryCode)
            },
            saveFetchResult = { articleResponse ->
                val articles = articleResponse.data?.articles
                localSource.insertArticles(articles.orEmpty())
            },
            shouldFetch = {
                minutesExceededPair
            },
            shouldClear = { _, _ ->
                minutesExceeded
            },
            query = {
                flow {
                    val articles = localSource.getArticles()
                    emit(Pair(articles, articles.size))
                }
            },
            clearData = {
                localSource.deleteArticles()
            }
        )
    }

    private val minutesExceededPair = runBlocking {
        Pair(
            dataStore.getHeadlineRefresh()?.compareExceedingMinutes(timeLocalSource.getCurrentTime(TimeSource.FETCH_OR_CLEAR)) ?: true,
            "${dataStore.getHeadlineRefresh()} | ${timeLocalSource.getCurrentTime(TimeSource.FETCH_OR_CLEAR)}"
        )
    }

    private val minutesExceeded = runBlocking {
        dataStore.getHeadlineRefresh()?.compareExceedingMinutes(timeLocalSource.getCurrentTime(TimeSource.FETCH_OR_CLEAR)) ?: true
    }
}
