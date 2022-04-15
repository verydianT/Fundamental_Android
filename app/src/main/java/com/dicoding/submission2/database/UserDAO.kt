package com.dicoding.submission2.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(user: UserEntity)

    @Query("SELECT * FROM UserGit")
    fun getFavorite(): LiveData<List<UserEntity>>

    @Delete
    fun deleteFavorite(user: UserEntity)
}