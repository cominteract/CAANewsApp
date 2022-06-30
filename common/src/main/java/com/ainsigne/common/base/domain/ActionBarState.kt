package com.ainsigne.common.base.domain

/**
 * Action bar state identifies if action bar will be displayed with back button,
 * user header, or no action bar
 * @property CHILD means action bar with title is optionally displayed with back button
 * @property CHILD_TOP means action bar with title is optionally displayed without back button
 * @property HOME means action bar home is displayed
 * @property NONE no action bar
 */
enum class ActionBarState {
    CHILD,
    CHILD_TOP,
    HOME,
    NONE
}
