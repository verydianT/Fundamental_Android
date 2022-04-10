package com.dicoding.submission2.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "UserGit")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    val username: String,

    @ColumnInfo(name = "avatar_url")
    val profile: String? = null,

    @ColumnInfo(name = "favorite")
    var Favorite: Boolean? = null

) : Parcelable
