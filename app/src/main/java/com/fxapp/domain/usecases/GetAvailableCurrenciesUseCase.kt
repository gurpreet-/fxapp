package com.fxapp.domain.usecases

import com.fxapp.libfoundation.domain.repository.ConversionRepository
import java.util.Currency

class GetAvailableCurrenciesUseCase(
    private val repository: ConversionRepository,
) {
    fun invoke(): List<Currency> {
        return repository.getAvailableCurrencies()
    }
}
