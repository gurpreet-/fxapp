package com.fxapp.transfer.view.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fxapp.libfoundation.view.compose.CurrencyRatesListItem
import com.fxapp.libfoundation.view.compose.FxAppScreen
import com.fxapp.transfer.viewmodel.TransferViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoricMainScreenComposable(
    viewModel: TransferViewModel = koinViewModel()
) = FxAppScreen {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val historicRates = uiState.historicRates
    LazyColumn(Modifier.fillMaxSize()) {
        items(historicRates) {
            CurrencyRatesListItem(it.currencyCode, it.formattedAmount) {

            }
        }
    }
}