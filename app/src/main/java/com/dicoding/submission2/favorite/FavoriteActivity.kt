package com.dicoding.submission2.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.R
import com.dicoding.submission2.database.UserEntity
import com.dicoding.submission2.databinding.ActivityFavoriteBinding
import com.dicoding.submission2.helper.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var user: UserEntity? = null
    private var _ActivityFavoriteBinding: ActivityFavoriteBinding? = null
    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var factory: ViewModelFactory
    private lateinit var favorite: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        factory = ViewModelFactory.getInstance(this)

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
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

}