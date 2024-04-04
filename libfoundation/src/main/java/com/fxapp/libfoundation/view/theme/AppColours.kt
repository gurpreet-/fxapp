package com.fxapp.libfoundation.view.theme

import androidx.compose.ui.graphics.Color

data class AppColours(
    val primary: Color,
    val background: Color,
    val secondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val inversePrimary: Color,

    val formFieldBackground: Color,
    val formFieldTextColour: Color,
    val cursorColour: Color,
    val textColour: Color,
    val transparent: Color = Color.Transparent,
    val primaryContainer: Color = primary,
)
