package com.fxapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.fxapp.login.model.AuthModel
import com.fxapp.login.view.activity.LoginActivity
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

    private val authModel = mockk<AuthModel>(relaxed = true)

    @get:Rule
    val composeRule = createAndroidComposeRule<LoginActivity>()


    override fun getTestingModule() = module {
        factory { authModel }
    }

    @Before
    fun testBefore() {
        every { authModel.isLoggedIn } returns false
    }

    @Test
    fun testIfLoginScreenLogsIn() = run {
        step("Logs in correctly") {
            onComposeScreen<LoginScreen>(composeRule) {
                loginButton {
                    isDisplayed()
                    every { authModel.isLoggedIn } returns true
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
