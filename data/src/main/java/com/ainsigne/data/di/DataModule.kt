package com.ainsigne.data.di

import com.ainsigne.data.local.datasource.headline.HeadlineLocalSource
import com.ainsigne.data.local.datasource.headline.HeadlineLocalSourceImpl
import com.ainsigne.data.local.datasource.time.TimeLocalSource
import com.ainsigne.data.local.datasource.time.TimeLocalSourceImpl
import com.ainsigne.data.local.datastore.CAANewsAppDataStore
import com.ainsigne.data.local.room.headline.HeadlineDao
import com.ainsigne.data.remote.api.HeadlineService
import com.ainsigne.data.remote.datasource.HeadlineRemoteSource
import com.ainsigne.data.remote.datasource.HeadlineRemoteSourceImpl
import com.ainsigne.data.repository.app.AppRepositoryImpl
import com.ainsigne.data.repository.headline.HeadlineRepositoryImpl
import com.ainsigne.domain.repository.AppRepository
import com.ainsigne.domain.repository.HeadlineRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@Module(includes = [NetworkModule::class])
class DataModule {

    /**
     * Provides headlines remote data source
     * @param apiService [HeadlineService] input service for making server requests to headlines endpoint
     * @return [HeadlineRemoteSource] provided for fetching resources from server requests using [HeadlineService]
     */
    @Provides
    fun provideHeadlineRemoteDataSource(apiService: HeadlineService): HeadlineRemoteSource {
        return HeadlineRemoteSourceImpl(apiService)
    }

    /**
     * Provides Time local data source
     * @return [TimeLocalSource] provided for cache related
     */
    @Provides
    fun provideTimeLocalDataSource(): TimeLocalSource {
        return TimeLocalSourceImpl()
    }

    /**
     * Provides Headlines local data source
     * @param headlineDao [HeadlineDao] input dao for making headline crud requests locally
     * @return [HeadlineLocalSource] provided for crud requests using [HeadlineDao]
     */
    @Provides
    fun provideHeadlineLocalDataSource(headlineDao: HeadlineDao): HeadlineLocalSource {
        return HeadlineLocalSourceImpl(headlineDao)
    }

    /**
     * Provides Headline repository with remote source provided for network requests and local source for caching purposes
     * @param dataStore [CAANewsAppDataStore] input data store for caching purposes
     * @param remoteDataSource [HeadlineRemoteSource] input remote source for making network requests
     * @param localDataSource [HeadlineLocalSource] input local source for caching purposes
     * @param timeLocalSource [TimeLocalSource] provided for cache related
     * @return [HeadlineRepository] provided for fetching headlines related resources from local and remote
     */
    @Singleton
    @Provides
    fun provideHeadlinesRepository(
        remoteDataSource: HeadlineRemoteSource,
        localDataSource: HeadlineLocalSource,
        timeLocalSource: TimeLocalSource,
        dataStore: CAANewsAppDataStore
    ): HeadlineRepository {
        return HeadlineRepositoryImpl(
            localDataSource,
            remoteDataSource,
            dataStore,
            timeLocalSource,
        )
    }

    /**
     * Provides App repository with datastore provided for caching purposes
     * @param dataStore [CAANewsAppDataStore] input data store for caching purposes
     * @return [AppRepository] provided for fetching app related resources from local data store
     */
    @Singleton
    @Provides
    fun provideAppRepository(
        dataStore: CAANewsAppDataStore
    ): AppRepository {
        return AppRepositoryImpl(
            dataStore = dataStore
        )
    }
}
