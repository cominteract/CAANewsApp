package com.ainsigne.caa_newsapp.navigation


import com.ainsigne.common.Navigation
import com.ainsigne.common.navigation.CustomNavigation
import com.ainsigne.common.navigation.HomeNavigation
import com.ainsigne.common.navigation.SplashNavigation
import timber.log.Timber

/**
 * Navigation collection to modularize navigation
 */
class NavigationCollection {

    /**
     * Custom navigation instances not related to features.
     * This should be updated whenever new modules are developed
     * @param navigationViewModel navigation view model used in navigating between fragments/views
     * @param navigation [Navigation] to identify which navigation will be used
     * @param data [T] optional data to be passed while navigating
     */
    fun <T> customNavigation(
        navigationViewModel: NavigationCoordinatorViewModel,
        navigation: Navigation,
        data: T?
    ) {
        when (navigation) {
            Navigation.Custom(CustomNavigation.POP_BACK) -> {
                navigationViewModel.popBackStack()
            }
            else -> {
                Timber.d(" Unhandled Navigation $data")
            }
        }
    }

    /**
     * Splash navigation instances.
     * This should be updated whenever new modules are developed
     * @param navigationViewModel navigation view model used in navigating between fragments/views
     * @param navigation [Navigation] to identify which navigation will be used
     * @param data [T] optional data to be passed while navigating
     */
    fun splashNavigation(
        navigationViewModel: NavigationCoordinatorViewModel,
        navigation: Navigation
    ) {
        when (navigation) {
            Navigation.Splash(SplashNavigation.SPLASH_TO_HOME) -> {
                navigationViewModel.splashToHome()
            }
            else -> {
                Timber.d(" Unhandled Navigation ")
            }
        }
    }

    /**
     * Home navigation instances.
     * This should be updated whenever new modules are developed
     * @param navigationViewModel navigation view model used in navigating between fragments/views
     * @param navigation [Navigation] to identify which navigation will be used
     */
    fun <T> homeNavigation(
        navigationViewModel: NavigationCoordinatorViewModel,
        navigation: Navigation,
        data: T?
    ) {
        when (navigation) {
            Navigation.Home(HomeNavigation.HOME_TO_DETAILS) -> {
                navigationViewModel.homeToDetails()
            }
            else -> {
                Timber.d(" Unhandled Navigation $data")
            }
        }
    }

}
