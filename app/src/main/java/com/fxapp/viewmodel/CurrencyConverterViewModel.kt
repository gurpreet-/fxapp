package com.fxapp.viewmodel

import com.fxapp.libfoundation.viewmodel.base.BaseViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Currency
import java.util.Locale

class CurrencyConverterViewModel : BaseViewModel() {

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
    }
}
