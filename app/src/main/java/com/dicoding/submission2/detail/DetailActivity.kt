package com.dicoding.submission2.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dicoding.submission2.R
import com.dicoding.submission2.database.UserEntity
import com.dicoding.submission2.databinding.ActivityDetailBinding
import com.dicoding.submission2.favorite.FavoriteViewModel
import com.dicoding.submission2.helper.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private val favoriteViewModel by viewModels<FavoriteViewModel>{
        ViewModelFactory.getInstance(this)
    }
    private var avatar: String? = null
    private var userN: String? = null

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_USER)
        detailViewModel.getDataUser(username)

        detailViewModel.dataUser.observe(this) {
                dataUser -> binding.apply {
                    Glide.with(this@DetailActivity)
                        .load(dataUser.avatarUrl)
                        .circleCrop()
                        .into(profile)
                    supportActionBar?.title = dataUser.name
                    usernameTv.text = "@${dataUser.login}"
                    nameTv.text = dataUser.name
                    tvCompany.text = dataUser.company
                    tvLocation.text = dataUser.location

                    val following = "${dataUser.following} Followers"
                    val followers = "${dataUser.followers} Following"
                    val repository = "${dataUser.publicRepos} Repository"

                    tvFollowing.text = following
                    tvFollowers.text = followers
                    tvRepository.text = repository
                }
            this.avatar = dataUser.avatarUrl
            this.userN = dataUser.login
        }

        val fragment = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.Following),
            FollowFragment.newInstance(FollowFragment.Followers)
        )
        val titleFragment = mutableListOf(
            getString(R.string.following), getString(R.string.followers)
        )
        val adapter = DetailAdapter(this, fragment)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) {
            tab, position -> tab.text = titleFragment[position]
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    detailViewModel.getFollower(username)
                } else {
                    detailViewModel.getFollowing(username)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        detailViewModel.getFollower(username)

        detailViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackbar ->
                Toast.makeText(this, snackbar, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.favoriteBtn.setOnClickListener {
            val favorite = UserEntity(userN!!, avatar)
            favorite.let {
                it.username = detailViewModel.dataUser.value!!.login
                it.profile = detailViewModel.dataUser.value!!.avatarUrl
            }
            favoriteViewModel.addFavoriteUser(favorite)
            finish()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progress.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}

