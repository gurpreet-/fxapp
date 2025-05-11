package com.fxapp.transfer.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.fxapp.libfoundation.domain.entities.Amount
import com.fxapp.libfoundation.presentation.view.base.ComposeFragment
import com.fxapp.libfoundation.presentation.view.compose.ComposeObject
import com.fxapp.transfer.presentation.compose.HistoricMainScreenComposable
import com.fxapp.transfer.presentation.viewmodel.TransferViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.math.BigDecimal

class HistoricalRatesFragment : ComposeFragment() {

    private val args by navArgs<HistoricalRatesFragmentArgs>()
    private val transferViewModel by activityViewModel<TransferViewModel>()

    override val composeScreen: ComposeObject
        get() = { HistoricMainScreenComposable() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        transferViewModel.fromAmount = Amount(args.initialCurrency, BigDecimal(args.initialAmount))
        transferViewModel.exchangedAmount = Amount(args.convertedCurrency, BigDecimal(args.convertedAmount))
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}