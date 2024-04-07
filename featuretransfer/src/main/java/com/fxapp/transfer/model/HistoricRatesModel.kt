package com.fxapp.transfer.model

import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.data.AmountOnDate
import com.fxapp.libfoundation.data.onDate
import com.fxapp.libfoundation.model.BaseModel
import com.fxapp.libfoundation.repository.ApiRepository
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class HistoricRatesModel(private val apiRepository: ApiRepository) : BaseModel() {

    suspend fun getHistoricRates(amount: Amount, currency: String): List<AmountOnDate> {
        val date10DaysAgo = getFormattedDate()
        val response = apiRepository.getHistoricRates(amount, currency, date10DaysAgo)
        return response.rates.map { entry ->
            val foundRate = entry.value.entries.find { it.key == currency }
            val exchangedRate = BigDecimal(foundRate?.value ?: 0.0)
            Amount(currency, exchangedRate).onDate(entry.key)
        }.reversed()
    }

    /**
     * Gets a date given X days ago.
     * @return Date in yyyy-mm-dd format (ISO_LOCAL_DATE)
     */
    fun getFormattedDate(daysAgo: Long = 10): String {
        val instantDaysPrior = Instant.now().minus(daysAgo, ChronoUnit.DAYS)
        val zonedTimeDaysPrior = ZonedDateTime.ofInstant(instantDaysPrior, ZoneId.systemDefault())
        return zonedTimeDaysPrior.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}