package com.fxapp.libfoundation.extensions

import com.fxapp.libfoundation.domain.entities.Amount
import java.math.BigDecimal
import java.util.Currency

fun Currency.toAmount(value: BigDecimal? = null) = Amount(this, value)