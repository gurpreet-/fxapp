package com.fxapp.repository

import com.fxapp.libfoundation.data.LatestRates
import com.fxapp.libfoundation.di.OkHttpClientHandler
import kotlinx.coroutines.coroutineScope
import org.http4k.core.Body
import org.http4k.core.HttpMessage.Companion.HTTP_2
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.format.Gson.auto
import java.util.Currency

class ApiRepository(
    val client: OkHttpClientHandler
) {

    suspend fun getLatestRates(from: Currency): LatestRates = coroutineScope {
        val latestRatesLens = Body.auto<LatestRates>().toLens()

        val request = getRequest("latest").query("from", from.currencyCode)
        val response = client.invoke(request)

        latestRatesLens(response)
    }

    private fun getRequest(path: String) = Request(GET, "$FULL_URL/$path", HTTP_2)

    companion object {
        const val SCHEME: String = "https://"
        const val HOST: String = "api.frankfurter.app"
        const val FULL_URL: String = "$SCHEME$HOST"
    }
}