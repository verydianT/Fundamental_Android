package com.dicoding.submission2.main

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.submission2.Event
import com.dicoding.submission2.api.ApiConfig
import com.dicoding.submission2.model.GithubItem
import com.dicoding.submission2.model.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<GithubItem>>()
    val listUser : LiveData<List<GithubItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText : LiveData<Event<String>> = _snackbarText

    companion object{
        private const val TAG = "MainViewModel"
    }

    fun getUser(query: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>
            ) {
                _isLoading.value = false
                val listUser = response.body()?.items
                if (response.isSuccessful) {
                        _listUser.value = listUser!!
                } else {
                    _snackbarText.value = Event(response.toString())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}