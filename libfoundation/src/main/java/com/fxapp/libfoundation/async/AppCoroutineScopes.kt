package com.fxapp.libfoundation.async

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob

class AppCoroutineScopes : CoroutineScopes {
    override val mainScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    override val ioScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.IO)
}