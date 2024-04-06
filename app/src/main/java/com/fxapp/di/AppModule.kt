package com.fxapp.di

import com.fxapp.libfoundation.di.FoundationModule
import com.fxapp.libfoundation.wrappers.BuildWrapper
import com.fxapp.transfer.di.TransferModule
import com.fxapp.viewmodel.CurrencyConverterViewModel
import com.fxapp.wrappers.AppBuildWrapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object AppModule {
    val module = module {
        single<BuildWrapper> { AppBuildWrapper() }
        viewModel { CurrencyConverterViewModel(get()) }
    }

    val allModules = listOf(module, FoundationModule.module, TransferModule.module)
}