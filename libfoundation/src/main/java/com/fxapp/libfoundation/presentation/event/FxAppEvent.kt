package com.fxapp.libfoundation.presentation.event

sealed interface FxAppEvent {
    data object AppStartUpEvent : FxAppEvent

    // Login
    data object LoginEvent : FxAppEvent
    data object LogoutEvent : FxAppEvent
    data class LoginFieldEvent(val username: String?, val password: String?) : FxAppEvent

    // Transfer
    data object GetHistoricalRatesEvent : FxAppEvent
}