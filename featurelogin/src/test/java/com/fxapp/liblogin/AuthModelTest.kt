package com.fxapp.liblogin

import com.fxapp.libfoundation.wrappers.SharedPreferencesWrapper
import com.fxapp.libtest.BaseUnitTest
import com.fxapp.login.model.AuthModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse

class AuthModelTest : BaseUnitTest() {

    private val sharedPreferences = mockk<SharedPreferencesWrapper>(relaxed = true)
    private lateinit var authModel: AuthModel


    @Before
    fun beforeTest() {
        authModel = AuthModel(sharedPreferences)
    }

    @Test
    fun `returns default value for is logged in`() = runTest {
        val isLoggedIn = authModel.isLoggedIn
        verify {
            sharedPreferences.getBoolean(any(), any())
        }
        assertFalse(isLoggedIn)
    }

    @Test
    fun `clears shared prefs on reset`() = runTest {
        authModel.reset()
        verify {
            sharedPreferences.edit(any(), any())
        }
    }
}
