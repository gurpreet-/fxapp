package com.fxapp.liblogin

import com.fxapp.libfoundation.wrappers.NavigationWrapper
import com.fxapp.libtest.BaseUnitTest
import com.fxapp.login.domain.repository.AuthRepository
import com.fxapp.login.domain.usecases.LoginUseCase
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest : BaseUnitTest() {

    private val authRepository = mockk<AuthRepository>(relaxed = true)
    private val navigationWrapper = mockk<NavigationWrapper>(relaxed = true)
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun beforeTest() {
        loginUseCase = LoginUseCase(authRepository, navigationWrapper)
    }

    @Test
    fun `when invoked, then try login`() = runTest {
        loginUseCase.invoke()
        verify {
            authRepository.isLoggedIn = true
            navigationWrapper.navigateToDeepLink(any(), any())
        }
    }
}
