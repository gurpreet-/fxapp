package com.fxapp.login.di

import com.fxapp.login.data.model.AuthRepositoryImpl
import com.fxapp.login.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object LoginModule {

    val module = module {
        factory { AuthRepositoryImpl(get()) }
        viewModel { LoginViewModel(get(), get(), get()) }
    }
}