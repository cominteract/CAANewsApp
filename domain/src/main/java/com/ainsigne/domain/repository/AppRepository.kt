package com.ainsigne.domain.repository

import com.ainsigne.common.utils.network.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    /**
     * Returns a [NetworkStatus.Success] with [Boolean] set to true when successful
     * @param countryCode [String] country code to update
     * @return [Boolean] when country code is already saved
     */
    suspend fun saveCountryCode(countryCode: String): Flow<NetworkStatus<Boolean>>

    /**
     * Returns a [NetworkStatus.Success] with [String] set to current country code
     * @return [String] country code that is already saved
     */
    suspend fun getCountryCode(): Flow<NetworkStatus<String>>

}