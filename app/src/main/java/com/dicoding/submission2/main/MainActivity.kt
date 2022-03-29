package com.dicoding.submission2.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var user: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User's Search"

        binding.searchUser.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    user = query.toString()
                    clearFocus()
                    val data = mainViewModel.getUser(user)
                    if (data.equals(null)) {
                        binding.rvUser.adapter = UserAdapter(emptyList())
                        binding.logo.visibility = View.VISIBLE
                        binding.text.visibility = View.VISIBLE
                    } else {
                        binding.logo.visibility = View.INVISIBLE
                        binding.text.visibility = View.INVISIBLE
                    }
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    user = p0.toString()
                    binding.rvUser.adapter = UserAdapter(emptyList())
                    binding.logo.visibility = View.VISIBLE
                    binding.text.visibility = View.VISIBLE
                    return true
                }
            })
        }

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }

        mainViewModel.listUser.observe(this){ listUser ->
            binding.rvUser.adapter = UserAdapter(listUser)
        }
        mainViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackbar ->
                Toast.makeText(this, snackbar, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }
    }

    private fun showLoading(Loading: Boolean) {
        binding.progressBar.visibility = if (Loading) View.VISIBLE else View.GONE
    }
}