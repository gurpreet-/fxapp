package com.fxapp.libfoundation.presentation.event

import com.fxapp.libfoundation.domain.entities.Amount

sealed interface FxAppEvent {
    data object AppStartUpEvent : FxAppEvent

    // Login
    data object LoginEvent : FxAppEvent
    data object LogoutEvent : FxAppEvent
    data class LoginFieldEvent(val username: String?, val password: String?) : FxAppEvent

    // Conversion
    data object GetHistoricalRatesEvent : FxAppEvent
    data object GetAvailableCurrencies : FxAppEvent
    data class SetAmount(val amount: Amount) : FxAppEvent
}