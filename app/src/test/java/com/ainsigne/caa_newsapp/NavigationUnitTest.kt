package com.ainsigne.caa_newsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ainsigne.caa_newsapp.navigation.NavigationCollection
import com.ainsigne.caa_newsapp.navigation.NavigationCoordinator
import com.ainsigne.caa_newsapp.navigation.NavigationCoordinatorViewModel
import com.ainsigne.common.Navigation
import com.ainsigne.common.navigation.HomeNavigation
import com.ainsigne.common.navigation.SplashNavigation
import com.ainsigne.domain.navigation.ArticleDetails
import com.ainsigne.home.ui.HomeFragmentDirections
import com.ainsigne.splash.ui.SplashFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NavigationUnitTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when navigated from the view model navigation value must be what is needed to navigate `() {
        // Given
        val navigationCoordinatorViewModel = NavigationCoordinatorViewModel()
        // When
        navigationCoordinatorViewModel.splashToHome()
        // Then
        assert(
            navigationCoordinatorViewModel.navigation.value
            is NavigationCoordinator.SplashToHome
        )
        assert(
            NavigationCoordinator.SplashToHome.navDirections ==
                SplashFragmentDirections.splashToHome()
        )

        val expectedArticle = ArticleDetails(
            title = "Sea Dogs defeat Bulldogs to capture 2nd Memorial Cup in franchise's 17-year history - CBC Sports",
            description = "Considered a long shot at the beginning of the Canadian Hockey League championship due to a first-round loss in the Quebec Major Junior Hockey League playoffs, the host Saint John Sea Dogs downed the Hamilton Bulldogs 6-3 in Wednesday's championship game",
            urlToImage = "https://i.cbc.ca/1.6506512.1656553746!/fileImage/httpImage/image.jpg_gen/derivatives/16x9_620/memorial-cup-2022-sea-dogs-saint-john.jpg",
            publishedAt = "2022-06-30T01:54:00Z",
            content = "The Saint John Sea Dogs turned a devastating playoff loss into a Memorial Cup championship, thanks to renewed focus, 40 days of sweat and a university coach who pushed all the right buttons.\\r\\nConside… [+5750 chars]\"},{\"source\":{\"id\":null,\"name\":\"Ctvnews.ca\"},\"author\":null,\"title\":\"Flights, destinations 'likely' to be cancelled at Montreal airport - CTV News Montreal\",\"description\":\"Canadians flying out of the Montreal airport might be in for a bit of a shock this summer as the head of the Trudeau airport says airlines will \\\"likely\\\" be asked to cancel some flights — or even destinations altogether.",
        )
        // When
        navigationCoordinatorViewModel.homeToDetails(expectedArticle)
        // Then
        assert(
            navigationCoordinatorViewModel.navigation.value
            is NavigationCoordinator.HomeToDetails
        )
        assert(
            NavigationCoordinator.HomeToDetails(expectedArticle).navDirections ==
                HomeFragmentDirections.homeToDetails(articleData = expectedArticle)
        )
    }

    @Test
    fun `when navigated with navigation enum must ensure that correct navigation call is made with correct data`() {
        // Given
        val mainActivity = MainActivity()
        val navCollection = mock(NavigationCollection::class.java)
        val navViewModel = mock(NavigationCoordinatorViewModel::class.java)
        // When
        mainActivity.setupNavigationViewModel(
            navigationViewModel = navViewModel,
            navigationCollection = navCollection
        )
        mainActivity.navigateWith(
            Navigation.Splash(SplashNavigation.SPLASH_TO_HOME), null
        )
        // Then
        Mockito.verify(navCollection, Mockito.atLeastOnce()).splashNavigation(
            navViewModel, Navigation.Splash(SplashNavigation.SPLASH_TO_HOME)
        )

        // When
        val expectedArticle = ArticleDetails(
            title = "Sea Dogs defeat Bulldogs to capture 2nd Memorial Cup in franchise's 17-year history - CBC Sports",
            description = "Considered a long shot at the beginning of the Canadian Hockey League championship due to a first-round loss in the Quebec Major Junior Hockey League playoffs, the host Saint John Sea Dogs downed the Hamilton Bulldogs 6-3 in Wednesday's championship game",
            urlToImage = "https://i.cbc.ca/1.6506512.1656553746!/fileImage/httpImage/image.jpg_gen/derivatives/16x9_620/memorial-cup-2022-sea-dogs-saint-john.jpg",
            publishedAt = "2022-06-30T01:54:00Z",
            content = "The Saint John Sea Dogs turned a devastating playoff loss into a Memorial Cup championship, thanks to renewed focus, 40 days of sweat and a university coach who pushed all the right buttons.\\r\\nConside… [+5750 chars]\"},{\"source\":{\"id\":null,\"name\":\"Ctvnews.ca\"},\"author\":null,\"title\":\"Flights, destinations 'likely' to be cancelled at Montreal airport - CTV News Montreal\",\"description\":\"Canadians flying out of the Montreal airport might be in for a bit of a shock this summer as the head of the Trudeau airport says airlines will \\\"likely\\\" be asked to cancel some flights — or even destinations altogether.",
        )

        mainActivity.navigateWith(
            Navigation.Home(HomeNavigation.HOME_TO_DETAILS), expectedArticle
        )
        // Then
        Mockito.verify(navCollection, Mockito.atLeastOnce()).homeNavigation(
            navViewModel, Navigation.Home(HomeNavigation.HOME_TO_DETAILS), expectedArticle
        )
    }
}
