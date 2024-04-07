package com.fxapp.wrappers

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.fxapp.libfoundation.wrappers.BuildWrapper
import com.fxapp.libfoundation.wrappers.SharedPreferencesWrapper

class AppSharedPreferencesWrapper(
    val sharedPreferences: SharedPreferences
) : SharedPreferencesWrapper {
    override fun edit(commit: Boolean, action: SharedPreferences.Editor.() -> Unit) {
        sharedPreferences.edit(commit, action)
    }

    override fun getBoolean(key: String, default: Boolean) = sharedPreferences.getBoolean(key, default)

    companion object {
        fun createSharedPrefs(context: Context, buildWrapper: BuildWrapper): SharedPreferences {
            return if (buildWrapper.isDebug) {
                val masterKey: MasterKey = MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build()

                EncryptedSharedPreferences.create(
                    context,
                    "secret_shared_prefs",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            } else {
                context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
            }
        }
    }
}