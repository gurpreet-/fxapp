package com.fxapp.domain.usecases

import com.fxapp.libfoundation.domain.repository.ConversionRepository
import java.text.DecimalFormat
import java.util.Currency

class GetNumberFormatUseCase(
    private val repository: ConversionRepository,
) {
    fun invoke(currency: Currency): DecimalFormat {
        return repository.getNumberFormat(currency)
    }
}
