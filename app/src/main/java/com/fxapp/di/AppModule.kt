package com.fxapp.di

import com.fxapp.libfoundation.di.FoundationModule
import com.fxapp.libfoundation.wrappers.BuildWrapper
import com.fxapp.libfoundation.wrappers.SharedPreferencesWrapper
import com.fxapp.login.di.LoginModule
import com.fxapp.transfer.di.TransferModule
import com.fxapp.viewmodel.ConverterViewModel
import com.fxapp.wrappers.AppBuildWrapper
import com.fxapp.wrappers.AppSharedPreferencesWrapper
import com.fxapp.wrappers.AppSharedPreferencesWrapper.Companion.createSharedPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object AppModule {
    val module = module {
        single<BuildWrapper> { AppBuildWrapper() }
        single<SharedPreferencesWrapper> { AppSharedPreferencesWrapper(createSharedPrefs(androidContext(), get())) }
        viewModel { ConverterViewModel(get()) }
    }

    val allModules = listOf(module, FoundationModule.module, LoginModule.module, TransferModule.module)
}