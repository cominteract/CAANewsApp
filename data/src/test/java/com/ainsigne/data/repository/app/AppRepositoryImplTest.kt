package com.ainsigne.data.repository.app

import com.ainsigne.common.utils.US
import com.ainsigne.common.utils.network.NetworkStatus
import com.ainsigne.data.local.datastore.CAANewsAppDataStore
import com.ainsigne.data.local.datastore.DataStoreKeys
import com.ainsigne.domain.repository.AppRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class AppRepositoryImplTest {
    private lateinit var profileDataStore : CAANewsAppDataStore
    lateinit var repository : AppRepository

    @Before
    fun setup() {
        profileDataStore = Mockito.mock(CAANewsAppDataStore::class.java)
        repository = AppRepositoryImpl(
            dataStore = profileDataStore
        )
    }

    @Test
    fun saveCountryCode() {
        runBlocking {
            // When
            val countryCode = repository.saveCountryCode(US)
            // Then
            assert(countryCode.last() is NetworkStatus.Success)
            assert(countryCode.last().data == true)
            Mockito.verify(profileDataStore, Mockito.atLeastOnce()).write(
                DataStoreKeys.KEY_PROFILE_COUNTRY_CODE, US
            )
        }
    }

    @Test
    fun getCountryCode() {
        runBlocking {
            // When
            val countryDataStore = profileDataStore.getValue(DataStoreKeys.KEY_PROFILE_COUNTRY_CODE)
            Mockito.`when`(
                countryDataStore
            ).thenReturn(
                US
            )
            // Then
            assert(repository.getCountryCode().last() is NetworkStatus.Success)
            assert(repository.getCountryCode().last().data == US)
        }
    }

}