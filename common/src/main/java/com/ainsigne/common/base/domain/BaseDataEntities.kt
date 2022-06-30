package com.ainsigne.common.base.domain

import androidx.lifecycle.LiveData
import com.ainsigne.common.utils.network.NetworkStatus

/**
 * Base data entities for handling commonly used outside repo
 */
sealed class BaseDataEntities {

    /**
     * Status for holding observer handling
     * @param isHandlingError [Boolean] if true is supposed to show dialog
     * @param isNeedToRefresh [Boolean] if true can handle refresh by swipe refresh
     * @param observer [LiveData] [NetworkStatus] handle data observer commonly
     * @param successBlock [NetworkStatus] provided we want a more inline handling of success block
     */
    data class Status(
        val isHandlingError: Boolean = false,
        val isNeedToRefresh: Boolean = false,
        val observer: LiveData<NetworkStatus<*>>,
        val successBlock: ((NetworkStatus<*>) -> Unit)? = null
    )
}
