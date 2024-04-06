package com.fxapp.libfoundation.data

import java.math.BigDecimal
import java.util.Currency

data class AmountOnDate(
    val currency: Currency,
    val value: BigDecimal,
    val date: String
) {
    fun toAmount() = Amount(currency, value)
}