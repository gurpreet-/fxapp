package com.fxapp.viewmodel

import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.extensions.toAmount
import com.fxapp.libfoundation.viewmodel.base.BaseViewModel
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Currency
import java.util.Locale
import kotlin.random.Random

class CurrencyConverterViewModel : BaseViewModel() {

    private val rates: List<Amount> = listOf(GBP, EUR, USD).map { it.toAmount(Random.nextDouble(0.1, 2.0).toBigDecimal()) }

    suspend fun getRates(amount: BigDecimal?, currency: Currency?): List<Amount> {
        return rates
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
