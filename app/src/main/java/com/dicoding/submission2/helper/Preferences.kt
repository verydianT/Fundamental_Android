package com.dicoding.submission2.helper

import androidx.datastore.core.DataStore

class Preferences private constructor(private val dataStore: DataStore<Preferences>){

    companion object {
        @Volatile
        private var INSTANCE: Preferences? = null

        @JvmStatic
        fun getInstance(dataStore: DataStore<Preferences>): Preferences {
            return INSTANCE ?: synchronized(this) {
                val instance = Preferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}