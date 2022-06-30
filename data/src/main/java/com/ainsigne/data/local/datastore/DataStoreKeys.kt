package com.ainsigne.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    // data store name
    const val NAME = "caanewsapp_dstore"

    // key for profile id used in profile data retrieval
    val KEY_PROFILE_COUNTRY_CODE = stringPreferencesKey(name = "caanews_profile_country_code")

}
