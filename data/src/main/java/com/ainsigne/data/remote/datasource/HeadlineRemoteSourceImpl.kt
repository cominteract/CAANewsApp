package com.ainsigne.data.remote.datasource

import com.ainsigne.common.utils.network.NetworkStatus
import com.ainsigne.data.remote.api.HeadlineService
import com.ainsigne.data.utils.safeApiCall
import com.ainsigne.domain.entities.ArticleDomainEntities

class HeadlineRemoteSourceImpl(val service: HeadlineService) : HeadlineRemoteSource {

    override suspend fun retrieveTopHeadlines(countryCode: String): NetworkStatus<ArticleDomainEntities.ArticlesResponse> {
        return safeApiCall {
            service.retrieveTopHeadlines(countryCode)
        }
    }
}