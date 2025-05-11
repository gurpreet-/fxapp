package com.fxapp.login.di

import com.fxapp.login.data.model.AuthRepositoryImpl
import com.fxapp.login.domain.repository.AuthRepository
import com.fxapp.login.domain.usecases.LoginUseCase
import com.fxapp.login.domain.usecases.LogoutUseCase
import com.fxapp.login.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

object LoginModule {
    val module = module {
        factoryOf(::AuthRepositoryImpl) bind AuthRepository::class
        factoryOf(::LoginUseCase)
        factoryOf(::LogoutUseCase)
        viewModelOf(::LoginViewModel)
    }
}