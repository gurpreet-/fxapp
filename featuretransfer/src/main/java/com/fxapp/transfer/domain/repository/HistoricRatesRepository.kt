package com.fxapp.transfer.domain.repository

import com.fxapp.libfoundation.domain.entities.Amount
import com.fxapp.libfoundation.domain.entities.AmountOnDate

interface HistoricRatesRepository {
    suspend fun getHistoricRates(amount: Amount, currency: String): List<AmountOnDate>

    /**
     * Gets a date given X days ago.
     * @return Date in yyyy-mm-dd format (ISO_LOCAL_DATE)
     */
    fun getFormattedDate(daysAgo: Long = 10): String
}