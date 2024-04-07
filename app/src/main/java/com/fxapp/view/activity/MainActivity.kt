package com.fxapp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.fxapp.R
import com.fxapp.login.viewmodel.LoginViewModel
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = setupNavController()
        loginViewModel.goToLoginScreenIfNotLoggedIn(navController)
    }

    private fun setupNavController(): NavController {
        // Retrieve the nav controller
        val navHostFragment = supportFragmentManager.findFragmentById(
            com.fxapp.libfoundation.R.id.main_fragment_container
        ) as NavHostFragment
        val navController = navHostFragment.navController

        // Set up the nav controller with our global graph
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<MaterialToolbar>(R.id.main_toolbar).apply {
            setupWithNavController(navController, appBarConfiguration)
            setSupportActionBar(this)
        }
        return navController
    }
}
