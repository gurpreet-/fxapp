package com.fxapp.screens

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class LoginScreen(semanticsProvider: SemanticsNodeInteractionsProvider) : ComposeScreen<LoginScreen>(
    semanticsProvider
) {

    val loginButton: KNode = child {
        hasTestTag("Login")
    }
}
