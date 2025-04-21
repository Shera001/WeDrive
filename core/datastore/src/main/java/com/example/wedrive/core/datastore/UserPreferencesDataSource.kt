package com.example.wedrive.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_prefs")

class UserPreferencesDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        val KEY_PHONE = stringPreferencesKey("user_phone")
    }

    val phoneFlow: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[KEY_PHONE] }

    suspend fun savePhone(phone: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_PHONE] = phone
        }
    }

    suspend fun clearPhone() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_PHONE)
        }
    }
}