package com.fxapp.libfoundation.view.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.fxapp.libfoundation.view.theme.Colours
import com.fxapp.libfoundation.view.theme.Typography

typealias SimpleCallback = () -> Unit
typealias ComposeObject = @Composable SimpleCallback

@Composable
fun FxAppScreen(content: ComposeObject) = FxAppTheme {
    val snackbarHostState = remember { SnackbarHostState() }
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

@Composable
fun FxAppTheme(content: ComposeObject) {
    val colourScheme = if (isSystemInDarkTheme()) {
        Colours.darkColourTheme()
    } else {
        Colours.lightColourTheme()
    }
    val defaultTypography = Typography.default()
    CompositionLocalProvider(LocalTextStyle provides LocalTextStyle.current.merge(defaultTypography.bodyMedium)) {
        MaterialTheme(
            colorScheme = colourScheme,
            typography = defaultTypography,
            content = content
        )
    }
}

@Composable
fun RenderPreview(content: ComposeObject) = FxAppTheme(content)