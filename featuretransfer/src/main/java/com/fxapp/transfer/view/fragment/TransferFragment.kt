package com.fxapp.transfer.view.fragment

import com.fxapp.libfoundation.view.base.ComposeFragment
import com.fxapp.libfoundation.view.compose.ComposeObject
import com.fxapp.transfer.view.compose.TransferMainScreenComposable

class TransferFragment : ComposeFragment() {

    override val composeScreen: ComposeObject
        get() = {
            TransferMainScreenComposable()
        }
}
