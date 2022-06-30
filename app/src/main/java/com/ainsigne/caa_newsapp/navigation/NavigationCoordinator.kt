package com.ainsigne.caa_newsapp.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import com.ainsigne.domain.navigation.ArticleDetails
import com.ainsigne.home.ui.HomeFragmentDirections
import com.ainsigne.splash.ui.SplashFragmentDirections

/**
 * Navigation coordinnator that allows use of fragment directions to navigation
 * @param navDirections [NavDirections] the nav direction to be used to identify where to navigate
 */
sealed class NavigationCoordinator(val navDirections: NavDirections? = null, val navId: Int? = null) {
    /**
     * Navigate back to previous entry from stack
     */
    object PopBackStack : NavigationCoordinator()

    /**
     * Navigate back to one level higher
     */
    object NavigateUp : NavigationCoordinator()

    /**
     * Navigate to generic error with argument
     * in case we want to display a modal
     */
    data class NavigateToGenericErrorPage(val bundle: Bundle) : NavigationCoordinator()

    /**
     * Navigate back to specific destination id
     */
    data class PopBackStackSpecific(@IdRes val destinationId: Int, val inclusive: Boolean = false) :
        NavigationCoordinator()

    /**
     * Splash to home page
     */
    object SplashToHome : NavigationCoordinator(
        SplashFragmentDirections.splashToHome()
    )
//
//
//    /**
//     * Home to details
//     */
    data class HomeToDetails(val details: ArticleDetails?) : NavigationCoordinator(
        HomeFragmentDirections.homeToDetails(details)
    )

}
