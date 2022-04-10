package com.dicoding.submission2.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.submission2.database.Repository
import com.dicoding.submission2.database.UserEntity

class FavoriteViewModel(private val repository: Repository) : ViewModel() {

    fun getFavoriteUser() = repository.getFavorite()

    fun addFavoriteUser(user: UserEntity) = repository.addFavorite(user)

    fun deleteFavoriteUser(user: UserEntity) = repository.deleteFavorite(user)
}