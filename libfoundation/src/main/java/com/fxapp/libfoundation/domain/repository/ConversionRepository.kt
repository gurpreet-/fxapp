package com.fxapp.libfoundation.domain.repository

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import com.fxapp.libfoundation.data.model.ConversionRepositoryImpl
import com.fxapp.libfoundation.domain.entities.Amount
import com.fxapp.libfoundation.domain.entities.AmountFormatted
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Currency

interface ConversionRepository {
    suspend fun getExchangedRatesForAmountFormatted(amount: Amount): List<AmountFormatted>

    suspend fun getExchangedRatesForAmount(amount: Amount): List<Amount>

    /**
     * A list of available currencies.
     */
    fun getAvailableCurrencies(): List<Currency>
    fun format(amount: Amount): String
    fun format(currency: Currency, amount: BigDecimal): String
    fun getNumberFormat(newCurrency: Currency): DecimalFormat

    /**
     * A useful method that parses a string and gets out
     * its numbers and a decimal separator.
     */
    fun extractNumbersAndSeparator(potentialNumber: String, decimalSeparator: Char = ConversionRepositoryImpl.baseNumberFormat.decimalFormatSymbols.decimalSeparator): String

    @VisibleForTesting(otherwise = PRIVATE)
    fun tryParseAsNumber(filteredForNumbersAndSeparator: String): String
}