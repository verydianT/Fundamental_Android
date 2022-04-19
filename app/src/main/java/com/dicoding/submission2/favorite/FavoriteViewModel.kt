package com.dicoding.submission2.favorite

import androidx.lifecycle.ViewModel
import com.dicoding.submission2.database.Repository
import com.dicoding.submission2.database.UserEntity

class FavoriteViewModel(private val repository: Repository) : ViewModel() {

    fun getFavoriteUser() = repository.getFavorite()

    fun addOrdeleteFavorite(user: UserEntity, favorite: Boolean) {
            if (favorite) {
                repository.deleteFavorite(user, false)
            } else {
                repository.addFavorite(user, true)
            }
    }
}