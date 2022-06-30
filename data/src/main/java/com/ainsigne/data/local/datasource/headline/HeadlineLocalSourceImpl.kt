package com.ainsigne.data.local.datasource.headline

import com.ainsigne.data.local.room.headline.HeadlineDao
import com.ainsigne.domain.entities.ArticleDomainEntities

class HeadlineLocalSourceImpl(val headlineDao: HeadlineDao) : HeadlineLocalSource {
    /**
     * Retrieve articles data from local db
     * @return [List] [ArticleDomainEntities.Article] articles data cached in db
     */
    override suspend fun getArticles(): List<ArticleDomainEntities.Article> {
        return headlineDao.queryArticles()
    }

    /**
     * Save articles data for caching
     * @param articles [List] [ArticleDomainEntities.Article] articles to cache
     */
    override suspend fun insertArticles(articles: List<ArticleDomainEntities.Article>) {
        headlineDao.insertArticles(articles)
    }
    /**
     * Delete articles data for reset caching
     */
    override suspend fun deleteArticles() {
        headlineDao.deleteArticles()
    }
}
