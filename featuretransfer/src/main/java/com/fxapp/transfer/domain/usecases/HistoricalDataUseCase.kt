package com.fxapp.transfer.domain.usecases

import com.fxapp.libfoundation.domain.entities.Amount
import com.fxapp.libfoundation.domain.entities.AmountOnDate
import com.fxapp.transfer.data.model.HistoricRatesRepositoryImpl

class HistoricalDataUseCase(
    private val historicRatesRepository: HistoricRatesRepositoryImpl
) {
    suspend fun invoke(amount: Amount, currency: String): List<AmountOnDate> {
        return historicRatesRepository.getHistoricRates(amount, currency)
    }
}
