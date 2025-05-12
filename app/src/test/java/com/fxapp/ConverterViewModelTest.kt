package com.fxapp

import app.cash.turbine.test
import com.fxapp.domain.usecases.GetAvailableCurrenciesUseCase
import com.fxapp.domain.usecases.GetExchangeRateForAmountUseCase
import com.fxapp.domain.usecases.GetNumberFormatUseCase
import com.fxapp.libfoundation.presentation.event.FxAppEvent
import com.fxapp.libtest.BaseUnitTest
import com.fxapp.libtest.data.HistoricRatesDataGenerator
import com.fxapp.presentation.viewmodel.ConverterViewModel
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class ConverterViewModelTest : BaseUnitTest() {

    private val numberFormatUseCase: GetNumberFormatUseCase = mockk(relaxed = true)
    private val availableCurrenciesUseCase: GetAvailableCurrenciesUseCase = mockk(relaxed = true)
    private val exchangeRateForAmountUseCase: GetExchangeRateForAmountUseCase = mockk(relaxed = true)
    private lateinit var viewModel: ConverterViewModel

    @Before
    fun beforeTest() {
        viewModel = ConverterViewModel(availableCurrenciesUseCase, numberFormatUseCase, exchangeRateForAmountUseCase)
    }

    @Test
    fun `when fired available currencies, then get available currencies`() = runTest {
        viewModel.onEvent(FxAppEvent.GetAvailableCurrencies)
        advanceUntilIdle()
        coVerify {
            availableCurrenciesUseCase.invoke()
        }
    }

    @Test
    fun `when fired event not intended for this viewmodel, then do nothing`() = runTest {
        viewModel.onEvent(FxAppEvent.AppStartUpEvent)
        verify(exactly = 0) {
            availableCurrenciesUseCase.invoke()
        }
    }

    @Test
    fun `when fired set amount, then set amount`() = runTest {
        viewModel.uiState.test {
            viewModel.onEvent(FxAppEvent.SetAmount(HistoricRatesDataGenerator.oneGbp))
            skipItems(1)
            val item = awaitItem().amount
            assertEquals(HistoricRatesDataGenerator.oneGbp, item)
        }
    }
}
