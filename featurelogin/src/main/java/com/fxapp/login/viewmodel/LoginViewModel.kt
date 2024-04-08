package com.fxapp.login.viewmodel

import android.annotation.SuppressLint
import com.fxapp.libfoundation.viewmodel.base.BaseViewModel
import com.fxapp.libfoundation.wrappers.BuildWrapper
import com.fxapp.libfoundation.wrappers.NavigationWrapper
import com.fxapp.login.model.AuthModel
import com.fxapp.libfoundation.R as LFR

class LoginViewModel(
    private val authModel: AuthModel,
    private val navigationWrapper: NavigationWrapper,
    private val buildWrapper: BuildWrapper
) : BaseViewModel() {

    fun goToLoginScreenIfNotLoggedIn() {
        if (!isLoggedIn()) {
            logout()
        }
    }

    fun isLoggedIn() = authModel.isLoggedIn

    fun login() {
        authModel.isLoggedIn = true
        navigationWrapper.navigateToDeepLink(LFR.id.home_fragment)
    }

    @SuppressLint("RestrictedApi")
    fun logout() {
        authModel.reset()
        navigationWrapper.logout()
    }

    fun showHelperText(): Boolean {
        return buildWrapper.isDebug
    }
}
