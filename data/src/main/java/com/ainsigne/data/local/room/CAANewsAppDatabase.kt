package com.ainsigne.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ainsigne.data.local.room.converter.Converters
import com.ainsigne.data.local.room.headline.HeadlineDao
import com.ainsigne.domain.entities.ArticleDomainEntities

/**
 * App database with dao and entities definition
 * [ArticleDomainEntities.Article] article domain data
 */
@Database(
    entities = [
        ArticleDomainEntities.Article::class
    ],
    version = 3, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CAANewsAppDatabase : RoomDatabase() {

    /**
     * Headlines dao definition
     */
    abstract fun headlineDao(): HeadlineDao

    companion object {
        /**
         * Database name
         */
        const val DATABASE_NAME = "caanewsapp_db"
    }
}
