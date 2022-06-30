package com.ainsigne.data.repository.headline

import com.ainsigne.common.utils.extension.EMPTY
import com.ainsigne.common.utils.network.NetworkStatus
import com.ainsigne.data.local.datasource.headline.HeadlineLocalSource
import com.ainsigne.data.local.datastore.CAANewsAppDataStore
import com.ainsigne.data.local.datastore.DataStoreKeys
import com.ainsigne.data.remote.datasource.HeadlineRemoteSource
import com.ainsigne.data.remote.utils.networkBoundResource
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
    private val dataStore: CAANewsAppDataStore
) : HeadlineRepository {


    override suspend fun forceRefreshHeadlines(): Flow<Boolean> {
        return flowOf(true)
    }

    override suspend fun getHeadlines(): Flow<NetworkStatus<List<ArticleDomainEntities.Article>>> {
        return networkBoundResource(
            fetch = {
                val countryCode = dataStore.getValue(DataStoreKeys.KEY_PROFILE_COUNTRY_CODE).orEmpty()
                remoteSource.retrieveTopHeadlines(countryCode)
            },
            saveFetchResult = { articleResponse ->
                val articles = articleResponse.data?.articles
                localSource.insertArticles(articles.orEmpty())
            },
            shouldFetch = {
                Pair(true, EMPTY)
            },
            shouldClear = { _, _ ->
                true
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


}