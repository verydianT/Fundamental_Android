package com.dicoding.submission2.favorite

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.dicoding.submission2.database.Repository
import com.dicoding.submission2.database.UserEntity
import com.dicoding.submission2.detail.DetailActivity

class FavoriteViewModel(private val repository: Repository) : ViewModel() {

    fun getFavoriteUser() = repository.getFavorite()

    fun addOrdeleteFavorite(user: UserEntity, favorite: Boolean) {
        if (favorite) {
            repository.deleteFavorite(user)
        } else {
            repository.addFavorite(user)
        }
    }
}