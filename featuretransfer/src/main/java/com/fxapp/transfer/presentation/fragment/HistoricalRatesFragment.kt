package com.fxapp.transfer.presentation.fragment

import com.fxapp.libfoundation.presentation.view.base.ComposeFragment
import com.fxapp.libfoundation.presentation.view.compose.ComposeObject
import com.fxapp.transfer.presentation.compose.HistoricMainScreenComposable

class HistoricalRatesFragment : ComposeFragment() {

    override val composeScreen: ComposeObject
        get() = { HistoricMainScreenComposable() }

}