package com.fxapp.di

import com.fxapp.libfoundation.di.FoundationModule
import com.fxapp.viewmodel.CurrencyConverterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val module = module {
        viewModel { CurrencyConverterViewModel() }
    }

    val allModules = listOf(module, FoundationModule.module)
}