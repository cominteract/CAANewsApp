package com.ainsigne.core.di.modules

import android.content.Context
import androidx.room.Room
import com.ainsigne.data.local.room.CAANewsAppDatabase
import com.ainsigne.data.local.room.CAANewsAppDatabase.Companion.DATABASE_NAME
import com.ainsigne.data.local.room.headline.HeadlineDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    /**
     * Provides app database
     * @param context [Context] context to create app database
     * @return [CAANewsAppDatabase] database with fallbacktodestructivemigration
     * for clearing database when version is updated
     **/
    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): CAANewsAppDatabase = Room.databaseBuilder(
        context, CAANewsAppDatabase::class.java, DATABASE_NAME
    )
        .fallbackToDestructiveMigration() // allows database to be cleared after upgrading version
        .build()

    /**
     * Provides headline dao
     * @param db [CAANewsAppDatabase] input app database
     * @return [HeadlineDao] dao for headline related crud
     */
    @Singleton
    @Provides
    fun provideHeadlineDao(db: CAANewsAppDatabase):
        HeadlineDao = db.headlineDao()
}
