package com.fxapp.viewmodel

import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.extensions.toAmount
import com.fxapp.libfoundation.viewmodel.base.BaseViewModel
import com.fxapp.model.ConversionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Currency

class CurrencyConverterViewModel(
    private val conversionModel: ConversionModel
) : BaseViewModel() {

    val uiState = MutableStateFlow(UIState())

    suspend fun getRates(amount: BigDecimal?, currency: Currency) = launchOnIO {
        val rates = conversionModel.getExchangedRatesForAmount(currency.toAmount(amount))
        uiState.update { it.copy(exchangeRates = rates) }
    }

    fun getNumberFormat(currency: Currency): DecimalFormat {
        return conversionModel.getNumberFormat(currency)
    }

    data class UIState(
        val exchangeRates: List<Amount> = listOf()
    )
}
