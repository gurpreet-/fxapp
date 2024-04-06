package com.fxapp.transfer.viewmodel

import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.data.AmountFormatted
import com.fxapp.libfoundation.extensions.toAmount
import com.fxapp.libfoundation.model.ConversionModel
import com.fxapp.libfoundation.model.ConversionModel.Companion.GBP
import com.fxapp.libfoundation.viewmodel.base.BaseViewModel
import com.fxapp.transfer.model.HistoricRatesModel
import kotlinx.coroutines.flow.MutableStateFlow

class TransferViewModel(
    private val conversionModel: ConversionModel,
    private val historicRatesModel: HistoricRatesModel
) : BaseViewModel() {

    val uiState = MutableStateFlow(UIState())
    var fromAmount = GBP.toAmount()
    var exchangedAmount = GBP.toAmount()

    fun getHistoricalRates() = launchOnIO {
        historicRatesModel.getHistoricRates(fromAmount, exchangedAmount.currency.currencyCode)
    }

    fun formatAmount(amount: Amount) = conversionModel.format(amount)

    fun getExchangedAmountFormatted() = formatAmount(exchangedAmount)

    data class UIState(
        val historicRates: List<AmountFormatted> = listOf()
    )
}
