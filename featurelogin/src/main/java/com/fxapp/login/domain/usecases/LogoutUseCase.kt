package com.fxapp.login.domain.usecases

import com.fxapp.libfoundation.wrappers.NavigationWrapper
import com.fxapp.login.domain.repository.AuthRepository

class LogoutUseCase(
    private val repository: AuthRepository,
    private val navigationWrapper: NavigationWrapper,
) {
    fun invoke() {
        repository.reset()
        navigationWrapper.logout()
    }
}
