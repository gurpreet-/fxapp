package com.fxapp.libfoundation.async

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob

class AppCoroutineScopes : CoroutineScopes {
    override val mainScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
    override val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO + Job())
}