package com.fxapp.libfoundation.di

import com.fxapp.libfoundation.async.AppCoroutineScopes
import com.fxapp.libfoundation.async.CoroutineScopes
import com.fxapp.libfoundation.async.JobExecutor
import org.koin.dsl.module

object FoundationModule {
    val module = module {
        factory<CoroutineScopes> { AppCoroutineScopes() }
        factory<JobExecutor> { JobExecutor(get()) }
    }
}