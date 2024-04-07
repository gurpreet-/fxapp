package com.fxapp.libtransfer

import com.fxapp.libfoundation.data.AmountOnDate
import com.fxapp.libfoundation.repository.ApiRepository
import com.fxapp.libtest.BaseUnitTest
import com.fxapp.libtest.data.HistoricRatesDataGenerator.historicRates
import com.fxapp.libtest.data.HistoricRatesDataGenerator.oneGbp
import com.fxapp.transfer.model.HistoricRatesModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.util.Currency
import kotlin.test.assertEquals

class HistoricRatesModelTest : BaseUnitTest() {

    private val apiRepository = mockk<ApiRepository>(relaxed = true)
    private lateinit var historicRatesModel: HistoricRatesModel


    private val convertTo = "USD"
    private val convertToCurrency: Currency
        get() = Currency.getInstance(convertTo)

    @Before
    fun beforeTest() {
        historicRatesModel = HistoricRatesModel(apiRepository)
    }

    /**
     * This test may be redundant as we're testing SDK
     * date formatting logic.
     */
    @Test
    fun `formats correct date ago`() = mockkStatic(Instant::class) {
        mockkStatic(ZoneId::class) {
            every { ZoneId.systemDefault() } returns ZoneId.of("UTC")
            every { Instant.now() } returns Instant.ofEpochSecond(1712527323)
            assertEquals("2024-04-06", historicRatesModel.getFormattedDate(1))
        }
    }

    @Test
    fun `gets correct list with most recent date first`() = runTest {
        coEvery { apiRepository.getHistoricRates(oneGbp, convertTo, any()) } returns historicRates
        val rates = historicRatesModel.getHistoricRates(oneGbp, convertTo)
        assertEquals(
            AmountOnDate(convertToCurrency, BigDecimal(1.18), "2024-04-01"),
            rates.first()
        )
    }

    @Test
    fun `gets correct list with least recent date last`() = runTest {
        coEvery { apiRepository.getHistoricRates(oneGbp, convertTo, any()) } returns historicRates
        val rates = historicRatesModel.getHistoricRates(oneGbp, convertTo)
        assertEquals(
            AmountOnDate(convertToCurrency, BigDecimal(1.26), "2024-03-29"),
            rates.last()
        )
    }
}
