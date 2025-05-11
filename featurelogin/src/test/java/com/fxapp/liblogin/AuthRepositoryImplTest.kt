package com.fxapp.liblogin

import android.content.SharedPreferences
import com.fxapp.libfoundation.wrappers.SharedPreferencesWrapper
import com.fxapp.libfoundation.wrappers.SharedPreferencesWrapper.Companion.IS_LOGGED_IN
import com.fxapp.libtest.BaseUnitTest
import com.fxapp.login.data.model.AuthRepositoryImpl
import io.mockk.every
import io.mockk.invoke
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse

class AuthRepositoryImplTest : BaseUnitTest() {

    private val sharedPreferenceEditor = mockk<SharedPreferences.Editor>(relaxed = true)
    private val sharedPreferences = mockk<SharedPreferencesWrapper>(relaxed = true) {
        every { getEditor() } returns sharedPreferenceEditor
        every { edit(any(), captureLambda()) } answers { lambda<(SharedPreferences.Editor) -> Unit>().invoke(sharedPreferenceEditor) }
    }
    private lateinit var authRepository: AuthRepositoryImpl

    @Before
    fun beforeTest() {
        authRepository = AuthRepositoryImpl(sharedPreferences)
    }

    @Test
    fun `returns default value for is logged in`() = runTest {
        val isLoggedIn = authRepository.isLoggedIn
        verify {
            sharedPreferences.getBoolean(any(), any())
        }
        assertFalse(isLoggedIn)
    }

    @Test
    fun `when logged in set to true, then shared prefs saves true`() = runTest {
        authRepository.isLoggedIn = true
        verify {
            sharedPreferences.edit(any(), any())
            sharedPreferenceEditor.putBoolean(IS_LOGGED_IN, true)
        }
    }

    @Test
    fun `when logged in set to false, then shared prefs saves false`() = runTest {
        authRepository.isLoggedIn = false
        verify {
            sharedPreferences.edit(any(), any())
            sharedPreferenceEditor.putBoolean(IS_LOGGED_IN, false)
        }
    }

    @Test
    fun `clears shared prefs on reset`() = runTest {
        authRepository.reset()
        verify {
            sharedPreferenceEditor.clear()
        }
    }
}
