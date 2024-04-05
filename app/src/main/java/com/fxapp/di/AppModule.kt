package com.fxapp.di

import com.fxapp.libfoundation.di.FoundationModule
import com.fxapp.libfoundation.wrappers.BuildWrapper
import com.fxapp.model.ConversionModel
import com.fxapp.repository.ApiRepository
import com.fxapp.repository.ApiRepository.Companion.HOST
import com.fxapp.viewmodel.CurrencyConverterViewModel
import com.fxapp.wrappers.AppBuildWrapper
import okhttp3.OkHttpClient
import org.http4k.client.OkHttp
import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

typealias OkHttpClientHandler = HttpHandler

object AppModule {
    val module = module {
        single<BuildWrapper> { AppBuildWrapper() }
        single<OkHttpClientHandler> { createOkHttpClient() }
        factory { ApiRepository(get(), get()) }
        factory { ConversionModel(get()) }
        viewModel { CurrencyConverterViewModel(get()) }
    }

    private fun createOkHttpClient(): OkHttpClientHandler {
        val client = OkHttpClient().newBuilder().addNetworkInterceptor {
            var request = it.request()

            if (!request.url.host.equals(HOST, true)) {
                val newUrl = request.url.newBuilder().host(HOST).build()
                request = request
                    .newBuilder()
                    .url(newUrl)
                    .build()
            }

            it.proceed(request)
        }.followRedirects(false).build()
        return DebuggingFilters.PrintRequestAndResponse().then(OkHttp(client = client))
    }

    val allModules = listOf(module, FoundationModule.module)
}