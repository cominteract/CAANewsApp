package com.ainsigne.common.base.interfaces

import com.ainsigne.common.Navigation

/**
 * Navigation call back template for navigating with Navigation enum and
 * optional data
 */
interface NavigationCallback {
    /**
     * Navigate with corresponding enum and optional data
     * @param navigation [Navigation] to identify which navigation will be used
     * @param data [T] optional data to be passed while navigating
     */
    fun <T> navigateWith(navigation: Navigation, data: T?)
}
