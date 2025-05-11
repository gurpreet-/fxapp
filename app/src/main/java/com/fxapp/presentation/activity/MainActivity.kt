package com.fxapp.presentation.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.fxapp.R
import com.fxapp.libfoundation.presentation.event.FxAppEvent
import com.fxapp.login.presentation.viewmodel.LoginViewModel
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val loginViewModel by viewModel<LoginViewModel>()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = setupNavController()
        loginViewModel.onEvent(FxAppEvent.AppStartUpEvent)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onSupportNavigateUp()
            true
        } else {
            false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (onBackPressedDispatcher.hasEnabledCallbacks()) {
            onBackPressedDispatcher.onBackPressed()
            true
        } else {
            navController.navigateUp() || super.onSupportNavigateUp()
        }
    }
}
