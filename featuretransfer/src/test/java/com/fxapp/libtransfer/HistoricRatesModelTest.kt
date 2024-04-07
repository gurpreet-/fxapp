package com.fxapp.libtransfer

import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.data.HistoricRates
import com.fxapp.libfoundation.repository.ApiRepository
import com.fxapp.libtest.BaseUnitTest
import com.fxapp.transfer.model.HistoricRatesModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class HistoricRatesModelTest : BaseUnitTest() {

    private val apiRepository = mockk<ApiRepository>(relaxed = true)
    private lateinit var historicRatesModel: HistoricRatesModel

    private val oneGbp = Amount("GBP", BigDecimal.ONE)
    private val historicRates = HistoricRates(
        oneGbp.value.toDouble(),
        oneGbp.currency.currencyCode,
        "2024-04-01",
        "2024-03-29",
        rates = mapOf(
            "2024-03-29" to mapOf(
                "USD" to 1.26
            ),
            "2024-03-30" to mapOf(
                "USD" to 1.24
            ),
            "2024-03-31" to mapOf(
                "USD" to 1.23
            ),
            "2024-04-01" to mapOf(
                "USD" to 1.18
            ),
        )
    )

    @Before
    fun beforeTest() {
        coEvery { apiRepository.getHistoricRates(oneGbp, "USD", historicRates.endDate) } returns historicRates
        historicRatesModel = HistoricRatesModel(apiRepository)
    }

    @Test
    fun test1() {

    }
}
