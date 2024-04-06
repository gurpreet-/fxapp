package com.fxapp.libfoundation.data

import java.math.BigDecimal
import java.util.Currency

data class Amount(
    val currency: Currency,
    val value: BigDecimal,
) {
    fun isZero() = value.compareTo(BigDecimal.ZERO) == 0
}

fun Amount(currency: Currency, value: BigDecimal? = null) = Amount(
    currency,
    value ?: BigDecimal.ZERO
)

fun Amount(currencyCode: String, value: BigDecimal? = null) = Amount(
    Currency.getInstance(currencyCode),
    value ?: BigDecimal.ZERO
)

fun Amount.onDate(isoLocalDate: String) = AmountOnDate(currency, value, isoLocalDate)