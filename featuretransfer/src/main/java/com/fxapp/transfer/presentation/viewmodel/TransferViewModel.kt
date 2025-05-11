package com.fxapp.transfer.presentation.viewmodel

import com.fxapp.libfoundation.data.model.ConversionRepositoryImpl.Companion.GBP
import com.fxapp.libfoundation.domain.entities.Amount
import com.fxapp.libfoundation.domain.repository.ConversionRepository
import com.fxapp.libfoundation.extensions.toAmount
import com.fxapp.libfoundation.presentation.base.BaseViewModel
import com.fxapp.libfoundation.presentation.event.FxAppEvent
import com.fxapp.libfoundation.presentation.state.FxAppState
import com.fxapp.transfer.domain.usecases.HistoricalDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TransferViewModel(
    private val historicalDataUseCase: HistoricalDataUseCase,
    private val conversionRepository: ConversionRepository,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(FxAppState())
    val state = _uiState.asStateFlow()

    var fromAmount = GBP.toAmount()
    var exchangedAmount = GBP.toAmount()

    fun onEvent(event: FxAppEvent) {
        when (event) {
            is FxAppEvent.GetHistoricalRatesEvent -> getHistoricalRates()
            else -> Unit
        }
    }

    fun getHistoricalRates() = launchOnIO {
        with(_uiState) {
            try {
                update { it.copy(isLoading = true) }
                val rates = historicalDataUseCase.invoke(fromAmount, exchangedAmount.currency.currencyCode)
                update { it.copy(historicRates = rates) }
            } catch (e: Throwable) {
                showError(this, e)
            } finally {
                update { it.copy(isLoading = false) }
            }
        }
    }

    fun formatAmount(amount: Amount) = conversionRepository.format(amount)
}
