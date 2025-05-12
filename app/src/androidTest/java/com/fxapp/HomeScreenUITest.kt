package com.fxapp

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.fxapp.login.domain.repository.AuthRepository
import com.fxapp.presentation.activity.MainActivity
import com.fxapp.screens.HomeScreen
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module


class HomeScreenUITest : BaseUITest() {

    private val authRepository = mockk<AuthRepository>(relaxed = true)
    private lateinit var mainActivity: ActivityScenario<MainActivity>

    @get:Rule
    val composeRule = createEmptyComposeRule()


    override fun getTestingModule() = module {
        factory { authRepository }
    }

    @Before
    fun testBefore() {
        every { authRepository.isLoggedIn } returns true
        mainActivity = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testShowsAmountField() = run {
        onComposeScreen<HomeScreen>(composeRule) {
            amountField {
                isDisplayed()
            }
        }
    }

    @Test
    fun testShowsList() = run {
        step("Enter text in amount field") {
            onComposeScreen<HomeScreen>(composeRule) {
                amountField {
                    performTextInput("1.00")
                }
                typeSomethingView {
                    assertIsNotDisplayed()
                }
            }
        }
    }

    @Test
    fun testOpensCurrencySelector() = run {
        onComposeScreen<HomeScreen>(composeRule) {
            curencySelectorButton.performClick()
            curencySelectorScreen {
                assertIsDisplayed()
            }
        }
    }
}
