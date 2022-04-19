package com.dicoding.submission2.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.submission2.Event
import com.dicoding.submission2.api.ApiService
import com.dicoding.submission2.helper.SettingPreferences
import com.dicoding.submission2.model.GithubItem
import com.dicoding.submission2.model.ResponseUser
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(
    private val apiService: ApiService,
    private val mUserDAO: UserDAO,
    private val preferences: SettingPreferences
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

    fun addFavorite(user: UserEntity, favorite: Boolean) {
        executorService.execute{
            user.Favorite = favorite
            mUserDAO.insertFavorite(user)
        }
    }

    fun deleteFavorite(user: UserEntity, favorite: Boolean) {
        executorService.execute{
            user.Favorite = favorite
            mUserDAO.deleteFavorite(user)
        }
    }

    fun getThemeSetting(): Flow<Boolean> = preferences.getThemeSet()

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        preferences.saveTheme(isDarkModeActive)
    }

    companion object{
        private const val TAG = "MainViewModel"

        @Volatile
        private var INSTANCE: Repository? = null
        fun getInstance(apiService: ApiService, dao: UserDAO, preferences: SettingPreferences) : Repository =
            INSTANCE ?: synchronized(this) {
                Repository(apiService, dao, preferences).also {
                    INSTANCE = it
                }
            }
    }
}