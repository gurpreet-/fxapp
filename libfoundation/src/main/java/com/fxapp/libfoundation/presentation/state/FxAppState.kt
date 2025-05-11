package com.fxapp.libfoundation.presentation.state

import androidx.compose.runtime.Immutable
import com.fxapp.libfoundation.domain.entities.AmountOnDate
import com.fxapp.libfoundation.presentation.view.base.BaseUIState

@Immutable
data class FxAppState(
    val username: String = "",
    val password: String = "",
    val historicRates: List<AmountOnDate> = listOf(),
    override var isLoading: Boolean = false,
    override var error: Throwable? = null
) : BaseUIState