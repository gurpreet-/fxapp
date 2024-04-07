package com.fxapp.login.viewmodel

import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.fxapp.libfoundation.viewmodel.base.BaseViewModel
import com.fxapp.libfoundation.wrappers.BuildWrapper
import com.fxapp.libfoundation.wrappers.NavigationWrapper
import com.fxapp.login.R
import com.fxapp.login.model.AuthModel
import com.fxapp.libfoundation.R as LFR

class LoginViewModel(
    val authModel: AuthModel,
    val navigationWrapper: NavigationWrapper,
    val buildWrapper: BuildWrapper
) : BaseViewModel() {

    fun goToLoginScreenIfNotLoggedIn(navController: NavController) {
        if (!isLoggedIn()) {
            logout(navController)
        }
    }

    fun isLoggedIn() = authModel.isLoggedIn

    fun login() {
        authModel.isLoggedIn = true
        navigationWrapper.navigateToDeepLink(LFR.id.home_fragment)
    }

    fun logout(navController: NavController) {
        navController.navigate(R.id.login_nav_graph, null, navOptions {
            popUpTo(R.id.login_nav_graph)
        })
    }

    fun showHelperText(): Boolean {
        return buildWrapper.isDebug
    }
}
