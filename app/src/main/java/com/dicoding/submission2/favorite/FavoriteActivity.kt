package com.dicoding.submission2.favorite

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.database.UserEntity
import com.dicoding.submission2.databinding.ActivityFavoriteBinding
import com.dicoding.submission2.helper.ViewModelFactory
import com.dicoding.submission2.model.GithubItem

class FavoriteActivity : AppCompatActivity() {

    private var _ActivityFavoriteBinding: ActivityFavoriteBinding? = null
    private val favoriteViewModel : FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favorite: FavoriteAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favorite = FavoriteAdapter()

        binding.rvFavorite.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = favorite
        }

        favoriteViewModel.getFavoriteUser().observe(this) { Userlist ->
            favorite.setList(Userlist)
            binding.progressFav.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _ActivityFavoriteBinding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}