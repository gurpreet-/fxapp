package com.fxapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.fxapp.login.data.model.AuthRepositoryImpl
import com.fxapp.login.presentation.activity.LoginActivity
import com.fxapp.screens.HomeScreen
import com.fxapp.screens.LoginScreen
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module


class LoginScreenUITest : BaseUITest() {

    private val authModelRepository = mockk<AuthRepositoryImpl>(relaxed = true)

    @get:Rule
    val composeRule = createAndroidComposeRule<LoginActivity>()


    override fun getTestingModule() = module {
        factory { authModelRepository }
    }

    @Before
    fun testBefore() {
        every { authModelRepository.isLoggedIn } returns false
    }

    @Test
    fun testIfLoginScreenLogsIn() = run {
        step("Logs in correctly") {
            onComposeScreen<LoginScreen>(composeRule) {
                loginButton {
                    isDisplayed()
                    every { authModelRepository.isLoggedIn } returns true
                    performClick()
                }
            }
            onComposeScreen<HomeScreen>(composeRule) {
                amountField {
                    isDisplayed()
                }
            }
        }
    }
}
