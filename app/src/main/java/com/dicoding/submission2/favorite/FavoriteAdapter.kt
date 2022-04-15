package com.dicoding.submission2.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission2.database.UserEntity
import com.dicoding.submission2.databinding.ItemUserBinding
import com.dicoding.submission2.detail.DetailActivity
import com.dicoding.submission2.helper.UserDiffCallback

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var listUser = ArrayList<UserEntity>()

    fun setList(list: List<UserEntity>) {
        val diffCallback = UserDiffCallback(this.listUser, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUser.addAll(list)
        this.listUser = list as ArrayList<UserEntity>
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity) {
            binding.apply {
                tvUsername.text = user.username
                Glide.with(itemView.context)
                    .load(user.profile)
                    .circleCrop()
                    .into(tvAvatar)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, user.username)
                itemView.context.startActivity(intent)
            }
        }
    }
}