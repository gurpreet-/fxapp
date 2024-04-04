package com.fxapp.libfoundation.view.theme

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.fxapp.libfoundation.R

object Colours {

    @Composable
    fun default() = AppColours(
        primary = colorResource(R.color.colorPrimary),
        background = colorResource(R.color.viewBackground),
        secondary = colorResource(R.color.colorSecondary),
        secondaryContainer = colorResource(R.color.colorSecondary),
        onSecondaryContainer = colorResource(R.color.white), // text on secondary
        inversePrimary = colorResource(R.color.colorSecondary),
        formFieldBackground = Color(0xfff8f9fa),
        formFieldTextColour = colorResource(R.color.black),
        textColour = colorResource(R.color.black),
        cursorColour = colorResource(R.color.lightGreen50),
    )

    @Composable
    fun defaultTextFieldColors() = MaterialTheme.colorScheme.run {
        TextFieldDefaults.colors(
            focusedIndicatorColor = default().transparent,
            unfocusedIndicatorColor = default().transparent,
            focusedContainerColor = default().formFieldBackground,
            unfocusedContainerColor = default().formFieldBackground,
            focusedTextColor = default().formFieldBackground,
            unfocusedTextColor = default().formFieldBackground,
            cursorColor = default().cursorColour,
            selectionColors = TextSelectionColors(default().inversePrimary, default().cursorColour.copy(alpha = 0.6f))
        )
    }

    @Composable
    fun defaultOutlinedTextFieldColors() = MaterialTheme.colorScheme.run {
        OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primary,
            unfocusedBorderColor = primary
        )
    }

    @Composable
    fun lightColourTheme() = default().run {
        lightColorScheme(
            primary = primary,
            primaryContainer = primary,
            background = background,
            secondary = secondary,
            secondaryContainer = secondaryContainer,
            onSecondaryContainer = onSecondaryContainer,
            inversePrimary = inversePrimary,
        )
    }

    // Add support for dark mode later on.
    @Composable
    fun darkColourTheme() = lightColourTheme()
}