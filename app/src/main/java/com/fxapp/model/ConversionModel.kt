package com.fxapp.model

import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.data.AmountFormatted
import com.fxapp.libfoundation.data.CurrencyMap
import com.fxapp.repository.ApiRepository
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Currency
import java.util.Locale

class ConversionModel(
    val apiRepository: ApiRepository
) {
    private var rates: MutableList<Amount> = mutableListOf()

    suspend fun getExchangedRatesForAmount(amount: Amount): List<Amount> {
        if (rates.isEmpty()) {
            val fetchedRates = apiRepository.getLatestRates(amount.currency)
            rates = fetchedRates.rates
                .map { Amount(Currency.getInstance(it.key), BigDecimal(it.value)) }
                .toMutableList()
                .apply {
                    add(amount.copy(value = BigDecimal.ONE))
                }
        }

        return rates.map {
            val multiplied = amount.value.multiply(it.value)
            it.copy(value = multiplied)
        }
    }

    suspend fun getExchangedRatesForAmountFormatted(amount: Amount): List<AmountFormatted> {
        return getExchangedRatesForAmount(amount).map { AmountFormatted(it.currency.currencyCode, format(it)) }
    }

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

    fun getNumberFormat(currency: String) = getNumberFormat(Currency.getInstance(currency))

    fun getNumberFormat(newCurrency: Currency): DecimalFormat {
        return baseNumberFormat.apply {
            decimalFormatSymbols = decimalFormatSymbols.apply {
                currencySymbol = CurrencyMap.map.getOrDefault(newCurrency.currencyCode, newCurrency.symbol)
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
        val GBP = Currency.getInstance("GBP")

        val baseNumberFormat = (DecimalFormat.getCurrencyInstance(Locale.UK) as DecimalFormat).apply {
            isParseBigDecimal = true
            roundingMode = RoundingMode.DOWN
            currency = Currency.getInstance("GBP")
            decimalFormatSymbols = decimalFormatSymbols.apply {
                decimalSeparator = '.'
                groupingSeparator = ','
            }
        }
    }
}