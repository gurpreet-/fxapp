package com.fxapp.liblogin

import com.fxapp.libfoundation.wrappers.NavigationWrapper
import com.fxapp.libtest.BaseUnitTest
import com.fxapp.login.domain.repository.AuthRepository
import com.fxapp.login.domain.usecases.LogoutUseCase
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class LogoutUseCaseTest : BaseUnitTest() {

    private val authRepository = mockk<AuthRepository>(relaxed = true)
    private val navigationWrapper = mockk<NavigationWrapper>(relaxed = true)
    private lateinit var logoutUseCase: LogoutUseCase

    @Before
    fun beforeTest() {
        logoutUseCase = LogoutUseCase(authRepository, navigationWrapper)
    }

    @Test
    fun `when invoked, then try login`() = runTest {
        logoutUseCase.invoke()
        verify {
            authRepository.reset()
            navigationWrapper.logout()
        }
    }
}
