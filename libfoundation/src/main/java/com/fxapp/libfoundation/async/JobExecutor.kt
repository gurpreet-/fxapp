package com.fxapp.libfoundation.async

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class JobExecutor(
    private val coroutineScopes: CoroutineScopes,
) {
    private val jobs: MutableList<Job> = mutableListOf()

    val ioScope: CoroutineScope
        get() = coroutineScopes.ioScope

    val ioContext: CoroutineContext
        get() = ioScope.coroutineContext


    fun launchIO(block: suspend CoroutineScope.() -> Unit) = try {
        val job = ioScope.launch(block = block)
        jobs.add(job)
    } catch (e: CancelledByUsException) {
        Timber.e(e)
    }

    fun cancelAll() {
        jobs.forEach {
            it.cancel(CancelledByUsException())
        }
    }
}