package com.fxapp.transfer.model

import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.data.LatestRates
import com.fxapp.libfoundation.repository.ApiRepository
import com.fxapp.libfoundation.viewmodel.base.BaseViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class HistoricRatesModel(val apiRepository: ApiRepository) : BaseViewModel() {

    suspend fun getHistoricRates(amount: Amount, currency: String): LatestRates {
        val instantDaysPrior = Instant.now().minus(10, ChronoUnit.DAYS)
        val zonedTimeDaysPrior = ZonedDateTime.ofInstant(instantDaysPrior, ZoneId.systemDefault())
        val formatted = zonedTimeDaysPrior.format(DateTimeFormatter.ISO_LOCAL_DATE)
        return apiRepository.getHistoricRates(amount, currency, formatted)
    }
}