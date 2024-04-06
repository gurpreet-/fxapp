package com.fxapp.transfer.model

import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.data.AmountOnDate
import com.fxapp.libfoundation.data.onDate
import com.fxapp.libfoundation.repository.ApiRepository
import com.fxapp.libfoundation.viewmodel.base.BaseViewModel
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class HistoricRatesModel(val apiRepository: ApiRepository) : BaseViewModel() {

    suspend fun getHistoricRates(amount: Amount, currency: String): List<AmountOnDate> {
        val instantDaysPrior = Instant.now().minus(10, ChronoUnit.DAYS)
        val zonedTimeDaysPrior = ZonedDateTime.ofInstant(instantDaysPrior, ZoneId.systemDefault())
        val formatted = zonedTimeDaysPrior.format(DateTimeFormatter.ISO_LOCAL_DATE)
        val response = apiRepository.getHistoricRates(amount, currency, formatted)
        return response.rates.map { entry ->
            val foundRate = entry.value.entries.find { it.key == currency }
            val exchangedRate = foundRate?.value?.let { BigDecimal(it) } ?: BigDecimal.ZERO
            Amount(currency, exchangedRate).onDate(entry.key)
        }.reversed()
    }
}