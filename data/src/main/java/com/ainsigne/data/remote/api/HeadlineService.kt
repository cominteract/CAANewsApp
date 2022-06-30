package com.ainsigne.data.remote.api

import com.ainsigne.domain.entities.ArticleDomainEntities
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Service for headline network requests
interface HeadlineService {
    // Fetch top headlines from api
    @GET("top-headlines")
    suspend fun retrieveTopHeadlines(@Query("country") country: String): Response<ArticleDomainEntities.ArticlesResponse>
}
