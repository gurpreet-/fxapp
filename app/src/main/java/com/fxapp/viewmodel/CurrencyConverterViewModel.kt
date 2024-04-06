package com.fxapp.viewmodel

import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.data.AmountFormatted
import com.fxapp.libfoundation.model.ConversionModel
import com.fxapp.libfoundation.model.ConversionModel.Companion.GBP
import com.fxapp.libfoundation.viewmodel.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.text.DecimalFormat
import java.util.Currency

class CurrencyConverterViewModel(
    private val conversionModel: ConversionModel
) : BaseViewModel() {

    val uiState = MutableStateFlow(UIState())
    var unformattedExchangeRates: List<Amount> = listOf()

    fun getRates(amount: Amount) = launchOnIO {
        unformattedExchangeRates = conversionModel.getExchangedRatesForAmount(amount)
        val formatted = conversionModel.getExchangedRatesForAmountFormatted(unformattedExchangeRates)
        uiState.update {
            it.copy(
                formattedExchangeRates = formatted
            )
        }
    }

    fun getAvailableCurrencies() = launchOnIO {
        uiState.update { it.copy(availableCurrencies = conversionModel.getAvailableCurrencies()) }
    }

    fun getNumberFormat(currency: Currency): DecimalFormat {
        return conversionModel.getNumberFormat(currency)
    }

    fun setAmount(amount: Amount) {
        uiState.update { it.copy(amount = amount) }
    }

    data class UIState(
        val amount: Amount = Amount(GBP),
        val formattedExchangeRates: List<AmountFormatted> = listOf(),
        val availableCurrencies: List<Currency> = listOf()
    )
}
