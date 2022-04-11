package com.dicoding.submission2.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.submission2.database.UserEntity

class UserDiffCallback(private val mOldlistUser: List<UserEntity>, private val mNewlistUser: List<UserEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldlistUser.size
    }
    override fun getNewListSize(): Int {
        return mNewlistUser.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldlistUser == mNewlistUser
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = mOldlistUser[oldItemPosition].username
        val new = mNewlistUser[newItemPosition].username
        return old == new
    }
}