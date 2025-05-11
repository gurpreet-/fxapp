package com.fxapp.liblogin

import com.fxapp.libfoundation.R
import com.fxapp.libfoundation.wrappers.BuildWrapper
import com.fxapp.libfoundation.wrappers.NavigationWrapper
import com.fxapp.libtest.BaseUnitTest
import com.fxapp.login.data.model.AuthRepositoryImpl
import com.fxapp.login.presentation.viewmodel.LoginViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class LoginViewModelTest : BaseUnitTest() {

    private val authModelRepository: AuthRepositoryImpl = mockk(relaxed = true)
    private val navigationWrapper: NavigationWrapper = mockk(relaxed = true)
    private val buildWrapper: BuildWrapper = mockk(relaxed = true)
    private lateinit var viewModel: LoginViewModel

    @Before
    fun beforeTest() {
        viewModel = LoginViewModel(authModelRepository, navigationWrapper, buildWrapper)
    }

    @Test
    fun `on login navigates to home fragment`() = runTest {
        viewModel.login()
        verify {
            authModelRepository.isLoggedIn = true
            navigationWrapper.navigateToDeepLink(R.id.home_fragment)
        }
    }

    @Test
    fun `on login check false, navigates to login screen`() = runTest {
        every { authModelRepository.isLoggedIn } returns false
        viewModel.goToLoginScreenIfNotLoggedIn()
        verify {
            authModelRepository.reset()
            navigationWrapper.logout()
        }
    }

    @Test
    fun `on login check true, does not navigate to login screen`() = runTest {
        every { authModelRepository.isLoggedIn } returns true
        viewModel.goToLoginScreenIfNotLoggedIn()
        verify(atLeast = 0) {
            navigationWrapper.logout()
        }
    }
}
