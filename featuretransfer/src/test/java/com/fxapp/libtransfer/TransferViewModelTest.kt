package com.fxapp.libtransfer

import app.cash.turbine.test
import com.fxapp.libfoundation.domain.entities.AmountOnDate
import com.fxapp.libfoundation.domain.repository.ConversionRepository
import com.fxapp.libtest.BaseUnitTest
import com.fxapp.libtest.data.HistoricRatesDataGenerator.oneGbp
import com.fxapp.transfer.domain.usecases.HistoricalDataUseCase
import com.fxapp.transfer.presentation.viewmodel.TransferViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TransferViewModelTest : BaseUnitTest() {

    private val conversionRepository: ConversionRepository = mockk(relaxed = true)
    private val historicalDataUseCase = mockk<HistoricalDataUseCase>(relaxed = true)
    private lateinit var viewModel: TransferViewModel

    @Before
    fun beforeTest() {
        coEvery { historicalDataUseCase.invoke(any(), any()) } returns listOf(
            AmountOnDate(oneGbp.currency, BigDecimal(1.2), "2024-04-06")
        )
        viewModel = TransferViewModel(historicalDataUseCase, conversionRepository)
    }

    @Test
    fun `get historical rates loads first and then updates ui state`() = runTest {
        viewModel.state.test {
            viewModel.getHistoricalRates()
            skipItems(1) // Initial state
            assertTrue(awaitItem().isLoading)
            assertFalse(awaitItem().historicRates.isEmpty())
            assertFalse(awaitItem().isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

}