package com.fxapp.transfer.di

import com.fxapp.transfer.data.model.HistoricRatesRepositoryImpl
import com.fxapp.transfer.domain.repository.HistoricRatesRepository
import com.fxapp.transfer.presentation.viewmodel.TransferViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module


object TransferModule {
    val module = module {
        factoryOf(::HistoricRatesRepositoryImpl) bind HistoricRatesRepository::class
        viewModelOf(::TransferViewModel)
    }
}