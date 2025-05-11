package com.fxapp.liblogin

import com.fxapp.libfoundation.wrappers.SharedPreferencesWrapper
import com.fxapp.libtest.BaseUnitTest
import com.fxapp.login.data.model.AuthRepositoryImpl
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse

class AuthRepositoryImplTest : BaseUnitTest() {

    private val sharedPreferences = mockk<SharedPreferencesWrapper>(relaxed = true)
    private lateinit var authModelRepository: AuthRepositoryImpl


    @Before
    fun beforeTest() {
        authModelRepository = AuthRepositoryImpl(sharedPreferences)
    }

    @Test
    fun `returns default value for is logged in`() = runTest {
        val isLoggedIn = authModelRepository.isLoggedIn
        verify {
            sharedPreferences.getBoolean(any(), any())
        }
        assertFalse(isLoggedIn)
    }

    @Test
    fun `clears shared prefs on reset`() = runTest {
        authModelRepository.reset()
        verify {
            sharedPreferences.edit(any(), any())
        }
    }
}
