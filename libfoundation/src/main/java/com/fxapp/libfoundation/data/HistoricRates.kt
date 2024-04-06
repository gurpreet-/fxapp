package com.fxapp.libfoundation.data

data class HistoricRates (
    val amount: Double,
    val base: String,
    val startDate: String,
    val endDate: String,
    val rates: DateRates
)