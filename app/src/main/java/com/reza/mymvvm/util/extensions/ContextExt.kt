package com.reza.mymvvm.util.extensions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager


fun Context.createBroadcastReceiver(onReceive: (intent: Intent?) -> Unit)
        : BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            onReceive(intent)
        }
    }

fun Context.registerLocalReceiver(receiver: BroadcastReceiver, intentFilterTag: String)
        = LocalBroadcastManager.getInstance(this).registerReceiver(receiver,IntentFilter(intentFilterTag))

fun Context.unregisterLocalReceiver(receiver: BroadcastReceiver) =
    LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)

fun Context.sendLocalBroadcast(intent: Intent) =
    LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
