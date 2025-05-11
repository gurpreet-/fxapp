package com.fxapp.login.domain.usecases

import com.fxapp.libfoundation.R
import com.fxapp.libfoundation.wrappers.NavigationWrapper
import com.fxapp.login.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository,
    private val navigationWrapper: NavigationWrapper,
) {
    fun invoke() {
        repository.isLoggedIn = true
        navigationWrapper.navigateToDeepLink(R.id.home_fragment)
    }
}
