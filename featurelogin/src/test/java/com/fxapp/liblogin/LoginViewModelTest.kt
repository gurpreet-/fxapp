package com.fxapp.liblogin

import com.fxapp.libfoundation.wrappers.BuildWrapper
import com.fxapp.libfoundation.wrappers.NavigationWrapper
import com.fxapp.libtest.BaseUnitTest
import com.fxapp.login.domain.repository.AuthRepository
import com.fxapp.login.domain.usecases.LoginUseCase
import com.fxapp.login.domain.usecases.LogoutUseCase
import com.fxapp.login.presentation.viewmodel.LoginViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class LoginViewModelTest : BaseUnitTest() {

    private val authRepository: AuthRepository = mockk(relaxed = true)
    private val navigationWrapper: NavigationWrapper = mockk(relaxed = true)
    private val loginUseCase: LoginUseCase = mockk(relaxed = true)
    private val logoutUseCase: LogoutUseCase = mockk(relaxed = true)
    private val buildWrapper: BuildWrapper = mockk(relaxed = true)
    private lateinit var viewModel: LoginViewModel

    @Before
    fun beforeTest() {
        viewModel = LoginViewModel(loginUseCase, logoutUseCase, authRepository, buildWrapper)
    }

    @Test
    fun `on login navigates to home fragment`() = runTest {
        viewModel.login()
        verify {
            loginUseCase.invoke()
        }
    }

    @Test
    fun `on login check false, navigates to login screen`() = runTest {
        every { authRepository.isLoggedIn } returns false
        viewModel.goToLoginScreenIfNotLoggedIn()
        verify {
            logoutUseCase.invoke()
        }
    }

    @Test
    fun `on login check true, does not navigate to login screen`() = runTest {
        every { authRepository.isLoggedIn } returns true
        viewModel.goToLoginScreenIfNotLoggedIn()
        verify(atLeast = 0) {
            navigationWrapper.logout()
        }
    }
}
