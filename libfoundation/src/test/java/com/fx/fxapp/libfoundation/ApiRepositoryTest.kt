package com.fx.fxapp.libfoundation

import com.fxapp.libfoundation.di.OkHttpClientHandler
import com.fxapp.libfoundation.domain.entities.Amount
import com.fxapp.libfoundation.domain.entities.LatestRates
import com.fxapp.libfoundation.domain.repository.ApiRepository
import com.fxapp.libfoundation.domain.repository.ApiRepository.Companion.FULL_URL
import com.fxapp.libtest.data.HistoricRatesDataGenerator
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.http4k.core.HttpMessage.Companion.HTTP_2
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class ApiRepositoryTest : BaseUnitTest() {

    private lateinit var apiRepository: ApiRepository
    private val client = mockk<OkHttpClientHandler>(relaxed = true)

    private val oneGbp = Amount("GBP", BigDecimal.ONE)
    private val latestRates = LatestRates(
        1.0,
        "GBP",
        "2024-04-01",
        mapOf(
            "USD" to 1.2,
            "EUR" to 1.3,
            "GBP" to 1.0, // Purposefully add GBP
        )
    )
    private val latestRatesJson = com.google.gson.Gson().toJson(latestRates)
    private val historicRatesJson = com.google.gson.Gson().toJson(HistoricRatesDataGenerator.historicRates)

    @Before
    fun beforeTest() {
        apiRepository = ApiRepository(client)
    }

    @Test
    fun `when getLatestRates, then returns LatestRates`() = runTest {
        every { client.invoke(any<Request>()) } returns Response(Status.OK).body(latestRatesJson)
        apiRepository.getLatestRates(oneGbp)
        verify {
            val req = getRequest("latest")
                .query("amount", oneGbp.value.toString())
                .query("from", oneGbp.currency.currencyCode)
            client.invoke(req)
        }
    }

    @Test
    fun `when getHistoricRates, then returns HistoricRates`() = runTest {
        every { client.invoke(any<Request>()) } returns Response(Status.OK).body(historicRatesJson)
        apiRepository.getHistoricRates(oneGbp, "GBP", "2024-04-01")
        verify {
            val request = getRequest("2024-04-01..")
                .query("amount", oneGbp.value.toString())
                .query("from", oneGbp.currency.currencyCode)
                .query("to", "GBP")
            client.invoke(request)
        }
    }

    private fun getRequest(path: String) = Request(GET, "$FULL_URL/$path", HTTP_2)
}
