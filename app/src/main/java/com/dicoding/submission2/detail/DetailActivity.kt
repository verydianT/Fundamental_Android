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
import com.dicoding.submission2.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USER)
        detailViewModel.getDataUser(username)

        detailViewModel.dataUser.observe(this) {
                dataUser -> binding.apply {
                    Glide.with(this@DetailActivity)
                        .load(dataUser.avatarUrl)
                        .circleCrop()
                        .into(profile)
                    supportActionBar?.title =  dataUser.name
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
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progress.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}