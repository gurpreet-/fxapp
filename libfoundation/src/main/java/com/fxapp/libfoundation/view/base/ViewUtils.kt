package com.fxapp.libfoundation.view.base

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.fxapp.libfoundation.R

fun showGlobalError(context: Context, message: String = "") {
    val builder = AlertDialog.Builder(context)
    builder
        .setTitle(context.getString(R.string.sorry))
        .setMessage(context.getString(R.string.sorry_description, message))
        .setPositiveButton(context.getString(R.string.ok)) { dialog, id ->
            dialog.dismiss()
        }
    // Create the AlertDialog object and return it.
    builder.create()
}