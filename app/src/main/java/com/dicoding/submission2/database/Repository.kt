package com.dicoding.submission2.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.submission2.Event
import com.dicoding.submission2.api.ApiService
import com.dicoding.submission2.helper.Helper
import com.dicoding.submission2.helper.Preferences
import com.dicoding.submission2.helper.ViewModelFactory
import com.dicoding.submission2.model.GithubItem
import com.dicoding.submission2.model.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(
    private val apiService: ApiService,
    private val mUserDAO: UserDAO
    ) {

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val listGithub = MutableLiveData<List<GithubItem>>()
    private val loading = MutableLiveData<Boolean>()
    private val snackbar = MutableLiveData<Event<String>>()

    val listUser : LiveData<List<GithubItem>> = listGithub
    val isLoading : LiveData<Boolean> = loading
    val snackbarText : LiveData<Event<String>> = snackbar

    fun getUser(query: String?) {
        loading.value = true
        val client = apiService.getUser(query)
        client.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>
            ) {
                loading.value = false
                if (response.isSuccessful) {
                    listGithub.value = response.body()?.items
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                loading.value = false
                snackbar.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFavorite(): LiveData<List<UserEntity>> = mUserDAO.getFavorite()

    fun addFavorite(user: UserEntity) {
        executorService.execute{ mUserDAO.insertFavorite(user) }
    }

    fun deleteFavorite(user: UserEntity) {
        executorService.execute{ mUserDAO.deleteFavorite(user) }
    }

    companion object{
        private const val TAG = "MainViewModel"

        @Volatile
        private var INSTANCE: Repository? = null
        fun getInstance(apiService: ApiService,
                        dao: UserDAO) : Repository =
            INSTANCE ?: synchronized(this) {
                Repository(apiService, dao).also {
                    INSTANCE = it
                }
            }
    }
}