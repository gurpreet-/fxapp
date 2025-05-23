package com.fxapp.libfoundation.di

import android.content.Context
import com.fxapp.libfoundation.async.AppCoroutineScopes
import com.fxapp.libfoundation.async.CoroutineScopes
import com.fxapp.libfoundation.async.JobExecutor
import com.fxapp.libfoundation.data.model.ConversionRepositoryImpl
import com.fxapp.libfoundation.domain.repository.ApiRepository
import com.fxapp.libfoundation.domain.repository.ConversionRepository
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
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.File
import java.util.concurrent.TimeUnit

typealias OkHttpClientHandler = HttpHandler

object FoundationModule {

    val module = module {
        factoryOf(::AppCoroutineScopes) bind CoroutineScopes::class
        factoryOf(::JobExecutor)
        factory<OkHttpClientHandler> { createOkHttpClient(androidContext(), get()) }
        factoryOf(::ApiRepository)
        factoryOf(::ConversionRepositoryImpl) bind ConversionRepository::class
    }

    private fun createOkHttpClient(context: Context, buildWrapper: BuildWrapper): OkHttpClientHandler {
        // Cache responses
        val cacheStorage = File(context.cacheDir, "http_cache")
        val storage = ReadWriteCache.Disk(cacheStorage.absolutePath)

        // Wrap HTTP Handler in a Recording Filter and play traffic through it
        val withCachedContent = TrafficFilters.ServeCachedFrom(storage)
            .then(TrafficFilters.RecordTo(storage))

        // Build OkHttpClient
        val client = OkHttpClient()
            .newBuilder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .callTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .followRedirects(false)
            .build()

        // Print request and responses in debug
        val debugPrint = if (buildWrapper.isDebug) {
            DebuggingFilters.PrintRequestAndResponse()
        } else {
            Filter { it }
        }

        // String everything together
        return debugPrint
            .then(withCachedContent)
            .then(OkHttp(client = client))
    }
}