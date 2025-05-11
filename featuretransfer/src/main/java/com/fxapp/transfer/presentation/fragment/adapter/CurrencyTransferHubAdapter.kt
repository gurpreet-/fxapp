package com.fxapp.transfer.presentation.fragment.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fxapp.transfer.presentation.fragment.HistoricalRatesFragment
import com.fxapp.transfer.presentation.fragment.TransferFragment

class CurrencyTransferHubAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments = listOf(TransferFragment(), HistoricalRatesFragment())

    override fun getItemCount() = fragments.count()

    override fun createFragment(position: Int) = fragments[position]

}