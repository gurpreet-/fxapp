package com.fxapp.libtransfer

import app.cash.turbine.test
import com.fxapp.libfoundation.data.AmountOnDate
import com.fxapp.libfoundation.model.ConversionModel
import com.fxapp.libtest.BaseUnitTest
import com.fxapp.libtest.data.HistoricRatesDataGenerator.oneGbp
import com.fxapp.transfer.model.HistoricRatesModel
import com.fxapp.transfer.viewmodel.TransferViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TransferViewModelTest : BaseUnitTest() {

    private val conversionModel: ConversionModel = mockk(relaxed = true)
    private val historicRatesModel: HistoricRatesModel = mockk(relaxed = true)
    private lateinit var viewModel: TransferViewModel

    @Before
    fun beforeTest() {
        coEvery { historicRatesModel.getHistoricRates(any(), any()) } returns listOf(
            AmountOnDate(oneGbp.currency, BigDecimal(1.2), "2024-04-06")
        )
        viewModel = TransferViewModel(conversionModel, historicRatesModel)
    }

    @Test
    fun `get historical rates loads first and then updates ui state`() = runTest {
        viewModel.uiState.test {
            viewModel.getHistoricalRates()
            skipItems(1) // Initial state
            assertTrue(awaitItem().isLoading)
            assertFalse(awaitItem().historicRates.isEmpty())
            assertFalse(awaitItem().isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

}