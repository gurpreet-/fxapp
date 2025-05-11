package com.fxapp.libfoundation.presentation.view.theme

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fxapp.libfoundation.R

object Typography {
    private const val DEFAULT_FONT_SIZE = 14

    val defaultFontSize = DEFAULT_FONT_SIZE.sp

    @Composable
    fun defaultTextStyle() = TextStyle(
        color = LocalContentColor.current,
        fontSize = defaultFontSize,
        fontFamily = FontFamily(
            Font(R.font.ss3_light, FontWeight.Light),
            Font(R.font.ss3_regular, FontWeight.Normal),
            Font(R.font.ss3_medium, FontWeight.Medium),
            Font(R.font.ss3_semibold, FontWeight.SemiBold),
            Font(R.font.ss3_bold, FontWeight.Bold),

            Font(R.font.ss3_lightitalic, FontWeight.Light, style = FontStyle.Italic),
            Font(R.font.ss3_italic, FontWeight.Normal, style = FontStyle.Italic),
            Font(R.font.ss3_mediumitalic, FontWeight.Medium, style = FontStyle.Italic),
            Font(R.font.ss3_semibolditalic, FontWeight.SemiBold, style = FontStyle.Italic),
            Font(R.font.ss3_bolditalic, FontWeight.Bold, style = FontStyle.Italic),
        ),
    )

    @Composable
    fun default() = Typography(
        labelSmall = defaultTextStyle().copy(fontSize = (DEFAULT_FONT_SIZE - 0.5).sp, fontWeight = FontWeight.SemiBold),
        labelMedium = defaultTextStyle().copy(fontSize = DEFAULT_FONT_SIZE.sp, fontWeight = FontWeight.SemiBold),
        labelLarge = defaultTextStyle().copy(fontSize = (DEFAULT_FONT_SIZE + 0.5).sp, fontWeight = FontWeight.SemiBold),
        bodySmall = defaultTextStyle().copy(fontSize = (DEFAULT_FONT_SIZE - 2).sp),
        bodyMedium = defaultTextStyle(),
        bodyLarge = defaultTextStyle().copy(fontSize = (DEFAULT_FONT_SIZE + 4).sp),
        titleSmall = defaultTextStyle().copy(fontSize = (DEFAULT_FONT_SIZE + 8).sp),
        titleMedium = defaultTextStyle().copy(fontSize = (DEFAULT_FONT_SIZE + 10).sp),
        titleLarge = defaultTextStyle().copy(fontSize = (DEFAULT_FONT_SIZE + 14).sp),
        headlineLarge = defaultTextStyle().copy(fontSize = (DEFAULT_FONT_SIZE + 32).sp),
    )
}