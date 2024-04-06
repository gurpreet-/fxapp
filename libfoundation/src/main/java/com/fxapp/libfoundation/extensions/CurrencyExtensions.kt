package com.fxapp.libfoundation.extensions

import com.fxapp.libfoundation.data.Amount
import java.math.BigDecimal
import java.util.Currency

fun Currency.toAmount(value: BigDecimal? = null) = Amount(this, value)
fun BigDecimal.toAmount(currencyCode: String) = Amount(Currency.getInstance(currencyCode), this)