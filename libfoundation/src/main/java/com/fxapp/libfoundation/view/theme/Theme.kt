package com.fxapp.libfoundation.view.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.fxapp.libfoundation.R

object Theme {


    object Colours {

        @Composable
        fun lightColourTheme() = lightColorScheme(
            primary = colorResource(R.color.colorPrimary),
            background = colorResource(R.color.viewBackground),
            secondary = colorResource(R.color.colorPrimaryLight),
            secondaryContainer = colorResource(R.color.colorPrimaryLight)
        )

        // Add support for dark mode later on.
        @Composable
        fun darkColourTheme() = lightColourTheme()
    }

    object Typography {

    }

    object Dimensions {
        val defaultMargin = 16.dp

    }
}