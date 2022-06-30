package com.ainsigne.data.remote.datasource

import com.ainsigne.common.utils.network.NetworkStatus
import com.ainsigne.domain.entities.ArticleDomainEntities

interface HeadlineRemoteSource {

    /**
     * Returns a [NetworkStatus.Error] when top headlines response does not exist
     * and [NetworkStatus.Success] with [ArticleDomainEntities.ArticlesResponse] when successful
     * @return [ArticleDomainEntities.ArticlesResponse] when top headlines exist
     */
    suspend fun retrieveTopHeadlines(countryCode: String): NetworkStatus<ArticleDomainEntities.ArticlesResponse>
}
