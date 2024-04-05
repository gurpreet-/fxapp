package com.fxapp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.fxapp.R
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavController()
    }

    private fun setupNavController() {
        // Retrieve the nav controller
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.main_fragment_container
        ) as NavHostFragment
        val navController = navHostFragment.navController

        // Set up the nav controller with our global graph
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<MaterialToolbar>(R.id.main_toolbar).apply {
            setupWithNavController(navController, appBarConfiguration)
        }
    }
}
