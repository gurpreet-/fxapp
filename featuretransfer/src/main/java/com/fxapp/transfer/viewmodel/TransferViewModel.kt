package com.fxapp.transfer.viewmodel

import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.data.AmountOnDate
import com.fxapp.libfoundation.extensions.toAmount
import com.fxapp.libfoundation.model.ConversionModel
import com.fxapp.libfoundation.model.ConversionModel.Companion.GBP
import com.fxapp.libfoundation.view.base.BaseUIState
import com.fxapp.libfoundation.viewmodel.base.BaseViewModel
import com.fxapp.transfer.model.HistoricRatesModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TransferViewModel(
    private val conversionModel: ConversionModel,
    private val historicRatesModel: HistoricRatesModel
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(TransferScreenState())
    val uiState = _uiState.asStateFlow()

    var fromAmount = GBP.toAmount()
    var exchangedAmount = GBP.toAmount()

    fun getHistoricalRates() = launchOnIO {
        with(_uiState) {
            try {
                update { it.copy(isLoading = true) }
                val rates = historicRatesModel.getHistoricRates(fromAmount, exchangedAmount.currency.currencyCode)
                update { it.copy(historicRates = rates) }
            } catch (e: Throwable) {
                showError(this, e)
            } finally {
                update { it.copy(isLoading = false) }
            }
        }
    }

    fun formatAmount(amount: Amount) = conversionModel.format(amount)

    fun getExchangedAmountFormatted(): String {
        return formatAmount(exchangedAmount)
    }

    data class TransferScreenState(
        val historicRates: List<AmountOnDate> = listOf(),
        override var isLoading: Boolean = false,
        override var error: Throwable? = null
    ) : BaseUIState
}
