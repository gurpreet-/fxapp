package com.fxapp.model

import com.fxapp.libfoundation.data.Amount
import com.fxapp.repository.ApiRepository
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Currency
import java.util.Locale

class ConversionModel(
    val apiRepository: ApiRepository
) {

    suspend fun getExchangedRatesForAmount(amount: Amount): List<Amount> {
        val latestRates = apiRepository.getLatestRates(amount.currency)
        return latestRates.rates.map {
            val multiplied = amount.value.multiply(BigDecimal(it.value))
            Amount(Currency.getInstance(it.key), multiplied)
        }
    }

    fun format(amount: Amount): String {
        return format(amount.currency, amount.value)
    }

    fun format(currency: Currency, amount: BigDecimal): String {
        val precisionValue = amount.setScale(2, RoundingMode.DOWN)
        return getNumberFormat(currency).format(precisionValue)
    }

    fun getNumberFormat(currency: String) = getNumberFormat(Currency.getInstance(currency))

    fun getNumberFormat(newCurrency: Currency): DecimalFormat {
        return baseNumberFormat.apply {
            currency = newCurrency
            decimalFormatSymbols = decimalFormatSymbols.apply {
                currencySymbol = newCurrency.symbol
            }
        }
    }

    fun numbersOnly(potentialNumber: String, decimalSeparator: Char): String {
        val filteredForNumbersAndSeparator = potentialNumber
            .filter { it.isDigit() || it == decimalSeparator }
            .ifBlank { "0" }
        return try {
            // Just try parse here and if it throws an
            // exception then return 0. Potential to be improved
            // by keeping the existing value.
            BigDecimal(filteredForNumbersAndSeparator)
                .setScale(2, RoundingMode.DOWN)
                .toString()
        } catch (e: Throwable) {
            "0"
        }
    }

    companion object {
        val baseNumberFormat = (DecimalFormat.getCurrencyInstance(Locale.UK) as DecimalFormat).apply {
            isParseBigDecimal = true
            roundingMode = RoundingMode.DOWN
            currency = Currency.getInstance("GBP")
            decimalFormatSymbols = decimalFormatSymbols.apply {
                decimalSeparator = '.'
                groupingSeparator = ','
            }
        }

        val GBP = Currency.getInstance("GBP")
        val USD = Currency.getInstance("USD")
        val EUR = Currency.getInstance("EUR")
    }
}