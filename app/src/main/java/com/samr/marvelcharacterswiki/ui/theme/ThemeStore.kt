package com.samr.marvelcharacterswiki.ui.theme

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Store id for selected theme in preferences
 * */
class ThemeStore(private val context: Context) {
    companion object {
        private const val SETTINGS_FILE = "settings"
        private const val THEME_KEY = "$SETTINGS_FILE.theme"
        private val themePreferenceKey = intPreferencesKey(THEME_KEY)

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(SETTINGS_FILE)
    }

    fun getTheme(): Flow<Int> =
        context.dataStore.data.map { prefs -> prefs[themePreferenceKey] ?: 0 }

    suspend fun setTheme(theme: Int) {
        context.dataStore.edit { prefs -> prefs[themePreferenceKey] = theme }
    }
}

