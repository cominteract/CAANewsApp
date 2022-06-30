package com.ainsigne.data.local.datasource.headline

import com.ainsigne.domain.entities.ArticleDomainEntities

interface HeadlineLocalSource {
    /**
     * Retrieve articles data from local db
     * @return [List] [ArticleDomainEntities.Article] articles data cached in db
     */
    suspend fun getArticles(): List<ArticleDomainEntities.Article>

    /**
     * Save articles data for caching
     * @param articles [List] [ArticleDomainEntities.Article] articles to cache
     */
    suspend fun insertArticles(articles: List<ArticleDomainEntities.Article>)
    /**
     * Delete articles data for reset caching
     */
    suspend fun deleteArticles()
}
