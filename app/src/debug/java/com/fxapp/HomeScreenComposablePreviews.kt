package com.fxapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.fxapp.libfoundation.model.ConversionModel.Companion.baseNumberFormat
import com.fxapp.libfoundation.presentation.view.compose.RenderPreview
import com.fxapp.view.compose.CurrencyExchangePanel
import com.fxapp.view.compose.CurrencyTextField
import com.fxapp.view.compose.TypeSomething
import java.math.BigDecimal
import java.util.Currency

@Preview
@Composable
private fun PreviewCurrencyExchangePanel() {
    CurrencyExchangePanel {
        CurrencyTextField(
            decimalFormat = baseNumberFormat,
            value = BigDecimal.ONE,
            currency = Currency.getInstance("GBP"),
            onCurrencyChanged = {  },
            onValueChanged = { },
        )
    }
}

@Preview
@Composable
private fun PreviewTypeSomething() = RenderPreview {
    TypeSomething()
}