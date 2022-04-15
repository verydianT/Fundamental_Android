package com.dicoding.submission2.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.R
import com.dicoding.submission2.databinding.ActivityMainBinding
import com.dicoding.submission2.favorite.FavoriteActivity
import com.dicoding.submission2.helper.SettingPreferences
import com.dicoding.submission2.helper.ViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private val mainViewModel : MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
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
                        binding.theme.visibility = View.VISIBLE
                    } else {
                        binding.logo.visibility = View.INVISIBLE
                        binding.text.visibility = View.INVISIBLE
                        binding.theme.visibility = View.INVISIBLE
                    }
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    binding.rvUser.adapter = UserAdapter(emptyList())
                    binding.logo.visibility = View.VISIBLE
                    binding.text.visibility = View.VISIBLE
                    binding.theme.visibility = View.VISIBLE
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

        val switchTheme = findViewById<SwitchMaterial>(R.id.theme)
        val pref = SettingPreferences.getInstance(dataStore)
        mainViewModel.themeSetting.observe(this) { darkMode: Boolean ->
            if (darkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }
        switchTheme.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked) }
    }

    private fun showLoading(Loading: Boolean) {
        binding.progressBar.visibility = if (Loading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.fav -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}