package com.fxapp.transfer.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.fxapp.libfoundation.view.base.BaseFragment
import com.fxapp.transfer.R
import com.fxapp.transfer.view.fragment.adapter.CurrencyTransferHubAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.fxapp.libfoundation.R as LFR

class CurrencyTransferHubFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflated = inflater.inflate(R.layout.fragment_currency_transfer_hub, container, false)
        return inflated
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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