package com.fxapp.login.di

import com.fxapp.login.model.AuthModel
import com.fxapp.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object LoginModule {

    val module = module {
        factory { AuthModel(get()) }
        viewModel { LoginViewModel(get(), get(), get()) }
    }
}