package com.ainsigne.common

import com.wellbet.common.navigation.CustomNavigation
import com.ainsigne.common.navigation.HomeNavigation
import com.ainsigne.common.navigation.SplashNavigation

/**
 * Navigation enum for identifying which navigation will take place
 */
sealed class Navigation {
    /**
     * Custom navigation instance
     * @param navigation [SplashNavigation] splash navigation enums
     */
    data class Custom(val navigation: CustomNavigation) : Navigation()
    /**
     * Splash navigation instance
     * @param navigation [SplashNavigation] splash navigation enums
     */
    data class Splash(val navigation: SplashNavigation) : Navigation()

    /**
     * Home navigation instance
     * @param navigation [HomeNavigation] Home navigation enums
     */
    data class Home(val navigation: HomeNavigation) : Navigation()

}
