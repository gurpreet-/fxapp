package com.fxapp.domain.usecases

import com.fxapp.libfoundation.domain.entities.Amount
import com.fxapp.libfoundation.domain.entities.AmountFormatted
import com.fxapp.libfoundation.domain.repository.ConversionRepository

class GetExchangeRateForAmountUseCase(
    private val repository: ConversionRepository,
) {
    suspend fun invoke(amount: Amount): List<AmountFormatted> {
        return repository.getExchangedRatesForAmountFormatted(amount)
    }
}
