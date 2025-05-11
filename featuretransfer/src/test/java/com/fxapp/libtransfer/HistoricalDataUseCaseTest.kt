package com.fxapp.libtransfer

import com.fxapp.libtest.BaseUnitTest
import com.fxapp.libtest.data.HistoricRatesDataGenerator
import com.fxapp.transfer.domain.repository.HistoricRatesRepository
import com.fxapp.transfer.domain.usecases.HistoricalDataUseCase
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class HistoricalDataUseCaseTest : BaseUnitTest() {

    private val repository = mockk<HistoricRatesRepository>(relaxed = true)
    private lateinit var historicalDataUseCase: HistoricalDataUseCase

    @Before
    fun beforeTest() {
        historicalDataUseCase = HistoricalDataUseCase(repository)
    }

    @Test
    fun `when invoked, then get historical rates from repo`() = runTest {
        historicalDataUseCase.invoke(HistoricRatesDataGenerator.oneGbp, "GBP")
        coVerify {
            repository.getHistoricRates(HistoricRatesDataGenerator.oneGbp, "GBP")
        }
    }
}
