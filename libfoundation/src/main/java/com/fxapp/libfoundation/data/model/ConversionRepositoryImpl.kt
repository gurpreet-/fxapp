package com.fxapp.libfoundation.data.model

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import com.fxapp.libfoundation.domain.entities.Amount
import com.fxapp.libfoundation.domain.entities.AmountFormatted
import com.fxapp.libfoundation.domain.entities.CurrencyMap
import com.fxapp.libfoundation.domain.repository.ApiRepository
import com.fxapp.libfoundation.domain.repository.ConversionRepository
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Currency
import java.util.Locale

open class ConversionRepositoryImpl(private val apiRepository: ApiRepository) :
    ConversionRepository {

    override suspend fun getExchangedRatesForAmountFormatted(amount: Amount): List<AmountFormatted> {
        return getExchangedRatesForAmount(amount)
            .map { AmountFormatted(it.currency.currencyCode, format(it)) }
    }

    override suspend fun getExchangedRatesForAmount(amount: Amount): List<Amount> {
        // Get the latest conversion rates from the API.
        val fetchedRates = apiRepository.getLatestRates(amount)
        // Map to formats that we can use within the app.
        val exchangedRates = fetchedRates.rates
            .map { Amount(Currency.getInstance(it.key), BigDecimal(it.value)) }
            .toMutableList()

        // When presenting, filter the one currently requested
        return exchangedRates.filterNot { it.currency == amount.currency }
    }

    override fun getAvailableCurrencies(): List<Currency> {
        return listOf(
            Currency.getInstance("GBP"),
            Currency.getInstance("USD"),
            Currency.getInstance("EUR"),
        )
    }

    override fun format(amount: Amount): String {
        return format(amount.currency, amount.value)
    }

    override fun format(currency: Currency, amount: BigDecimal): String {
        val precisionValue = amount.setScale(2, RoundingMode.DOWN)
        return getNumberFormat(currency).format(precisionValue)
    }

    override fun getNumberFormat(newCurrency: Currency): DecimalFormat {
        return baseNumberFormat.apply {
            decimalFormatSymbols = decimalFormatSymbols.apply {
                currencySymbol = CurrencyMap.map.getOrDefault(newCurrency.currencyCode, newCurrency.symbol)
            }
        }
    }

    override fun extractNumbersAndSeparator(potentialNumber: String, decimalSeparator: Char): String {
        val oneSeparator = if (potentialNumber.count { it == decimalSeparator } == 0) {
            potentialNumber
        } else {
            // Get the first most separator and sanitise the strings
            // before and after it
            val after = potentialNumber.substringAfter(decimalSeparator).filter(Char::isDigit)
            val before = potentialNumber.substringBefore(decimalSeparator)
            "$before$decimalSeparator$after"
        }

        val filteredForNumbersAndSeparator = oneSeparator
            .filter { it.isDigit() || it == decimalSeparator }
            .ifBlank { "0" }

        return tryParseAsNumber(filteredForNumbersAndSeparator)
    }

    @VisibleForTesting(otherwise = PRIVATE)
    override fun tryParseAsNumber(filteredForNumbersAndSeparator: String): String {
        // Just try parse here and if it throws an
        // exception then return 0. Potential to be improved
        // by keeping the existing value rather than defauling
        // to 0.
        return try {
            BigDecimal(filteredForNumbersAndSeparator)
                .setScale(2, RoundingMode.DOWN)
                .toString()
        } catch (e: Throwable) {
            "0"
        }
    }

    companion object {
        val GBP: Currency = Currency.getInstance("GBP")

        val baseNumberFormat = (DecimalFormat.getCurrencyInstance(Locale.UK) as DecimalFormat).apply {
            isParseBigDecimal = true
            roundingMode = RoundingMode.DOWN
            currency = Currency.getInstance("GBP")

            // For the purposes of this app, we force all decimal separators
            // to be "." and all grouping separators to be "," even though
            // that might look incorrect in some locales.
            decimalFormatSymbols = decimalFormatSymbols.apply {
                decimalSeparator = '.'
                groupingSeparator = ','
            }
        }
    }
}