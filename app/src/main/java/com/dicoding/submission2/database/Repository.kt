package com.dicoding.submission2.database

import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(private val mUserDAO: UserDAO) {

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getFavorite(): LiveData<List<UserEntity>> = mUserDAO.getFavorite()

    fun addFavorite(user: UserEntity) {
        executorService.execute{ mUserDAO.insertFavorite(user) }
    }

    fun deleteFavorite(user: UserEntity) {
        executorService.execute{ mUserDAO.deleteFavorite(user) }
    }
}