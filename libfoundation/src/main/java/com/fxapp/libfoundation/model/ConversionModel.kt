package com.fxapp.libfoundation.model

import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.data.AmountFormatted
import com.fxapp.libfoundation.data.CurrencyMap
import com.fxapp.libfoundation.repository.ApiRepository
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Currency
import java.util.Locale

open class ConversionModel(val apiRepository: ApiRepository) {
    // A small handy cache for our basic rates
    // that we get from the API
    private var rates: MutableList<Amount> = mutableListOf()
    // Another handy store of all the exchanged rates.
    private var exchangedRates: List<Amount> = mutableListOf()

    suspend fun getExchangedRatesForAmountFormatted(amount: Amount): List<AmountFormatted> {
        return getExchangedRatesForAmount(amount)
            .map { AmountFormatted(it.currency.currencyCode, format(it)) }
    }

    private suspend fun getExchangedRatesForAmount(amount: Amount): List<Amount> {
        if (rates.isEmpty()) {
            // Get the latest conversion rates from the API.
            val fetchedRates = apiRepository.getLatestRates(amount.currency)
            // Map to formats that we can use within the app.
            rates = fetchedRates.rates
                .map { Amount(Currency.getInstance(it.key), BigDecimal(it.value)) }
                .toMutableList()
                .apply {
                    add(amount.copy(value = BigDecimal.ONE))
                }
        }

        // Multiply rates
        exchangedRates = rates.map {
            val multiplied = amount.value.multiply(it.value)
            it.copy(value = multiplied)
        }

        // When presenting, filter the one currently requested
        return exchangedRates.filterNot { it.currency == amount.currency }
    }

    /**
     * A list of available currencies.
     */
    fun getAvailableCurrencies(): List<Currency> {
        return listOf(
            Currency.getInstance("GBP"),
            Currency.getInstance("USD"),
            Currency.getInstance("EUR"),
        )
    }

    fun format(amount: Amount): String {
        return format(amount.currency, amount.value)
    }

    fun format(currency: Currency, amount: BigDecimal): String {
        val precisionValue = amount.setScale(2, RoundingMode.DOWN)
        return getNumberFormat(currency).format(precisionValue)
    }

    fun getNumberFormat(newCurrency: Currency): DecimalFormat {
        return baseNumberFormat.apply {
            decimalFormatSymbols = decimalFormatSymbols.apply {
                currencySymbol = CurrencyMap.map.getOrDefault(newCurrency.currencyCode, newCurrency.symbol)
            }
        }
    }

    /**
     * A useful method that parses a string and gets out
     * its numbers and a decimal separator.
     */
    fun extractNumbersAndSeparator(potentialNumber: String, decimalSeparator: Char = baseNumberFormat.decimalFormatSymbols.decimalSeparator): String {
        val filteredForNumbersAndSeparator = potentialNumber
            .filter { it.isDigit() || it == decimalSeparator }
            .ifBlank { "0" }

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