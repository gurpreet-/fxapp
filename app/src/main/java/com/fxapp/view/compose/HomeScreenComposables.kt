package com.fxapp.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fxapp.libfoundation.view.compose.FormTextField
import com.fxapp.libfoundation.view.compose.FxAppScreen
import com.fxapp.libfoundation.view.compose.RenderPreview
import com.fxapp.libfoundation.view.theme.Colours
import com.fxapp.libfoundation.view.theme.Dimens.extraSmallMargin
import com.fxapp.libfoundation.view.theme.Dimens.smallMargin
import com.fxapp.libfoundation.view.theme.Typography
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale


@Composable
fun HomeScreen() = FxAppScreen {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CurrencyExchangePanel(
            Modifier
                .background(
                    Colours.default().primaryContainer
                )
                .fillMaxWidth()
                .padding(smallMargin)
        )
    }
}

@Composable
fun CurrencyExchangePanel(modifier: Modifier = Modifier) {
    val firstAmount by remember { mutableStateOf(BigDecimal.ZERO) }
    val secondAmount by remember { mutableStateOf(BigDecimal.ZERO) }
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        CurrencyTextField(firstAmount)
        CurrencyTextField(secondAmount)
    }
}

@Composable
fun CurrencyTextField(
    value: BigDecimal
) {
    Box(Modifier.padding(start = 100.dp)) {
        FormTextField(
            value = NumberFormat
                .getCurrencyInstance(Locale.ENGLISH).apply {
                    currency = Currency.getInstance("EUR")
                    isGroupingUsed = true
                }.format(value),
            placeholder = "0.00",
            textStyle = Typography.default().titleLarge.copy(color = Colours.default().inversePrimary),
            trailingIcon = {
                Text(
                    "EUR",
                    modifier = Modifier
                        .padding(horizontal = smallMargin, vertical = extraSmallMargin),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = Typography.default().bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            colours = Colours.defaultTextFieldColors().copy(
                focusedTextColor = Colours.default().formFieldTextColour.copy(alpha = 0.9f),
                unfocusedTextColor = Colours.default().formFieldTextColour.copy(alpha = 0.9f),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Colours.default().cursorColour,
            )
        ) {

        }
    }
}

@Preview
@Composable
private fun PreviewCurrencyExchangePanel() = RenderPreview {
    CurrencyExchangePanel(Modifier.fillMaxWidth())
}