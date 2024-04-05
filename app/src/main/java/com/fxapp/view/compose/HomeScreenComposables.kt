package com.fxapp.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fxapp.libfoundation.view.compose.FormTextField
import com.fxapp.libfoundation.view.compose.FxAppScreen
import com.fxapp.libfoundation.view.compose.FxFilledIconButton
import com.fxapp.libfoundation.view.compose.RenderPreview
import com.fxapp.libfoundation.view.theme.Colours
import com.fxapp.libfoundation.view.theme.Dimens.defaultMargin
import com.fxapp.libfoundation.view.theme.Typography
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Currency
import java.util.Locale


@Composable
fun HomeScreen() = FxAppScreen {
    Column(
        Modifier
            .fillMaxSize()
            .background(Colours.default().green10)
            .verticalScroll(rememberScrollState())
    ) {
        CurrencyExchangePanel(
            Modifier
                .shadow(4.dp)
                .background(
                    Colours.default().primaryColourDark
                )
                .fillMaxWidth()
                .padding(defaultMargin)
        )
        Column(
            Modifier.weight(1f)
        ) {
            Text("Hi")
        }
    }
}

@Composable
fun CurrencyExchangePanel(modifier: Modifier = Modifier) {
    var firstAmount by remember { mutableStateOf(BigDecimal.ZERO) }
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        CurrencyTextField(firstAmount) {
            firstAmount = it
        }
    }
}

@Composable
fun CurrencyTextField(
    value: BigDecimal,
    onValueChange: (BigDecimal) -> Unit
) {
    val numberFormat = (DecimalFormat.getCurrencyInstance(Locale.UK) as DecimalFormat).apply {
        isParseBigDecimal = true
        roundingMode = RoundingMode.DOWN
        currency = Currency.getInstance("GBP")
        decimalFormatSymbols = decimalFormatSymbols.apply {
            decimalSeparator = '.'
            groupingSeparator = ','
        }
    }
    val formatted = numberFormat.format(value)
    val currentNumbersOnly by remember {
        mutableStateOf("")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FormTextField(
            value = formatted,
            modifier = Modifier.weight(1f),
            placeholder = "0.00",
            textStyle = Typography.default()
                .bodyMedium
                .copy(color = Colours.default().secondary, fontSize = 50.sp),
            colours = Colours.defaultTextFieldColors().copy(
                focusedTextColor = Colours.default().secondary,
                unfocusedTextColor = Colours.default().secondary,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Colours.default().cursorColour,
            )
        ) { formattedString ->
            val newNumbersOnly = numbersOnly(formattedString, numberFormat.decimalFormatSymbols.decimalSeparator)
            if (currentNumbersOnly != newNumbersOnly) {
                onValueChange(BigDecimal(newNumbersOnly))
            }
        }
        FxFilledIconButton()
    }
}

private fun numbersOnly(s: String, decimalSeparator: Char): String {
    return s.filter {
        it.isDigit() || it == decimalSeparator
    }
}

@Preview
@Composable
private fun PreviewCurrencyExchangePanel() = RenderPreview {
    CurrencyExchangePanel(Modifier.fillMaxWidth())
}