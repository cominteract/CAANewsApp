package com.ainsigne.common.base.interfaces

import com.ainsigne.common.base.domain.BaseDataEntities


/**
 * Status Callback that handles status changes
 */
interface StatusCallback {

    /**
     * Handle status for observers
     * @param vmObserverStatuses [List] [BaseDataEntities.Status] common status to be iterated and handled
     */
    fun handleStatus(vmObserverStatuses: List<BaseDataEntities.Status>)

    /**
     * If the ui has action bar and title. Fill this out
     * @return [String] the action bar title
     */
    fun actionBarTitle(): String

    /**
     * If back button has been pressed and needs to be handled. Add any handler here
     * @return [Unit] block to be invoked
     */
    fun backButtonPressed(): () -> Unit
}
