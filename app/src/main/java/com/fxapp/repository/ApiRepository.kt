package com.fxapp.repository

import com.fxapp.di.OkHttpClientHandler
import com.fxapp.libfoundation.async.JobExecutor
import com.fxapp.libfoundation.data.LatestRates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import org.http4k.core.Body
import org.http4k.core.HttpMessage.Companion.HTTP_2
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.format.Gson.auto
import java.util.Currency

class ApiRepository(
    val client: OkHttpClientHandler,
    val jobExecutor: JobExecutor
) {
    suspend fun getLatestRates(from: Currency): LatestRates = launchRequest {
        val latestRatesLens = Body.auto<LatestRates>().toLens()

        val request = getRequest("latest").query("from", from.currencyCode)
        val response = client.invoke(request)

        latestRatesLens(response)
    }

    private fun getRequest(path: String) = Request(GET, "$FULL_URL/$path", HTTP_2)

    private suspend fun <T> launchRequest(block: suspend CoroutineScope.() -> T) = withContext(jobExecutor.ioContext, block)

    companion object {
        const val SCHEME: String = "https://"
        const val HOST: String = "api.frankfurter.app"
        const val FULL_URL: String = "$SCHEME$HOST"
    }
}