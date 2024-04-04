package com.fxapp.libfoundation.view.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.fxapp.libfoundation.R

object Typography {
    private const val DEFAULT_FONT_SIZE = 13

    val defaultFont = DEFAULT_FONT_SIZE.sp


    @Composable
    fun defaultTextStyle() = TextStyle(
        color = Colours.default().formFieldTextColour,
        fontSize = defaultFont,
        fontFamily = FontFamily(
            Font(R.font.main_font)
        )
    )

    @Composable
    fun default() = Typography(
        bodySmall = defaultTextStyle().copy(fontSize = (DEFAULT_FONT_SIZE - 1).sp),
        bodyMedium = defaultTextStyle(),
        titleSmall = defaultTextStyle().copy(fontSize = (DEFAULT_FONT_SIZE + 8).sp),
        titleMedium = defaultTextStyle().copy(fontSize = (DEFAULT_FONT_SIZE + 10).sp),
        titleLarge = defaultTextStyle().copy(fontSize = (DEFAULT_FONT_SIZE + 14).sp),
    )
}