package com.fxapp.libfoundation.data

data class HistoricRates (
    val amount: Long,
    val base: String,
    val startDate: String,
    val endDate: String,
    val rates: DateRates
)