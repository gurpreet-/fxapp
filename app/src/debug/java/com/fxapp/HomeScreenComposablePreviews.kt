package com.fxapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.fxapp.libfoundation.data.model.ConversionRepositoryImpl.Companion.baseNumberFormat
import com.fxapp.libfoundation.presentation.view.compose.RenderPreview
import com.fxapp.presentation.compose.CurrencyExchangePanel
import com.fxapp.presentation.compose.CurrencyTextField
import com.fxapp.presentation.compose.TypeSomething
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