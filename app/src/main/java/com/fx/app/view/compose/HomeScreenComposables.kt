package com.fx.app.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fx.app.libfoundation.view.compose.theme.Theme.Dimensions.defaultMargin

@Composable
fun HomeScreen() = FxAppMain {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CurrencyExchangePanel(
            Modifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .fillMaxWidth()
                .padding(defaultMargin)
        )
    }
}

@Composable
fun CurrencyExchangePanel(modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.Center) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            Button(onClick = {}) {
                Text("EUR")
            }
        }
    }
}