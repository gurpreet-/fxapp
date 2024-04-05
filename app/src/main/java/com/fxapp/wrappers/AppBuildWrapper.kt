package com.fxapp.wrappers

import com.fxapp.BuildConfig
import com.fxapp.libfoundation.wrappers.BuildWrapper

class AppBuildWrapper : BuildWrapper {
    override val isDebug: Boolean
        get() = BuildConfig.IS_DEBUG

}