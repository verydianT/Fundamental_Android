package com.dicoding.submission2.helper

import android.content.Context
import androidx.datastore.core.DataStore
import com.dicoding.submission2.api.ApiConfig
import com.dicoding.submission2.database.Repository
import com.dicoding.submission2.database.UserDatabase

object Helper {
    fun getRepository(context: Context): Repository{
        val apiService = ApiConfig.getApiService()
//        val preferences = Preferences.getInstance(dataStore)
        val database = UserDatabase.getDatabase(context)
        val dao = database.userDAO()
        return Repository.getInstance(apiService, dao)
    }
}