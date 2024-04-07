package com.fxapp.libtest.data

import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.data.HistoricRates
import java.math.BigDecimal

object HistoricRatesDataGenerator {

    val oneGbp = Amount("GBP", BigDecimal.ONE)

    val historicRates = HistoricRates(
        oneGbp.value.toDouble(),
        oneGbp.currency.currencyCode,
        "2024-04-01",
        "2024-03-29",
        rates = mapOf(
            "2024-03-29" to mapOf(
                "USD" to 1.26
            ),
            "2024-03-30" to mapOf(
                "USD" to 1.24
            ),
            "2024-03-31" to mapOf(
                "USD" to 1.23
            ),
            "2024-04-01" to mapOf(
                "USD" to 1.18
            ),
        )
    )
}