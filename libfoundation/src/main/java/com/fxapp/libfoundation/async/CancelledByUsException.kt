package com.fxapp.libfoundation.async

import kotlinx.coroutines.CancellationException

class CancelledByUsException : CancellationException("Cancelled by us.")