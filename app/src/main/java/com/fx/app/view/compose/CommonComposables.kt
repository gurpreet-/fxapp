package com.fx.app.view.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.fx.app.view.compose.theme.Theme

typealias SimpleCallback = () -> Unit
typealias ComposeObject = @Composable SimpleCallback

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FxAppMain(content: ComposeObject) {
    val snackbarHostState = remember { SnackbarHostState() }
    val colourScheme = if (isSystemInDarkTheme()) {
        Theme.Colours.darkColourTheme()
    } else {
        Theme.Colours.lightColourTheme()
    }
    MaterialTheme(
        colorScheme = colourScheme
    ) {
        Scaffold(
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) {
            Box(
                Modifier
                    .padding(it)
                    .fillMaxSize()) {
                content()
            }
        }
    }
}