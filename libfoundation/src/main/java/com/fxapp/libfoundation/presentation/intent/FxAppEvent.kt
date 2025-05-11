package com.fxapp.libfoundation.presentation.intent

sealed interface FxAppEvent {
    data object AppStartUpEvent : FxAppEvent
    data object LoginEvent : FxAppEvent
    data object LogoutEvent : FxAppEvent
    data class LoginFieldEvent(val username: String?, val password: String?) : FxAppEvent
}