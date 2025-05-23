package com.fxapp.transfer.presentation.compose

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
import com.fxapp.libfoundation.presentation.event.FxAppEvent
import com.fxapp.libfoundation.presentation.view.base.showGlobalError
import com.fxapp.libfoundation.presentation.view.compose.CircularLoading
import com.fxapp.libfoundation.presentation.view.compose.CurrencyRatesListItem
import com.fxapp.libfoundation.presentation.view.compose.FxAppScreen
import com.fxapp.libfoundation.presentation.view.compose.koinLocalViewModel
import com.fxapp.transfer.presentation.viewmodel.TransferViewModel

@Composable
fun HistoricMainScreenComposable(
    viewModel: TransferViewModel = koinLocalViewModel()
) = FxAppScreen {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val historicRates = uiState.historicRates

    LaunchedEffect(Unit) {
        viewModel.onEvent(FxAppEvent.GetHistoricalRatesEvent)
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