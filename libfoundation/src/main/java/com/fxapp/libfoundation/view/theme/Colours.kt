package com.fxapp.libfoundation.view.theme

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.fxapp.libfoundation.R

object Colours {

    @Composable
    fun default() = AppColours(
        green70 = colorResource(R.color.green70),
        green50 = colorResource(R.color.green50),
        green30 = colorResource(R.color.green30),
        green20 = colorResource(R.color.green20),
        green10 = colorResource(R.color.green10),
        lgreen50 = colorResource(R.color.lgreen50),
        lgreen40 = colorResource(R.color.lgreen40),
        lgreen20 = colorResource(R.color.lgreen20),
        grey60 = colorResource(R.color.grey60),
        grey50 = colorResource(R.color.grey50),
        grey40 = colorResource(R.color.grey40),
        grey10 = colorResource(R.color.grey10),
        slate50 = colorResource(R.color.slate50),
        slate20 = colorResource(R.color.slate20),
        slate10 = colorResource(R.color.slate10),
        neonGreen50 = colorResource(R.color.neonGreen50),
        sand50 = colorResource(R.color.sand50),
        sand40 = colorResource(R.color.sand40),
    )

    @Composable
    fun defaultTextFieldColors() = MaterialTheme.colorScheme.run {
        TextFieldDefaults.colors(
            focusedIndicatorColor = default().primaryColour,
            unfocusedIndicatorColor = default().primaryColour,
            focusedContainerColor = default().formFieldBackground,
            unfocusedContainerColor = default().formFieldBackground,
            focusedTextColor = default().formFieldBackground,
            unfocusedTextColor = default().formFieldBackground,
            unfocusedLeadingIconColor = default().formFieldBackground,
            focusedLeadingIconColor = default().formFieldBackground,
            cursorColor = default().cursorColour,
            selectionColors = TextSelectionColors(default().secondary, default().cursorColour.copy(alpha = 0.6f))
        )
    }

    @Composable
    fun lightColourTheme() = default().run {
        lightColorScheme(
            primary = primaryColour,
            primaryContainer = primaryColour,
            onPrimary = white,
            onPrimaryContainer = green20,
            background = background,
            secondary = secondary,
            secondaryContainer = secondaryContainer,
            onSecondaryContainer = secondaryContainer,
            inversePrimary = secondary,
            tertiaryContainer = green20
        )
    }

    // Add support for dark mode later on.
    @Composable
    fun darkColourTheme() = lightColourTheme()
}