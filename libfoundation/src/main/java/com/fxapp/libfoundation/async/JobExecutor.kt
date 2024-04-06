package com.fxapp.libfoundation.async

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class JobExecutor(
    private val coroutineScopes: CoroutineScopes,
) {
    private val jobs: MutableList<Job> = mutableListOf()

    private val ioScope: CoroutineScope
        get() = coroutineScopes.ioScope

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