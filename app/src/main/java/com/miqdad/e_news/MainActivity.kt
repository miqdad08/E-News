package com.miqdad.e_news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.miqdad.e_news.databinding.ActivityMainBinding
import com.miqdad.e_news.ui.home.HomeViewModel

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding as ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.swipeRefreshLayout.setOnRefreshListener {
            recreate()
        }
// value of navView to bind
        val navView: BottomNavigationView = binding.navView
// value for navController to declare
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // val for appbarconfigure
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                // set of home fragment and search fragment
                R.id.home_fragment,
                R.id.search_fragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        binding.navView.selectedItemId = R.id.nav_host_fragment_activity_main
    }
}
