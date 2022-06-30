package com.ainsigne.domain.repository

import com.ainsigne.common.utils.network.NetworkStatus
import com.ainsigne.domain.entities.ArticleDomainEntities
import kotlinx.coroutines.flow.Flow

/**
 * Headline repository for local and remote resource related data
 **/
interface HeadlineRepository {
    /**
     * Returns a [Boolean] upon force refresh of top headlines
     * @return [Boolean] when top headlines are refreshed
     */
    suspend fun forceRefreshHeadlines(): Flow<Boolean>

    /**
     * Returns a [NetworkStatus.Error] when headlines does not exist
     * and [NetworkStatus.Success] with [List] [ArticleDomainEntities.Article] when successful
     * @return [List] [ArticleDomainEntities.Article] when headlines exist
     */
    suspend fun getHeadlines(): Flow<NetworkStatus<List<ArticleDomainEntities.Article>>>
}
