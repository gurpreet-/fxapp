package com.fxapp.login.presentation.viewmodel

import com.fxapp.libfoundation.presentation.base.BaseViewModel
import com.fxapp.libfoundation.presentation.intent.FxAppEvent
import com.fxapp.libfoundation.presentation.state.FxAppState
import com.fxapp.libfoundation.wrappers.BuildWrapper
import com.fxapp.login.domain.repository.AuthRepository
import com.fxapp.login.domain.usecases.LoginUseCase
import com.fxapp.login.domain.usecases.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val authRepository: AuthRepository,
    private val buildWrapper: BuildWrapper
) : BaseViewModel() {

    val state = MutableStateFlow(FxAppState())

    fun onEvent(event: FxAppEvent) {
        when (event) {
            is FxAppEvent.AppStartUpEvent -> goToLoginScreenIfNotLoggedIn()
            is FxAppEvent.LoginEvent -> login()
            is FxAppEvent.LogoutEvent -> logout()
            is FxAppEvent.LoginFieldEvent -> storeUserNamePassword(event.username, event.password)
        }
    }

    fun goToLoginScreenIfNotLoggedIn() {
        if (!isLoggedIn()) {
            logout()
        }
    }

    fun isLoggedIn() = authRepository.isLoggedIn

    private fun storeUserNamePassword(username: String?, password: String?) {
        username?.let {
            state.update { it.copy(username = username) }
        }
        password?.let {
            state.update { it.copy(password = password) }
        }
    }

    fun login() {
        loginUseCase.invoke()
    }

    fun logout() {
        logoutUseCase.invoke()
    }

    fun showHelperText(): Boolean {
        return buildWrapper.isDebug
    }
}
