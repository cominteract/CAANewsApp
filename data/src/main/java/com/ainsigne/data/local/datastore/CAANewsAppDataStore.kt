package com.ainsigne.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Data store for saving preferences
 * @param context [Context] needed to create preference datastore instance
 */
class CAANewsAppDataStore @Inject constructor(
    val context: Context
) {
    // Preference data store instance for saving preferences data
    val Context.dataStore by preferencesDataStore(DataStoreKeys.NAME)

    /**
     * write/save value with indicated key defined in [DataStoreKeys]
     * @param key [Preferences.Key] input to identify which preference data to overwrite
     * @param value [T] data to saved with indicated key
     */
    suspend fun <T> write(
        key: Preferences.Key<T>,
        value: T
    ) {
        context.dataStore.edit {
            it[key] = value
        }
    }
    /**
     * write/save value with indicated key defined in [DataStoreKeys]
     * @param key [String] input to identify which preference string to overwrite
     * @param value [String] data to saved with indicated key
     */
    suspend fun write(
        key: String,
        value: String
    ) {
        context.dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    /**
     * remove/delete data associated with key input
     * @param key [Preferences.Key] to indicate which data to remove/delete
     */
    suspend fun <T> remove(
        key: Preferences.Key<T>,
    ) {
        context.dataStore.edit {
            it.remove(key)
        }
    }

    @DelicateCoroutinesApi
    suspend fun <T> getValue(
        key: Preferences.Key<T>
    ): T? {
        return datastoreData().data.first()[key]
    }

    fun datastoreData() = context.dataStore
}
