package com.dicoding.submission2.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.submission2.api.ApiConfig
import com.dicoding.submission2.database.Repository
import com.dicoding.submission2.database.UserDatabase

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object Helper {
    fun getRepository(context: Context): Repository{
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getDatabase(context)
        val dao = database.userDAO()
        val preferences = SettingPreferences.getInstance(context.dataStore)
        return Repository.getInstance(apiService, dao, preferences)
    }
}