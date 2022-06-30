package com.ainsigne.data.repository.headline

import com.ainsigne.common.utils.US
import com.ainsigne.common.utils.extension.compareExceedingMinutes
import com.ainsigne.common.utils.network.NetworkStatus
import com.ainsigne.data.local.datasource.headline.HeadlineLocalSource
import com.ainsigne.data.local.datasource.time.TimeSource
import com.ainsigne.data.local.datastore.CAANewsAppDataStore
import com.ainsigne.data.local.datastore.DataStoreKeys
import com.ainsigne.data.remote.api.HeadlineService
import com.ainsigne.data.remote.datasource.HeadlineRemoteSource
import com.ainsigne.data.remote.datasource.HeadlineRemoteSourceImpl
import com.ainsigne.data.repository.util.FakeTimeLocalSource
import com.ainsigne.data.utils.getHeadlineRefresh
import com.ainsigne.domain.entities.ArticleDomainEntities
import com.ainsigne.domain.repository.HeadlineRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class HeadlineRepositoryImplTest {

    private lateinit var dataStore: CAANewsAppDataStore
    private lateinit var service: HeadlineService
    private lateinit var timeLocalSource: FakeTimeLocalSource
    private lateinit var remoteSource: HeadlineRemoteSource
    private lateinit var localSource: HeadlineLocalSource
    private lateinit var repository: HeadlineRepository

    @Before
    fun setup() {
        dataStore = Mockito.mock(CAANewsAppDataStore::class.java)
        service = Mockito.mock(HeadlineService::class.java)
        timeLocalSource = FakeTimeLocalSource()
        remoteSource = HeadlineRemoteSourceImpl(service)
        localSource = Mockito.mock(HeadlineLocalSource::class.java)
        repository = HeadlineRepositoryImpl(
            dataStore = dataStore,
            localSource = localSource,
            remoteSource = remoteSource,
            timeLocalSource = timeLocalSource
        )
    }

    @Test
    fun forceRefreshHeadlines() {
        // Given
        runBlocking {
            // When
            repository.forceRefreshHeadlines()

            // Then
            Mockito.verify(dataStore, Mockito.atLeastOnce()).write(
                DataStoreKeys.KEY_REFRESH_HEADLINE,
                timeLocalSource.getCurrentTime(TimeSource.FORCE_REFRESH)
            )
            // Asserts that force refresh compared to current time is always beyond target cache expiry minute
            assert(
                timeLocalSource.getCurrentTime(TimeSource.FORCE_REFRESH)
                    .compareExceedingMinutes(timeLocalSource.getCurrentTime(TimeSource.CURRENT))
            )
        }
    }

    @Test
    fun getHeadlines() {
// Given
        runBlocking {
            // When
            val expectedArticles = listOf(
                ArticleDomainEntities.Article(
                    id = 1,
                    title = "Sea Dogs defeat Bulldogs to capture 2nd Memorial Cup in franchise's 17-year history - CBC Sports",
                    description = "Considered a long" +
                        " shot at the beginning of the Canadian Hockey League championship due to a first-round loss in the Quebec " +
                        "Major Junior Hockey League playoffs, the " +
                        "host Saint John Sea Dogs downed the Hamilton" +
                        " Bulldogs 6-3 in Wednesday's championship game",
                    urlToImage = "https://i.cbc.ca/1.6506512." +
                        "1656553746!/fileImage/httpImage/image.jpg_gen/derivatives/16x9_620/memorial-cup-2022-sea-dogs-saint-john.jpg",
                    publishedAt = "2022-06-30T01:54:00Z",
                    content = "The Saint John Sea Dogs turned a " +
                        "devastating playoff loss into a" +
                        " Memorial Cup championship, thanks to" +
                        " renewed focus, 40 days of sweat and a university coach who" +
                        " pushed all the right buttons.\\r\\nConside… [+5750 chars]\"}," +
                        "{\"source\":{\"id\":null,\"name\":\"Ctvnews.ca\"},\"author\":null," +
                        "\"title\":\"Flights, destinations 'likely' to be cancelled at Montreal " +
                        "airport - CTV News Montreal\",\"description\":\"Canadians " +
                        "flying out of the Montreal airport might be in for a bit of a shock " +
                        "this summer as the head of the Trudeau airport" +
                        " says airlines will \\\"likely\\\" be" +
                        " asked to cancel some flights — or even destinations altogether.",
                    source = ArticleDomainEntities.ArticleSource(
                        id = "cbc-news",
                        name = "CBC News"
                    )

                ),
                ArticleDomainEntities.Article(
                    id = 2,
                    title = "Sea Dogs defeat Bulldogs to capture 2nd Memorial Cup in franchise's 17-year history" +
                        " - CBC Sports",
                    description = "Considered a long shot at the beginning of the Canadian Hockey" +
                        " League championship due to a first-round" +
                        " loss in the Quebec Major Junior Hockey League playoffs, the host " +
                        "aint John Sea Dogs downed the Hamilton Bulldogs 6-3 in Wednesday's " +
                        "championship game",
                    urlToImage = "https://i.cbc.ca/1.6506512." +
                        "1656553746!/fileImage/httpImage/image.jpg_gen/derivatives/16x9" +
                        "_620/memorial-cup-2022-sea-dogs-saint-john.jpg",
                    publishedAt = "2022-06-30T01:54:00Z",
                    content = "The Saint John Sea Dogs turned a " +
                        "devastating playoff loss into a Memorial Cup championship, " +
                        "thanks to renewed focus," +
                        " 40 days of sweat and a university coach who pushed all the " +
                        "right buttons.\\r\\nConside… [+5750 chars]\"},{\"source\":{\"id\":null" +
                        ",\"name\":\"Ctvnews.ca\"},\"author\":null,\"title\":\"Flights, destinations " +
                        "'likely' to be cancelled at Montreal airport - " +
                        "CTV News Montreal\",\"description\":\"Canadians flying out of the " +
                        "Montreal airport might be in for a bit of a shock this summer as the " +
                        "head of the Trudeau airport says airlines will \\\"likely\\\" be asked" +
                        " to cancel some flights — or even destinations altogether.",
                    source = ArticleDomainEntities.ArticleSource(
                        id = "cbc-news",
                        name = "CBC News"
                    )
                )
            )
            /**
             * Allows refresh/fetch to be done due to exceeding the amount of minutes for cache expiry
             */
            Mockito.`when`(
                dataStore.getHeadlineRefresh()
            ).thenReturn(
                timeLocalSource.getCurrentTime(TimeSource.BEFORE_CURRENT)
            )

            /**
             * Allows cache to be done due to not exceeding the amount of minutes for cache expiry
             */
//            Mockito.`when`(
//                dataStore.getHeadlineRefresh()
//            ).thenReturn(
//                timeLocalSource.getCurrentTime(TimeSource.AFTER_SAVE)
//            )

            Mockito.`when`(
                localSource.getArticles()
            ).thenReturn(
                expectedArticles
            )

            Mockito.`when`(
                service.retrieveTopHeadlines(US)
            ).thenReturn(
                Response.success(
                    ArticleDomainEntities.ArticlesResponse(
                        status = "ok",
                        articles = expectedArticles
                    )
                )
            )
            val headlines = repository.getHeadlines()
            // Then
            assert(headlines.last() is NetworkStatus.Success)
            assert(headlines.last().data == expectedArticles)
            Mockito.verify(localSource, Mockito.atLeastOnce()).insertArticles(
                expectedArticles
            )

            Mockito.verify(dataStore, Mockito.atLeastOnce()).write(
                DataStoreKeys.KEY_REFRESH_HEADLINE,
                timeLocalSource.getCurrentTime(TimeSource.AFTER_SAVE)
            )
        }
    }
}
