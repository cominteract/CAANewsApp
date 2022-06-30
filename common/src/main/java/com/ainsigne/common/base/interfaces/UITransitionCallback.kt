package com.ainsigne.common.base.interfaces

import com.ainsigne.common.utils.extension.EMPTY

/**
 * App wide interface for fragments that ui transition states
 */
interface UITransitionCallback {
    /**
     * onSwipeRefresh when data is refreshed
     */
    fun onSwipeRefresh(action: () -> Unit)

    /**
     * Stop refresh when data is already refreshed
     */
    fun stopRefresh()

    /**
     * Start refresh when data needs to be manually refreshed
     */
    fun startRefresh()

    /**
     * Is refreshing when data is currently being refreshed we dont want progress to appear
     */
    fun isRefreshing(): Boolean

    /**
     * Setup whether swipe refresh is enabled
     */
    fun setupRefresh(hasSwipeRefresh: Boolean = false)

    /**
     * Setup whether action bar is enabled with back button or not
     * @param title [String] title for the action bar
     * @param hasActionBar [Boolean] toggle to set whether actionbar is hidden or not
     * @param hasBackButton [Boolean] toogle to set whether actionbar has backbutton or not
     */
    fun setupActionbar(
        title: String = EMPTY,
        hasActionBar: Boolean = false,
        hasBackButton: Boolean = false,
        hasHomeBar: Boolean = false,
        backPressedBlock: (() -> Unit)? = null
    )

    fun setupLanguage(language: String)
}
