package com.fxapp.viewmodel

import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.data.AmountFormatted
import com.fxapp.libfoundation.model.ConversionModel
import com.fxapp.libfoundation.model.ConversionModel.Companion.GBP
import com.fxapp.libfoundation.view.base.BaseUIState
import com.fxapp.libfoundation.viewmodel.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.text.DecimalFormat
import java.util.Currency
import kotlin.time.Duration.Companion.seconds

class ConverterViewModel(
    private val conversionModel: ConversionModel
) : BaseViewModel() {

    // Main state flow used to update the view
    // careful not to add too many fields that update as
    // it can be quite expensive. Better to use different
    // screen states.
    private val _uiState = MutableStateFlow(ConverterScreenState())
    // Consume this unidirectional UI state from the view
    val uiState: StateFlow<ConverterScreenState> = _uiState.asStateFlow()

    // A flow with a debounce that you can emit
    // Amounts to without choking up the app with network requests.
    private val exchangeRatesUpdaterFlow = MutableStateFlow<Amount?>(null)

    init {
        launchOnIO {
            exchangeRatesUpdaterFlow
                .asStateFlow()
                .debounce(2.seconds)
                .onEach { it?.let { getFormattedExchangeRates(it) } }
                .launchIn(this)
        }
    }

    fun initialise() {
        getAvailableCurrencies()
    }

    /**
     * This method fetches and shows exchange rates
     * based on an amount we supply.
     */
    private fun getFormattedExchangeRates(amount: Amount) = launchOnIO {
        _uiState.update { it.copy(isLoading = true) }
        val formatted = conversionModel.getExchangedRatesForAmountFormatted(amount)
        _uiState.update { it.copy(formattedExchangeRates = formatted, isLoading = false) }
    }

    private fun getAvailableCurrencies() = launchOnIO {
        _uiState.update { it.copy(availableCurrencies = conversionModel.getAvailableCurrencies()) }
    }

    fun getNumberFormat(currency: Currency): DecimalFormat {
        return conversionModel.getNumberFormat(currency)
    }

    fun setAmount(amount: Amount) = launchOnIO {
        _uiState.update { it.copy(amount = amount, isLoading = true) }
        exchangeRatesUpdaterFlow.emit(amount)
    }

    data class ConverterScreenState(
        val amount: Amount = Amount(GBP),
        val formattedExchangeRates: List<AmountFormatted> = listOf(),
        val availableCurrencies: List<Currency> = listOf(),
        override var isLoading: Boolean = false,
        override var error: Throwable? = null
    ) : BaseUIState
}
