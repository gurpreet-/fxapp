package com.fxapp.transfer.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.view.base.BaseFragment
import com.fxapp.transfer.R
import com.fxapp.transfer.view.fragment.adapter.CurrencyTransferHubAdapter
import com.fxapp.transfer.viewmodel.TransferViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.math.BigDecimal
import com.fxapp.libfoundation.R as LFR

class TransferHubFragment : BaseFragment() {

    private val args by navArgs<TransferHubFragmentArgs>()
    private val transferViewModel by activityViewModel<TransferViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_currency_transfer_hub, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        transferViewModel.fromAmount = Amount(args.initialCurrency, BigDecimal(args.initialAmount))
        transferViewModel.exchangedAmount = Amount(args.convertedCurrency, BigDecimal(args.convertedAmount))

        val fragmentAdapter = CurrencyTransferHubAdapter(this)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager).apply {
            adapter = fragmentAdapter
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(LFR.string.tab1_title)
                1 -> getString(LFR.string.tab2_title)
                else -> getString(LFR.string.details)
            }
        }.attach()
    }

}