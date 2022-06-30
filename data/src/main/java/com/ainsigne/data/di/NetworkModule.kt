package com.ainsigne.data.di

import android.content.Context
import com.ainsigne.data.local.datastore.CAANewsAppDataStore
import com.ainsigne.data.remote.api.HeadlineService
import com.ainsigne.data.utils.json
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Network module for network related dependencies
 */
@DelicateCoroutinesApi
@Module
class NetworkModule {

    // Request time out can be updated for long fetch requests
    private val requestTimeOut = 10

    /**
     * Provides data store
     * @param context [Context] input context for creating [CAANewsAppDataStore] instance
     * @return [CAANewsAppDataStore] instance with [Context] to create it
     */
    @Singleton
    @Provides
    fun provideDataStoreModule(context: Context) =
        CAANewsAppDataStore(context.applicationContext)

    /**
     * Provides logging interceptor for network requests logs
     * @return [HttpLoggingInterceptor] instance created with BODY level of logging
     */
    @Provides
    @Singleton
    internal fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    /**
     * Provides Okhttp client with [HttpLoggingInterceptor] and timeout setup
     * @param httpLoggingInterceptor [HttpLoggingInterceptor] input with BODY level of logging
     */
    @Provides
    @Singleton
    internal fun provideOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {

        val token = "8250410cccae463c9629f914d101a063"

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor { chain ->
                val request =
                    chain.request()
                val url = request.url
                val newRequest = url.newBuilder().addQueryParameter(
                    "apiKey", token
                ).build()
                val response = chain.proceed(request.newBuilder().url(newRequest).build())
                response
            }
            .connectTimeout(requestTimeOut.toLong(), TimeUnit.SECONDS)
            .readTimeout(requestTimeOut.toLong(), TimeUnit.SECONDS)
            .build()

        return okHttpClient
    }

    /**
     * Provides retrofit instance
     * @param okHttpClient [OkHttpClient] input client for retrofit
     * @return [Retrofit] instance with [OkHttpClient] as client and [GsonConverterFactory] for conversion
     */
    @ExperimentalSerializationApi
    @Named("wb_base")
    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(com.ainsigne.data.BuildConfig.API_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(json())
            )
            .client(okHttpClient)
            .build()
    }

    /**
     * Provides headline service
     * @param retrofit [Retrofit] instance for creation of service
     * @return [HeadlineService] instance created for headlines network requests
     */
    @Provides
    @Singleton
    fun provideHeadlineService(@Named("wb_base") retrofit: Retrofit): HeadlineService {
        return retrofit.create(HeadlineService::class.java)
    }
}
