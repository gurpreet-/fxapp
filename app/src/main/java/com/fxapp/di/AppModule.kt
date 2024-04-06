package com.fxapp.di

import com.fxapp.libfoundation.di.FoundationModule
import com.fxapp.libfoundation.wrappers.BuildWrapper
import com.fxapp.model.ConversionModel
import com.fxapp.repository.ApiRepository
import com.fxapp.viewmodel.CurrencyConverterViewModel
import com.fxapp.wrappers.AppBuildWrapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object AppModule {
    val module = module {
        single<BuildWrapper> { AppBuildWrapper() }

        factory { ApiRepository(get()) }
        factory { ConversionModel(get()) }
        viewModel { CurrencyConverterViewModel(get()) }
    }

    val allModules = listOf(module, FoundationModule.module)
}