package com.fxapp.login.presentation.viewmodel

import android.annotation.SuppressLint
import com.fxapp.libfoundation.viewmodel.base.BaseViewModel
import com.fxapp.libfoundation.wrappers.BuildWrapper
import com.fxapp.libfoundation.wrappers.NavigationWrapper
import com.fxapp.login.data.model.AuthRepositoryImpl
import com.fxapp.libfoundation.R as LFR

class LoginViewModel(
    private val authModelRepository: AuthRepositoryImpl,
    private val navigationWrapper: NavigationWrapper,
    private val buildWrapper: BuildWrapper
) : BaseViewModel() {

    fun goToLoginScreenIfNotLoggedIn() {
        if (!isLoggedIn()) {
            logout()
        }
    }

    fun isLoggedIn() = authModelRepository.isLoggedIn

    fun login() {
        authModelRepository.isLoggedIn = true
        navigationWrapper.navigateToDeepLink(LFR.id.home_fragment)
    }

    @SuppressLint("RestrictedApi")
    fun logout() {
        authModelRepository.reset()
        navigationWrapper.logout()
    }

    fun showHelperText(): Boolean {
        return buildWrapper.isDebug
    }
}
