package com.fxapp.transfer.di

import com.fxapp.transfer.model.HistoricRatesModel
import com.fxapp.transfer.viewmodel.TransferViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object TransferModule {
    val module = module {
        factory { HistoricRatesModel(get()) }
        viewModel { TransferViewModel(get(), get()) }
    }
}