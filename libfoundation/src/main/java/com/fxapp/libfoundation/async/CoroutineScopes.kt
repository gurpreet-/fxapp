package com.fxapp.libfoundation.async

import kotlinx.coroutines.CoroutineScope

interface CoroutineScopes {
    val mainScope: CoroutineScope
    val ioScope: CoroutineScope
}