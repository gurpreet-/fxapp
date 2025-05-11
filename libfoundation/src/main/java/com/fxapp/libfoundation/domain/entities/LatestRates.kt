package com.fxapp.libfoundation.domain.entities

data class LatestRates (
    val amount: Double,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
