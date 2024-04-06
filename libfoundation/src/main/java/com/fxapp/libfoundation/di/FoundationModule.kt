package com.fxapp.libfoundation.di

import android.content.Context
import com.fxapp.libfoundation.async.AppCoroutineScopes
import com.fxapp.libfoundation.async.CoroutineScopes
import com.fxapp.libfoundation.async.JobExecutor
import com.fxapp.libfoundation.wrappers.BuildWrapper
import okhttp3.OkHttpClient
import org.http4k.client.OkHttp
import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.filter.TrafficFilters
import org.http4k.traffic.ReadWriteCache
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File

typealias OkHttpClientHandler = HttpHandler

object FoundationModule {

    val module = module {
        factory<CoroutineScopes> { AppCoroutineScopes() }
        factory<JobExecutor> { JobExecutor(get()) }
        single<OkHttpClientHandler> { createOkHttpClient(androidContext(), get()) }
    }

    private fun createOkHttpClient(context: Context, buildWrapper: BuildWrapper): OkHttpClientHandler {
        // Cache responses
        val cacheStorage = File(context.cacheDir, "http_cache")
        val storage = ReadWriteCache.Disk(cacheStorage.absolutePath)

        // Wrap HTTP Handler in a Recording Filter and play traffic through it
        val withCachedContent = TrafficFilters.ServeCachedFrom(storage)
            .then(TrafficFilters.RecordTo(storage))

        // Build OkHttpClient
        val client = OkHttpClient().newBuilder().followRedirects(false).build()

        // String everything together
        val debugPrint = if (buildWrapper.isDebug) {
            DebuggingFilters.PrintRequestAndResponse()
        } else {
            Filter { it }
        }
        return debugPrint
            .then(withCachedContent)
            .then(OkHttp(client = client))
    }
}