package com.fxapp.transfer.domain.usecases

import com.fxapp.libfoundation.domain.entities.Amount
import com.fxapp.libfoundation.domain.entities.AmountOnDate
import com.fxapp.transfer.domain.repository.HistoricRatesRepository

class HistoricalDataUseCase(
    private val historicRatesRepository: HistoricRatesRepository
) {
    suspend fun invoke(amount: Amount, currency: String): List<AmountOnDate> {
        return historicRatesRepository.getHistoricRates(amount, currency)
    }
}
