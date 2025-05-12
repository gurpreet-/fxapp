package com.fxapp.presentation.viewmodel

import com.fxapp.domain.usecases.GetAvailableCurrenciesUseCase
import com.fxapp.domain.usecases.GetExchangeRateForAmountUseCase
import com.fxapp.domain.usecases.GetNumberFormatUseCase
import com.fxapp.libfoundation.domain.entities.Amount
import com.fxapp.libfoundation.presentation.base.BaseViewModel
import com.fxapp.libfoundation.presentation.event.FxAppEvent
import com.fxapp.libfoundation.presentation.state.FxAppState
import kotlinx.coroutines.FlowPreview
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

@OptIn(FlowPreview::class)
class ConverterViewModel(
    private val getAvailableCurrenciesUseCase: GetAvailableCurrenciesUseCase,
    private val getNumberFormatUseCase: GetNumberFormatUseCase,
    private val getExchangeRateForAmountUseCase: GetExchangeRateForAmountUseCase,
) : BaseViewModel() {

    // Main state flow used to update the view
    // careful not to add too many fields that update as
    // it can be quite expensive. Better to use different
    // screen states.
    private val _uiState = MutableStateFlow(FxAppState())
    // Consume this unidirectional UI state from the view
    val uiState: StateFlow<FxAppState> = _uiState.asStateFlow()

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

    fun onEvent(event: FxAppEvent) {
        when (event) {
            is FxAppEvent.GetAvailableCurrencies -> getAvailableCurrencies()
            is FxAppEvent.SetAmount -> setAmount(event.amount)
            else -> Unit
        }
    }

    /**
     * This method fetches and shows exchange rates
     * based on an amount we supply.
     */
    private fun getFormattedExchangeRates(amount: Amount) = launchOnIO {
        with(_uiState) {
            try {
                update { it.copy(isLoading = true) }
                if (!amount.isZero()) {
                    val formatted = getExchangeRateForAmountUseCase.invoke(amount)
                    update { it.copy(formattedExchangeRates = formatted) }
                }
            } catch (e: Throwable) {
                showError(this, e)
            } finally {
                update { it.copy(isLoading = false) }
            }
        }
    }

    private fun getAvailableCurrencies() = launchOnIO {
        val currencies = getAvailableCurrenciesUseCase.invoke()
        _uiState.update { it.copy(availableCurrencies = currencies) }
    }

    fun getNumberFormat(currency: Currency): DecimalFormat {
        return getNumberFormatUseCase.invoke(currency)
    }

    private fun setAmount(amount: Amount) = launchOnIO {
        _uiState.update { it.copy(amount = amount, isLoading = true) }
        exchangeRatesUpdaterFlow.emit(amount)
    }
}
