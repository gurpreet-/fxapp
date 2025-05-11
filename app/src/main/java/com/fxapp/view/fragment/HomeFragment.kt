package com.fxapp.view.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import com.fxapp.R
import com.fxapp.libfoundation.presentation.view.base.ComposeFragment
import com.fxapp.libfoundation.presentation.view.compose.ComposeObject
import com.fxapp.login.presentation.viewmodel.LoginViewModel
import com.fxapp.view.compose.HomeScreen

class HomeFragment : ComposeFragment(), MenuProvider {

    private val viewModel by activityViewModels<LoginViewModel>()

    override val composeScreen: ComposeObject
        get() = { HomeScreen() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.addMenuProvider(this, viewLifecycleOwner)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.logout -> {
                viewModel.logout()
                true
            }
            else -> false
        }
    }
}