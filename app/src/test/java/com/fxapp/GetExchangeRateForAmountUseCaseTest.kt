package com.fxapp

import com.fxapp.domain.usecases.GetExchangeRateForAmountUseCase
import com.fxapp.libfoundation.domain.repository.ConversionRepository
import com.fxapp.libtest.BaseUnitTest
import com.fxapp.libtest.data.HistoricRatesDataGenerator
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class GetExchangeRateForAmountUseCaseTest : BaseUnitTest() {

    private val conversionRepository = mockk<ConversionRepository>(relaxed = true)
    private lateinit var useCase: GetExchangeRateForAmountUseCase

    @Before
    fun beforeTest() {
        useCase = GetExchangeRateForAmountUseCase(conversionRepository)
    }

    @Test
    fun `when invoked, then try login`() = runTest {
        val amount = HistoricRatesDataGenerator.oneGbp
        useCase.invoke(amount)
        coVerify {
            conversionRepository.getExchangedRatesForAmountFormatted(amount)
        }
    }
}
