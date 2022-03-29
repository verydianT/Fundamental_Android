package com.dicoding.submission2.api

import com.dicoding.submission2.BuildConfig
import com.dicoding.submission2.model.GithubItem
import com.dicoding.submission2.model.RespondDataUser
import com.dicoding.submission2.model.ResponseUser
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("search/users")
    fun getUser(
        @Query("q") query: String?
    ): Call<ResponseUser>

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("users/{username}")
    fun getDataUser(
        @Path("username") username: String?
    ): Call<GithubItem>

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("users/{username}/followers")
    fun getUserFollower(
        @Path("username") username: String?
    ): Call<ArrayList<RespondDataUser>>

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String?
    ): Call<ArrayList<RespondDataUser>>
}