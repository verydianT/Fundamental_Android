package com.dicoding.submission2.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.R
import com.dicoding.submission2.database.Repository
import com.dicoding.submission2.databinding.ActivityMainBinding
import com.dicoding.submission2.favorite.FavoriteActivity
import com.dicoding.submission2.helper.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private val mainViewModel : MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: Repository
    private lateinit var user: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User's Search"

        binding.searchUser.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    user = p0.toString()
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

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        mainViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackbar ->
                Toast.makeText(this, snackbar, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showLoading(Loading: Boolean) {
        binding.progressBar.visibility = if (Loading) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.fav -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}