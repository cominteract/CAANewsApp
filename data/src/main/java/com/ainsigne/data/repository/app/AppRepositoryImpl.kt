package com.ainsigne.data.repository.app

import com.ainsigne.common.utils.network.NetworkStatus
import com.ainsigne.data.local.datastore.CAANewsAppDataStore
import com.ainsigne.data.local.datastore.DataStoreKeys
import com.ainsigne.domain.repository.AppRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


/**
 * App repository for local and remote resource related data
 * @param dataStore for user profile preferences related requests
 */
@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
class AppRepositoryImpl(val dataStore: CAANewsAppDataStore) : AppRepository {

    /**
     * Returns a [NetworkStatus.Success] with [Boolean] set to true when successful
     * @param countryCode [String] country code to update
     * @return [Boolean] when country code is already saved
     */
    override suspend fun saveCountryCode(countryCode: String): Flow<NetworkStatus<Boolean>> {
        dataStore.write(DataStoreKeys.KEY_PROFILE_COUNTRY_CODE, countryCode)
        return flowOf(NetworkStatus.Success(true))
    }

    /**
     * Returns a [NetworkStatus.Success] with [String] set to current country code
     * @return [String] country code that is already saved
     */
    override suspend fun getCountryCode(): Flow<NetworkStatus<String>> {
        return flowOf(NetworkStatus.Success(dataStore.getValue(DataStoreKeys.KEY_PROFILE_COUNTRY_CODE).orEmpty()))
    }

}