package com.ainsigne.caa_newsapp.navigation

import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ainsigne.common.utils.ui.viewmodel.SingleLiveEvent

/**
 * Navigation Coordinator Viewmodel for coordinating all the navigations within the app
 */
class NavigationCoordinatorViewModel : ViewModel() {

    /**
     * navigation observer for consolidating navigation [SingleLiveEvent] [NavigationCoordinator]
     */
    private val _navigation = SingleLiveEvent<NavigationCoordinator?>(null)
    /**
     * navigation observer for consolidating navigation [SingleLiveEvent] [NavigationCoordinator]
     */
    val navigation: LiveData<NavigationCoordinator?> = _navigation

    /**
     * Updates navigation value to popbackstack
     */
    fun popBackStack() {
        _navigation.value = NavigationCoordinator.PopBackStack
    }

    /**
     * Updates navigation value to popbackstack with specified destination
     */
    fun popBackStack(@IdRes destinationId: Int, inclusive: Boolean = false) {
        _navigation.value = NavigationCoordinator.PopBackStackSpecific(destinationId, inclusive)
    }

    fun navigateUp() {
        _navigation.value = NavigationCoordinator.NavigateUp
    }

    /**
     * Splash Navigation Methods
     */
    fun splashToHome() {
        _navigation.value = NavigationCoordinator.SplashToHome
    }
//
//    fun homeToFavorites() {
//        _navigation.value = NavigationCoordinator.HomeToFavorites
//    }


}
