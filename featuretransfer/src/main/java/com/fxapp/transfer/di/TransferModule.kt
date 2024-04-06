package com.fxapp.transfer.di

import com.fxapp.transfer.viewmodel.TransferViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object TransferModule {
    val module = module {
        viewModel { TransferViewModel(get(), get()) }
    }
}