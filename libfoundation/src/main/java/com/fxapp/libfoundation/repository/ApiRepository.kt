package com.fxapp.libfoundation.repository

import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.data.HistoricRates
import com.fxapp.libfoundation.data.LatestRates
import com.fxapp.libfoundation.di.OkHttpClientHandler
import kotlinx.coroutines.coroutineScope
import org.http4k.core.Body
import org.http4k.core.HttpMessage.Companion.HTTP_2
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.format.Gson.auto

class ApiRepository(
    val client: OkHttpClientHandler
) {

    suspend fun getLatestRates(amount: Amount): LatestRates = coroutineScope {
        val latestRatesLens = Body.auto<LatestRates>().toLens()

        val request = getRequest("latest")
            .query("amount", amount.value.toString())
            .query("from", amount.currency.currencyCode)
        val response = client.invoke(request)

        latestRatesLens(response)
    }

    suspend fun getHistoricRates(
        amount: Amount,
        convertToCurrencyIsoCode: String,
        datePrior: String
    ): HistoricRates = coroutineScope {
        val converter = Body.auto<HistoricRates>().toLens()

        val request = getRequest("$datePrior..")
            .query("amount", amount.value.toString())
            .query("from", amount.currency.currencyCode)
            .query("to", convertToCurrencyIsoCode)
        val response = client.invoke(request)

        converter(response)
    }

    private fun getRequest(path: String) = Request(GET, "$FULL_URL/$path", HTTP_2)

    companion object {
        private const val SCHEME: String = "https://"
        private const val HOST: String = "api.frankfurter.app"
        const val FULL_URL: String = "$SCHEME$HOST"
    }
}