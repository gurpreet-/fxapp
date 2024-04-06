package com.fxapp.transfer.view.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fxapp.libfoundation.view.base.showGlobalError
import com.fxapp.libfoundation.view.compose.CircularLoading
import com.fxapp.libfoundation.view.compose.CurrencyRatesListItem
import com.fxapp.libfoundation.view.compose.FxAppScreen
import com.fxapp.libfoundation.view.compose.koinLocalViewModel
import com.fxapp.transfer.viewmodel.TransferViewModel

@Composable
fun HistoricMainScreenComposable(
    viewModel: TransferViewModel = koinLocalViewModel()
) = FxAppScreen {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val historicRates = uiState.historicRates

    LaunchedEffect(true) {
        viewModel.getHistoricalRates()
    }

    val context = LocalContext.current
    LaunchedEffect(uiState.error) {
        showGlobalError(context, uiState.error)
    }

    if (uiState.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularLoading()
        }
    } else {
        LazyColumn(Modifier.fillMaxSize()) {
            items(historicRates) {
                CurrencyRatesListItem(
                    it.currency.currencyCode,
                    viewModel.formatAmount(it.toAmount()),
                    it.date
                )
            }
        }
    }
}