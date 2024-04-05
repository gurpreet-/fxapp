package com.fxapp.libfoundation.view.theme

import androidx.compose.ui.graphics.Color

data class AppColours(
    val black: Color = Color.Black,
    val white: Color = Color.White,
    val transparent: Color = Color.Transparent,
    val viewBackground: Color = white,
    val background: Color = viewBackground,

    val green70: Color,
    val green50: Color,
    val green30: Color,
    val green20: Color,
    val green10: Color,

    val grey40: Color,
    val grey10: Color,

    val neonGreen50: Color,

    val sand50: Color,
    val sand40: Color,

    val primaryColour: Color = green50,
    val primaryColourDark: Color = green70,
    val primaryColourLight: Color = green20,

    val secondary: Color = sand50,
    val secondaryContainer: Color = secondary,

    val formFieldBackground: Color = white,
    val formFieldTextColour: Color = black,
    val cursorColour: Color = neonGreen50,
    val textColour: Color = black,
)
