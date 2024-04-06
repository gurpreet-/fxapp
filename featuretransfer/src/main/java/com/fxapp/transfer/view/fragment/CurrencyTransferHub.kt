package com.fxapp.transfer.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fxapp.libfoundation.view.base.BaseFragment
import com.fxapp.transfer.R

class CurrencyTransferHub : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflated = inflater.inflate(R.layout.fragment_currency_transfer_hub, container, false)
        return inflated
    }


}