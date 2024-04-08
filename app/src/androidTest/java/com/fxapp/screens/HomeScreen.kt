package com.fxapp.screens

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import com.fxapp.view.compose.TestTags.AMOUNT_FIELD
import com.fxapp.view.compose.TestTags.CURRENCY_SELECTOR_BUTTON
import com.fxapp.view.compose.TestTags.CURRENCY_SELECTOR_SCREEN
import com.fxapp.view.compose.TestTags.TYPE_SOMETHING
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class HomeScreen(semanticsProvider: SemanticsNodeInteractionsProvider) : ComposeScreen<HomeScreen>(
    semanticsProvider
) {

    val amountField: KNode = child {
        hasTestTag(AMOUNT_FIELD)
    }

    val typeSomethingView: KNode = child {
        hasTestTag(TYPE_SOMETHING)
    }

    val curencySelectorButton: KNode = child {
        hasTestTag(CURRENCY_SELECTOR_BUTTON)
    }

    val curencySelectorScreen: KNode = child {
        hasTestTag(CURRENCY_SELECTOR_SCREEN)
    }
}
