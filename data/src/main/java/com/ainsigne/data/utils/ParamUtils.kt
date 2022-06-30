package com.ainsigne.data.utils

import com.ainsigne.common.utils.US
import com.ainsigne.data.local.datastore.CAANewsAppDataStore
import com.ainsigne.data.local.datastore.DataStoreKeys
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
suspend fun CAANewsAppDataStore.getCode(): String {
    val countryCode = this.getValue(DataStoreKeys.KEY_PROFILE_COUNTRY_CODE)
    val code = if (countryCode.isNullOrEmpty()) {
        US
    } else {
        countryCode
    }
    return code
}

@DelicateCoroutinesApi
suspend fun CAANewsAppDataStore.getHeadlineRefresh(): Long? {
    return getValue(DataStoreKeys.KEY_REFRESH_HEADLINE)
}
