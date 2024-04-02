package com.fx.app.view.compose.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.fx.app.R

object Theme {


    object Colours {

        @Composable
        fun lightColourTheme() = lightColorScheme(
            primary = colorResource(R.color.colorPrimary),
            background = colorResource(R.color.viewBackground),
            secondary = colorResource(R.color.colorPrimaryLight),
            secondaryContainer = colorResource(R.color.colorPrimaryLight)
        )

        @Composable
        fun darkColourTheme() = darkColorScheme(
            primary = lightColourTheme().primary,
            secondary = lightColourTheme().secondary,
            secondaryContainer = lightColourTheme().secondaryContainer
        )
    }

    object Typography {

    }

    object Dimensions {
        val defaultMargin = 16.dp

    }
}