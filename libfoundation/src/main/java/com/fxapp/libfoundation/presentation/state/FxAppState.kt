package com.fxapp.libfoundation.presentation.state

import androidx.compose.runtime.Immutable
import com.fxapp.libfoundation.data.model.ConversionRepositoryImpl.Companion.GBP
import com.fxapp.libfoundation.domain.entities.Amount
import com.fxapp.libfoundation.domain.entities.AmountFormatted
import com.fxapp.libfoundation.domain.entities.AmountOnDate
import com.fxapp.libfoundation.presentation.view.base.BaseUIState
import java.util.Currency

@Immutable
data class FxAppState(
    val username: String = "",
    val password: String = "",
    val historicRates: List<AmountOnDate> = listOf(),
    val amount: Amount = Amount(GBP),
    val formattedExchangeRates: List<AmountFormatted> = listOf(),
    val availableCurrencies: List<Currency> = listOf(),
    override var isLoading: Boolean = false,
    override var error: Throwable? = null
) : BaseUIState