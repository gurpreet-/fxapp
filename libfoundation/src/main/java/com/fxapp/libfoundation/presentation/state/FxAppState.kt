package com.fxapp.libfoundation.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
data class FxAppState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)