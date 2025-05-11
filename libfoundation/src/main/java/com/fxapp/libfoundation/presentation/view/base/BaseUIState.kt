package com.fxapp.libfoundation.presentation.view.base

interface BaseUIState {
    var isLoading: Boolean
    var error: Throwable?
}