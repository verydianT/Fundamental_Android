package com.dicoding.submission2.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission2.Event
import com.dicoding.submission2.api.ApiConfig
import com.dicoding.submission2.model.GithubItem
import com.dicoding.submission2.model.RespondDataUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _listDataUser = MutableLiveData<List<RespondDataUser>>()
    val listDataUser : LiveData<List<RespondDataUser>> = _listDataUser

    private val _dataUser = MutableLiveData<GithubItem>()
    val dataUser : LiveData<GithubItem> = _dataUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText : LiveData<Event<String>> = _snackbarText

    companion object{
        private const val TAG = "DetailViewModel"
    }

    fun getDataUser(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDataUser(username)
        client.enqueue(object : Callback<GithubItem> {
            override fun onResponse(call: Call<GithubItem>, response: Response<GithubItem>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _dataUser.value = response.body()
                } else {
                    _snackbarText.value = Event(response.toString())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubItem>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollower(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollower(username)
        client.enqueue(object : Callback<ArrayList<RespondDataUser>> {
            override fun onResponse(call: Call<ArrayList<RespondDataUser>>, response: Response<ArrayList<RespondDataUser>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listDataUser.postValue(response.body())
                } else {
                    _snackbarText.value = Event(response.toString())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<RespondDataUser>>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<ArrayList<RespondDataUser>> {
            override fun onResponse(call: Call<ArrayList<RespondDataUser>>, response: Response<ArrayList<RespondDataUser>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listDataUser.postValue(response.body())
                } else {
                    _snackbarText.value = Event(response.toString())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<RespondDataUser>>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}