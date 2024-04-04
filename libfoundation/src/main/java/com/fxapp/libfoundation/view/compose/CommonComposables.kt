package com.fxapp.libfoundation.view.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import com.fxapp.libfoundation.view.theme.Theme

typealias SimpleCallback = () -> Unit
typealias ComposeObject = @Composable SimpleCallback

@Composable
fun FxAppScreen(content: ComposeObject) {
    val snackbarHostState = remember { SnackbarHostState() }
    FxAppTheme {
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

@Composable
fun FxAppTheme(content: ComposeObject) {
    val colourScheme = if (isSystemInDarkTheme()) {
        Theme.Colours.darkColourTheme()
    } else {
        Theme.Colours.lightColourTheme()
    }
    MaterialTheme(
        colorScheme = colourScheme,
        shapes = MaterialTheme.shapes.copy(small = CutCornerShape(12.dp)),
        content = content
    )
}

@Composable
fun RenderPreview(content: ComposeObject) = FxAppTheme(content)