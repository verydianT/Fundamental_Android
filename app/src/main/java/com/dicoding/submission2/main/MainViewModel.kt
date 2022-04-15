package com.dicoding.submission2.main

import androidx.lifecycle.*
import com.dicoding.submission2.Event
import com.dicoding.submission2.database.Repository
import com.dicoding.submission2.model.GithubItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val isLoading : LiveData<Boolean> = repository.isLoading
    val listUser : LiveData<List<GithubItem>> = repository.listUser
    val snackbarText : LiveData<Event<String>> = repository.snackbarText
    val themeSetting : LiveData<Boolean> = repository.getThemeSetting()

    fun getUser(query: String?) {
        viewModelScope.launch {
            repository.getUser(query)
        }
    }

    fun saveThemeSetting(setting: Boolean) {
        viewModelScope.launch {
            repository.saveThemeSetting(setting)
        }
    }
}