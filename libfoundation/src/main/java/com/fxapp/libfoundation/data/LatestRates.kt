package com.fxapp.libfoundation.data

data class LatestRates (
    val amount: Long,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
