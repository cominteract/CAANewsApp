package com.ainsigne.data.local.room.headline

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ainsigne.domain.entities.ArticleDomainEntities

/**
 * Headline dao for top headlines local related crud operations
 */
@Dao
interface HeadlineDao {

    /**
     * Save ArticleDomainEntities.Article to local database and returns positive [LongArray] when successful
     * @param articles [List] [ArticleDomainEntities.Article] input data to be saved in local db
     * @return positive [LongArray] when successful
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleDomainEntities.Article>): LongArray

    /**
     * Retrieve articles data from local db
     * @return [List] [ArticleDomainEntities.Article] saved in local db
     */
    @Query("SELECT * FROM news_article_list")
    suspend fun queryArticles(): List<ArticleDomainEntities.Article>

    /**
     * Delete articles data from local db
     * @return [Int] deleted article list indication
     */
    @Query("DELETE FROM news_article_list")
    suspend fun deleteArticles(): Int

}