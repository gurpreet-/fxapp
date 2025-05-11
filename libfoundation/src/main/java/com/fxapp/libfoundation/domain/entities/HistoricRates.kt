package com.fxapp.libfoundation.domain.entities

data class HistoricRates (
    val amount: Double,
    val base: String,
    val startDate: String,
    val endDate: String,
    val rates: DateRates
)