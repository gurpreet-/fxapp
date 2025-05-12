package com.fxapp.di

import com.fxapp.domain.usecases.GetAvailableCurrenciesUseCase
import com.fxapp.domain.usecases.GetExchangeRateForAmountUseCase
import com.fxapp.domain.usecases.GetNumberFormatUseCase
import com.fxapp.libfoundation.di.FoundationModule
import com.fxapp.libfoundation.wrappers.BuildWrapper
import com.fxapp.libfoundation.wrappers.NavigationWrapper
import com.fxapp.libfoundation.wrappers.SharedPreferencesWrapper
import com.fxapp.login.di.LoginModule
import com.fxapp.presentation.viewmodel.ConverterViewModel
import com.fxapp.transfer.di.TransferModule
import com.fxapp.wrappers.AppBuildWrapper
import com.fxapp.wrappers.AppNavigationWrapper
import com.fxapp.wrappers.AppSharedPreferencesWrapper
import com.fxapp.wrappers.AppSharedPreferencesWrapper.Companion.createSharedPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


object AppModule {
    val module = module {
        factory<BuildWrapper> { AppBuildWrapper() }
        factory<SharedPreferencesWrapper> { AppSharedPreferencesWrapper(createSharedPrefs(androidContext(), get())) }
        factory<NavigationWrapper> { AppNavigationWrapper(androidContext()) }

        factoryOf(::GetNumberFormatUseCase)
        factoryOf(::GetAvailableCurrenciesUseCase)
        factoryOf(::GetExchangeRateForAmountUseCase)
        viewModelOf(::ConverterViewModel)
    }

    val allModules = listOf(module, FoundationModule.module, LoginModule.module, TransferModule.module)
}