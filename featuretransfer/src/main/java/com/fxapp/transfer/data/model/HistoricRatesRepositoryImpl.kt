package com.fxapp.transfer.data.model

import com.fxapp.libfoundation.domain.entities.Amount
import com.fxapp.libfoundation.domain.entities.AmountOnDate
import com.fxapp.libfoundation.domain.entities.onDate
import com.fxapp.libfoundation.domain.repository.ApiRepository
import com.fxapp.libfoundation.domain.repository.BaseRepository
import com.fxapp.transfer.domain.repository.HistoricRatesRepository
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class HistoricRatesRepositoryImpl(private val apiRepository: ApiRepository) : BaseRepository(),
    HistoricRatesRepository {

    override suspend fun getHistoricRates(amount: Amount, currency: String): List<AmountOnDate> {
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
    override fun getFormattedDate(daysAgo: Long): String {
        val instantDaysPrior = Instant.now().minus(daysAgo, ChronoUnit.DAYS)
        val zonedTimeDaysPrior = ZonedDateTime.ofInstant(instantDaysPrior, ZoneId.systemDefault())
        return zonedTimeDaysPrior.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}