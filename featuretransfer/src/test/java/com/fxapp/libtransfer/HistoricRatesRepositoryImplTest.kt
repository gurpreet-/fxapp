package com.fxapp.libtransfer

import com.fxapp.libfoundation.domain.entities.AmountOnDate
import com.fxapp.libfoundation.domain.repository.ApiRepository
import com.fxapp.libtest.BaseUnitTest
import com.fxapp.libtest.data.HistoricRatesDataGenerator.historicRates
import com.fxapp.libtest.data.HistoricRatesDataGenerator.oneGbp
import com.fxapp.transfer.data.model.HistoricRatesRepositoryImpl
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

class HistoricRatesRepositoryImplTest : BaseUnitTest() {

    private val apiRepository = mockk<ApiRepository>(relaxed = true)
    private lateinit var historicRatesRepository: HistoricRatesRepositoryImpl


    private val convertTo = "USD"
    private val convertToCurrency: Currency
        get() = Currency.getInstance(convertTo)

    @Before
    fun beforeTest() {
        historicRatesRepository = HistoricRatesRepositoryImpl(apiRepository)
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
            assertEquals("2024-04-06", historicRatesRepository.getFormattedDate(1))
        }
    }

    @Test
    fun `gets correct list with most recent date first`() = runTest {
        coEvery { apiRepository.getHistoricRates(oneGbp, convertTo, any()) } returns historicRates
        val rates = historicRatesRepository.getHistoricRates(oneGbp, convertTo)
        assertEquals(
            AmountOnDate(convertToCurrency, BigDecimal(1.18), "2024-04-01"),
            rates.first()
        )
    }

    @Test
    fun `gets correct list with least recent date last`() = runTest {
        coEvery { apiRepository.getHistoricRates(oneGbp, convertTo, any()) } returns historicRates
        val rates = historicRatesRepository.getHistoricRates(oneGbp, convertTo)
        assertEquals(
            AmountOnDate(convertToCurrency, BigDecimal(1.26), "2024-03-29"),
            rates.last()
        )
    }
}
