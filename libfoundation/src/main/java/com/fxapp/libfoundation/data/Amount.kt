package com.fxapp.libfoundation.data

import java.math.BigDecimal
import java.util.Currency

data class Amount(
    val currency: Currency,
    val value: BigDecimal,
)

fun Amount(currency: Currency, value: BigDecimal? = null) = Amount(
    currency,
    value ?: BigDecimal.ZERO
)